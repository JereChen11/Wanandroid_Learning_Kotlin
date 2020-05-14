package com.jere.wanandroid_learning_kotlin.model

import com.jere.wanandroid_learning_kotlin.model.api.AbstractRetrofitCallback
import com.jere.wanandroid_learning_kotlin.model.api.ApiWrapper

object CollectionRepository {

    fun collectArticle(articleId: Int, listener: CollectOrUnCollectListener) {
        ApiWrapper.getInstance()?.collectArticle(articleId)
            ?.enqueue(object : AbstractRetrofitCallback() {

                override fun getSuccessful(responseBody: String) {
                    listener.isSuccessful(true)
                }

                override fun getFailed(failedMsg: String) {
                    listener.isSuccessful(false)
                }
            })
    }

    fun unCollectArticle(articleId: Int, listener: CollectOrUnCollectListener) {
        ApiWrapper.getInstance()?.unCollectArticle(articleId)
            ?.enqueue(object : AbstractRetrofitCallback() {

                override fun getSuccessful(responseBody: String) {
                    listener.isSuccessful(true)
                }

                override fun getFailed(failedMsg: String) {
                    listener.isSuccessful(false)
                }
            })
    }

    interface CollectOrUnCollectListener {
        fun isSuccessful(isSuccess: Boolean)
    }
}