package com.tarikyasar.curmin.presentation.ui.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


abstract class BaseViewModel<UiState, Intent, Event>(initState: UiState) : ViewModel(),
    CoroutinesUseCaseRunner {

//    @Inject
//    lateinit var loadingManager: LoadingManager
//
//    @Inject
//    lateinit var errorManager: ErrorManager

    /**
     * This indicates that the screen/dialog etc is successfully loaded the initial data
     * that is required at the first launch of it
     * */
    protected var isInitialized: Boolean = false
        private set

    // this method will be called after injecting the vm
    fun initialize() {
        if (isInitialized.not()) {
            onFirstLaunch()
            isInitialized = true
        }
    }

    // this method will be used to load data after injecting the vm
    protected open fun onFirstLaunch() {}

    private val _events: Channel<Event> = Channel()
    val events = _events.receiveAsFlow()

    private val _uiState = MutableStateFlow(initState)
    val uiState = _uiState.asStateFlow()

    val currentUiState: UiState
        get() = _uiState.value

    private var loadingCount: Int = 0
        set(value) {
//            loadingManager.setLoading(value > 0)
            field = value
        }

    protected fun updateUiState(reducer: UiState.() -> UiState) {
        val newState = uiState.value.reducer()

        _uiState.value = newState
    }

    abstract fun onIntent(intent: Intent)

    protected fun triggerEvent(event: Event) {
        viewModelScope.launch { _events.send(event) }
    }

    override val useCaseScope = viewModelScope

    override fun withUseCaseScope(
        loadingUpdater: ((Boolean) -> Unit)?,
        onError: ((Throwable) -> Unit)?,
        onComplete: (() -> Unit)?,
        block: suspend () -> Unit
    ): Job {
        val defaultLoadingUpdater = { isLoading: Boolean ->
            if (isLoading) {
                loadingCount++
            } else {
                loadingCount--
            }
        }
        return super.withUseCaseScope(
            loadingUpdater = {
                loadingUpdater?.invoke(it) ?: defaultLoadingUpdater(it)
            },
            onError = {
                if (it is CancellationException) {
                    return@withUseCaseScope
                }
                onError?.invoke(it) // ?: errorManager.pushError(it)
            },
            onComplete = onComplete,
            block = block
        )
    }

    fun updateLoading(isLoading: Boolean) {
        if (isLoading) {
            loadingCount++
        } else {
            loadingCount--
        }
    }

    @Composable
    operator fun component1(): UiState {
        return uiState.collectAsState().value
    }

    operator fun component2(): (Intent) -> Unit = ::onIntent

    operator fun component3(): Flow<Event> = events
}