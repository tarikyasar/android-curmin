package com.tarikyasar.curmin.presentation.ui.screens.currency.watchlist

import android.os.Build
import androidx.annotation.RequiresApi
import com.tarikyasar.curmin.domain.database.model.CurrencyWatchlistItemData
import com.tarikyasar.curmin.domain.usecase.api.GetCurrencyFluctuationUseCase
import com.tarikyasar.curmin.domain.usecase.api.GetCurrencySymbolsUseCase
import com.tarikyasar.curmin.domain.usecase.database.DeleteCurrencyWatchlistItemUseCase
import com.tarikyasar.curmin.domain.usecase.database.GetCurrencyWatchlistItemsUseCase
import com.tarikyasar.curmin.domain.usecase.database.InsertCurrencyWatchlistItemUseCase
import com.tarikyasar.curmin.domain.usecase.database.UpdateCurrencyWatchlistItemUseCase
import com.tarikyasar.curmin.presentation.ui.base.BaseViewModel
import com.tarikyasar.curmin.presentation.ui.screens.currency.watchlist.CurrencyWatchlistContract.Event
import com.tarikyasar.curmin.presentation.ui.screens.currency.watchlist.CurrencyWatchlistContract.Intent
import com.tarikyasar.curmin.presentation.ui.screens.currency.watchlist.CurrencyWatchlistContract.UiState
import com.tarikyasar.curmin.presentation.ui.utils.PreferenceManager
import com.tarikyasar.curmin.utils.DateUtils
import com.tarikyasar.curmin.utils.DatesInMs
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class CurrencyWatchlistViewModel @Inject constructor(
    private val preferenceManager: PreferenceManager,
    private val getCurrencyWatchlistItems: GetCurrencyWatchlistItemsUseCase,
    private val insertCurrencyWatchlistItemUseCase: InsertCurrencyWatchlistItemUseCase,
    private val deleteCurrencyWatchlistItemUseCase: DeleteCurrencyWatchlistItemUseCase,
    private val updateCurrencyWatchlistItemUseCase: UpdateCurrencyWatchlistItemUseCase,
    private val getCurrencySymbolsUseCase: GetCurrencySymbolsUseCase,
    private val getCurrencyFluctuationUseCase: GetCurrencyFluctuationUseCase
) : BaseViewModel<UiState, Intent, Event>(
    UiState(
        askToRemoveItemParameter = preferenceManager.getPreference()
    )
) {

    override fun onFirstLaunch() {
        super.onFirstLaunch()

        withUseCaseScope {
            getCurrencySymbols()
        }
    }

    fun resetError() {
        updateUiState {
            copy(error = null)
        }
    }

    override fun onIntent(intent: Intent) {
        when (intent) {
            Intent.GetAskToRemoveItemParameter -> {
                updateUiState {
                    copy(
                        askToRemoveItemParameter = preferenceManager.getPreference()
                    )
                }
            }

            is Intent.GetCurrencies -> {
                withUseCaseScope {
                    getCurrencies(intent.forceRefresh)
                }
            }

            is Intent.DeleteCurrencyWatchlistItem -> {
                withUseCaseScope {
                    deleteCurrencyWatchlistItemUseCase(intent.currencyWatchlistItemUid)

                    updateUiState {
                        copy(
                            currencies = currentUiState.currencies.filter {
                                it.uid != intent.currencyWatchlistItemUid
                            }
                        )
                    }
                }
            }

            is Intent.UpdateCurrencyWatchlistItemData -> {
                withUseCaseScope {
                    updateCurrencyListItem(intent.currencyWatchlistItem)
                }
            }

            is Intent.InsertCurrencyWatchlistItem -> {
                withUseCaseScope {
                    insertCurrencyListItem(intent.currencyWatchlistItem)
                }
            }

            is Intent.GetCurrencyFluctuation -> {
                withUseCaseScope {
                    getCurrencyFluctuation(
                        startDate = intent.startDate,
                        endDate = intent.endDate,
                        baseCurrencyCode = intent.baseCurrencyCode,
                        targetCurrencyCode = intent.targetCurrencyCode,
                        currencyWatchlistItemData = intent.currencyWatchlistItemData
                    )
                }
            }

            is Intent.CreateCurrencyWatchlistItem -> {
                withUseCaseScope {
                    val todayInMs = System.currentTimeMillis()

                    val currencyFluctuation = getCurrencyFluctuationUseCase(
                        startDate = DateUtils.formatTime(todayInMs - DatesInMs.DAY.value),
                        endDate = DateUtils.formatTime(todayInMs),
                        baseCurrencyCode = intent.baseCurrencyCode,
                        targetCurrencyCode = intent.targetCurrencyCode
                    )

                    insertCurrencyListItem(
                        CurrencyWatchlistItemData(
                            uid = UUID.randomUUID().toString(),
                            baseCurrencyCode = intent.baseCurrencyCode,
                            targetCurrencyCode = intent.targetCurrencyCode,
                            rate = currencyFluctuation.endRate,
                            date = DateUtils.formatTime(LocalDateTime.now()),
                            change = currencyFluctuation.change
                        )
                    )
                }
            }
        }
    }

    private suspend fun getCurrencies(forceRefresh: Boolean = false) {
        val currenciesWatchlistItems = getCurrencyWatchlistItems()

        if (forceRefresh) {
            currentUiState.currencies.forEach { currencyWatchlistItemData ->
                val todayInMs = System.currentTimeMillis()

                getCurrencyFluctuation(
                    startDate = DateUtils.formatTime(todayInMs - DatesInMs.DAY.value),
                    endDate = DateUtils.formatTime(todayInMs),
                    baseCurrencyCode = currencyWatchlistItemData.baseCurrencyCode
                        ?: "USD",
                    targetCurrencyCode = currencyWatchlistItemData.targetCurrencyCode
                        ?: "TRY",
                    currencyWatchlistItemData = currencyWatchlistItemData
                )
            }
        } else {
            updateUiState {
                copy(currencies = currenciesWatchlistItems)
            }
        }
    }

    private suspend fun getCurrencySymbols() {
        val symbols = getCurrencySymbolsUseCase()

        updateUiState {
            copy(
                symbols = symbols
            )
        }

        getCurrencies(false)
    }

    private suspend fun updateCurrencyListItem(
        item: CurrencyWatchlistItemData
    ) {
        updateCurrencyWatchlistItemUseCase(item)

        getCurrencies()
    }

    private suspend fun insertCurrencyListItem(
        item: CurrencyWatchlistItemData
    ) {
        insertCurrencyWatchlistItemUseCase(item)

        updateUiState {
            copy(
                currencies = currentUiState.currencies + item
            )
        }
    }

    private suspend fun getCurrencyFluctuation(
        startDate: String,
        endDate: String,
        baseCurrencyCode: String,
        targetCurrencyCode: String,
        currencyWatchlistItemData: CurrencyWatchlistItemData
    ) {
        val currencyFluctuations = getCurrencyFluctuationUseCase(
            startDate = startDate,
            endDate = endDate,
            baseCurrencyCode = baseCurrencyCode,
            targetCurrencyCode = targetCurrencyCode
        )

        updateCurrencyListItem(
            currencyWatchlistItemData.copy(
                rate = currencyFluctuations.endRate,
                date = DateUtils.formatTime(LocalDateTime.now()),
                change = currencyFluctuations.change
            )
        )
    }
}