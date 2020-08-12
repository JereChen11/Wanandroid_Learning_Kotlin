package com.jere.wanandroid_learning_kotlin.model

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import java.io.IOException

open class BaseRepository {

    suspend fun <T : Any> safeApiCall(
        requestName: String,
        call: suspend () -> BaseResult<T>
    ): BaseResult<T> {
        return try {
            call()
        } catch (e: Exception) {
            //请求失败
            Log.e("jereTest", "request $requestName failed: ${e.message}")
            //抛出一个IO异常
            BaseResult.Error(IOException("jereTest request failed", e))
        }
    }

    suspend fun <T : Any> executeResponse(
        response: BaseResponse<T>,
        successBlock: (suspend CoroutineScope.() -> Unit)? = null,
        errorBlock: (suspend CoroutineScope.() -> Unit)? = null
    ): BaseResult<T> {
        return coroutineScope {
            if (response.errorCode == 0) {
                successBlock?.let { it() }
                BaseResult.Success(response.data)
            } else {
                errorBlock?.let { it() }
                BaseResult.Error(IOException(response.errorMsg))
            }
        }
    }
}