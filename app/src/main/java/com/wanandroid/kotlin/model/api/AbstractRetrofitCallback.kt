package com.wanandroid.kotlin.model.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class AbstractRetrofitCallback : Callback<ResponseBody> {
    abstract fun getSuccessful(responseBody: String)

    abstract fun getFailed(failedMsg: String)

    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
        if (response.code() == 200) {
            val responseBodyString : String = response.body()!!.string()
            getSuccessful(responseBodyString)
        } else {
            getFailed("error response code: " + response.code())
        }
    }

    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
        getFailed(t.message.toString())
    }

}

