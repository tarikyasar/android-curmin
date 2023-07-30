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
import com.tarikyasar.curmin.presentation.ui.screens.currency.watchlist.CurrencyWatchlistContract.*
import com.tarikyasar.curmin.presentation.ui.utils.PreferenceManager
import com.tarikyasar.curmin.utils.DateUtils
import com.tarikyasar.curmin.utils.DatesInMs
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import java.util.*
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

        onIntent(Intent.GetSymbols)
    }

    fun resetError() {
        updateUiState {
            copy(error = null)
        }
    }

    override fun onIntent(intent: Intent) {
        when (intent) {
            Intent.GetSymbols -> {
                withUseCaseScope {
                    val symbols = getCurrencySymbolsUseCase()

                    updateUiState {
                        copy(
                            symbols = symbols
                        )
                    }

                    onIntent(Intent.GetCurrencies(false))
                }
            }

            Intent.GetAskToRemoveItemParameter -> {
                updateUiState {
                    copy(
                        askToRemoveItemParameter = preferenceManager.getPreference()
                    )
                }
            }

            is Intent.GetCurrencies -> {
                withUseCaseScope {
                    val currenciesWatchlistItems = getCurrencyWatchlistItems()

                    if (intent.forceRefresh) {
                        currentUiState.currencies.forEach { currencyWatchlistItemData ->
                            val todayInMs = System.currentTimeMillis()

                            onIntent(
                                Intent.GetCurrencyFluctuation(
                                    startDate = DateUtils.formatTime(todayInMs - DatesInMs.DAY.value),
                                    endDate = DateUtils.formatTime(todayInMs),
                                    baseCurrencyCode = currencyWatchlistItemData.baseCurrencyCode
                                        ?: "USD",
                                    targetCurrencyCode = currencyWatchlistItemData.targetCurrencyCode
                                        ?: "TRY",
                                    currencyWatchlistItemData = currencyWatchlistItemData
                                )
                            )
                        }
                    } else {
                        updateUiState {
                            copy(currencies = currenciesWatchlistItems)
                        }
                    }
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
                    updateCurrencyWatchlistItemUseCase(intent.currencyWatchlistItem)

                    onIntent(Intent.GetCurrencies(false))
                }
            }

            is Intent.InsertCurrencyWatchlistItem -> {
                withUseCaseScope {
                    insertCurrencyWatchlistItemUseCase(intent.currencyWatchlistItem)

                    updateUiState {
                        copy(
                            currencies = currentUiState.currencies + intent.currencyWatchlistItem
                        )
                    }
                }
            }

            is Intent.GetCurrencyFluctuation -> {
                withUseCaseScope {
                    val currencyFluctuations = getCurrencyFluctuationUseCase(
                        startDate = intent.startDate,
                        endDate = intent.endDate,
                        baseCurrencyCode = intent.baseCurrencyCode,
                        targetCurrencyCode = intent.targetCurrencyCode
                    )

                    onIntent(
                        Intent.UpdateCurrencyWatchlistItemData(
                            intent.currencyWatchlistItemData.copy(
                                rate = currencyFluctuations.endRate,
                                date = DateUtils.formatTime(LocalDateTime.now()),
                                change = currencyFluctuations.change
                            )
                        )
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

                    onIntent(
                        Intent.InsertCurrencyWatchlistItem(
                            CurrencyWatchlistItemData(
                                uid = UUID.randomUUID().toString(),
                                baseCurrencyCode = intent.baseCurrencyCode,
                                targetCurrencyCode = intent.targetCurrencyCode,
                                rate = currencyFluctuation.endRate,
                                date = DateUtils.formatTime(LocalDateTime.now()),
                                change = currencyFluctuation.change
                            )
                        )
                    )
                }
            }
        }
    }
}