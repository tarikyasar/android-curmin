package com.tarikyasar.curmin.presentation.ui.screens.currency.watchlist

import android.os.Build
import androidx.annotation.RequiresApi
import com.tarikyasar.curmin.data.database.model.CurrencyWatchlistItemData
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
) : BaseViewModel<UiState, Intent, Event>(UiState()) {

    override fun onFirstLaunch() {
        super.onFirstLaunch()

        getSymbols()
        getAskToRemoveItemParameter()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrencies(refreshList: Boolean) {
        withUseCaseScope {
            val currenciesWatchlistItems = getCurrencyWatchlistItems()

            if (refreshList) {
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
                    copy(currencies = currenciesWatchlistItems as MutableList<CurrencyWatchlistItemData>)
                }
            }
        }
    }

    fun deleteCurrency(currencyWatchlistItemUid: String) {
        withUseCaseScope {
            deleteCurrencyWatchlistItemUseCase(currencyWatchlistItemUid)

            currentUiState.currencies.remove(currentUiState.currencies.find {
                it.uid == currencyWatchlistItemUid
            })

            updateUiState {
                copy(
                    currencies = currentUiState.currencies
                )
            }
        }
    }

    fun createCurrencyWatchlistItem(
        baseCurrencyCode: String,
        targetCurrencyCode: String
    ) {
        withUseCaseScope {
            val todayInMs = System.currentTimeMillis()

            val currencyFluctuation = getCurrencyFluctuationUseCase(
                startDate = DateUtils.formatTime(todayInMs - DatesInMs.DAY.value),
                endDate = DateUtils.formatTime(todayInMs),
                baseCurrencyCode = baseCurrencyCode,
                targetCurrencyCode = targetCurrencyCode
            )

            insertCurrency(
                CurrencyWatchlistItemData(
                    uid = UUID.randomUUID().toString(),
                    baseCurrencyCode = baseCurrencyCode,
                    targetCurrencyCode = targetCurrencyCode,
                    rate = currencyFluctuation?.endRate ?: 0.0,
                    date = DateUtils.formatTime(LocalDateTime.now()),
                    change = currencyFluctuation?.change
                )
            )
        }
    }

    fun getAskToRemoveItemParameter() {
        updateUiState {
            copy(
                askToRemoveItemParameter = preferenceManager.getPreference()
            )
        }
    }

    private fun getCurrencyFluctuation(
        startDate: String,
        endDate: String,
        baseCurrencyCode: String,
        targetCurrencyCode: String,
        currencyWatchlistItemData: CurrencyWatchlistItemData,
    ) {
        withUseCaseScope {
            val currencyFluctuations = getCurrencyFluctuationUseCase(
                startDate = startDate,
                endDate = endDate,
                baseCurrencyCode = baseCurrencyCode,
                targetCurrencyCode = targetCurrencyCode
            )

            updateCurrencyWatchlistItemData(
                currencyWatchlistItemData.copy(
                    rate = currencyFluctuations?.endRate ?: 0.0,
                    date = DateUtils.formatTime(LocalDateTime.now()),
                    change = currencyFluctuations?.change
                )
            )
        }
    }

    private fun getSymbols() {
        withUseCaseScope {
            val symbols = getCurrencySymbolsUseCase()

            updateUiState {
                copy(
                    symbols = symbols
                )
            }

            getCurrencies(refreshList = false)
        }
    }

    private fun insertCurrency(currencyWatchlistItem: CurrencyWatchlistItemData) {
        withUseCaseScope {
            insertCurrencyWatchlistItemUseCase(currencyWatchlistItem)

            updateUiState {
                copy(
                    currencies = (currentUiState.currencies + currencyWatchlistItem) as MutableList<CurrencyWatchlistItemData>
                )
            }
        }
    }

    private fun updateCurrencyWatchlistItemData(
        currencyWatchlistItem: CurrencyWatchlistItemData
    ) {
        withUseCaseScope {
            updateCurrencyWatchlistItemUseCase(currencyWatchlistItem)

            getCurrencies(false)
        }
    }

    fun resetError() {
        updateUiState {
            copy(error = null)
        }
    }

    override fun onIntent(intent: Intent) {
        TODO("Not yet implemented")
    }
}