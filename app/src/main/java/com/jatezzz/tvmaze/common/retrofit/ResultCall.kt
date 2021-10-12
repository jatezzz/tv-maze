package com.jatezzz.tvmaze.common.retrofit

import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class ResultCall<T>(proxy: Call<T>) : CallDelegate<T, ResultType<T>>(proxy) {

    override fun enqueueImpl(callback: Callback<ResultType<T>>) = proxy.enqueue(object :
        Callback<T> {

        override fun onResponse(call: Call<T>, response: Response<T>) {
            val body = response.body()
            val resultType: ResultType<T> = if (body != null) {
                ResultType.Success(body)
            } else {
                ResultType.Complete
            }
            callback.onResponse(this@ResultCall, Response.success(resultType))
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            if (call.isCanceled) return
            val failureResult = ResultType.Error(asRetrofitException(t))
            callback.onResponse(this@ResultCall, Response.success(failureResult))
        }

        private fun asRetrofitException(throwable: Throwable): RetrofitException {
            (throwable as? HttpException)?.response()?.let {
                return RetrofitException.httpError(it)
            }
            return when (throwable) {
                is IOException -> {
                    RetrofitException.networkError(throwable)
                }
                else -> {
                    RetrofitException.unexpectedError(throwable)
                }
            }
        }

    })

    override fun cloneImpl() = ResultCall(proxy.clone())

    override fun timeout(): Timeout = proxy.timeout()

}