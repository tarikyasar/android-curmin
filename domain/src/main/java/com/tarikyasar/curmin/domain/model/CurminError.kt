package com.tarikyasar.curmin.domain.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.tarikyasar.curmin.domain.R
import java.io.Serializable

data class CurminError(
    val errorCode: Int?,
    val message: String?,
    val errorType: CurminErrorType
) : Serializable {

    @Composable
    fun getErrorMessage(): String {
        return when (errorType) {
            CurminErrorType.API_ERROR -> {
                val context = LocalContext.current
                val resourceName = "api_error_${this.errorCode}"
                val stringResourceId =
                    context.resources.getIdentifier(resourceName, "string", context.packageName)

                return when (stringResourceId) {
                    0 -> {
                        stringResource(id = R.string.unexpected_error)
                    }
                    else -> {
                        stringResource(id = stringResourceId)
                    }
                }
            }
            CurminErrorType.NETWORK_ERROR -> {
                stringResource(id = R.string.network_error)
            }
            CurminErrorType.DATABASE_ERROR -> {
                stringResource(id = R.string.database_error)
            }
        }
    }
}

enum class CurminErrorType {
    API_ERROR,
    NETWORK_ERROR,
    DATABASE_ERROR;
}
