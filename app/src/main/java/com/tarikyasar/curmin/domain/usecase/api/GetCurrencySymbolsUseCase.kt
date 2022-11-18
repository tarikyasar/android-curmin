package com.tarikyasar.curmin.domain.usecase.api

import com.tarikyasar.curmin.common.Resource
import com.tarikyasar.curmin.data.repository.currency.mapper.toCurrencySymbol
import com.tarikyasar.curmin.domain.SymbolListManager
import com.tarikyasar.curmin.domain.model.Symbol
import com.tarikyasar.curmin.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCurrencySymbolsUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {
    operator fun invoke(): Flow<Resource<List<Symbol>>> = flow {
        try {
            emit(Resource.Loading<List<Symbol>>())
            var currencySymbols = SymbolListManager.symbols

            if (currencySymbols.isEmpty()) {
                currencySymbols = repository.getCurrencySymbols().toCurrencySymbol()
            }

            emit(Resource.Success<List<Symbol>>(currencySymbols))
        } catch (e: HttpException) {
            emit(
                Resource.Error<List<Symbol>>(
                    e.localizedMessage ?: "An unexpected error occurred."
                )
            )
        } catch (e: IOException) {
            emit(Resource.Error<List<Symbol>>("Couldn't reach server. Check your internet connection."))
        }
    }
}