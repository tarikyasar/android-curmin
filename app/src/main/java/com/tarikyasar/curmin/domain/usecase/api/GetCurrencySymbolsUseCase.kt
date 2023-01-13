package com.tarikyasar.curmin.domain.usecase.api

import com.tarikyasar.curmin.common.Resource
import com.tarikyasar.curmin.data.repository.currency.mapper.toCurrencySymbol
import com.tarikyasar.curmin.domain.SymbolListManager
import com.tarikyasar.curmin.domain.model.CurminError
import com.tarikyasar.curmin.domain.model.CurminErrorType
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

            if (SymbolListManager.symbols.isEmpty()) {
                SymbolListManager.symbols = repository.getCurrencySymbols().toCurrencySymbol()
            }

            emit(Resource.Success<List<Symbol>>(SymbolListManager.symbols))
        } catch (e: HttpException) {
            emit(
                Resource.Error<List<Symbol>>(
                    CurminError(
                        e.code(),
                        null,
                        CurminErrorType.API_ERROR
                    )
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error<List<Symbol>>(
                    CurminError(
                        null,
                        null,
                        CurminErrorType.NETWORK_ERROR
                    )
                )
            )
        }
    }
}