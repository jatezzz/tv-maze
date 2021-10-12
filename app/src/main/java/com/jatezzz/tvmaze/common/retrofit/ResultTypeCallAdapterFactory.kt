package com.jatezzz.tvmaze.common.retrofit


import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ResultTypeCallAdapterFactory private constructor() : CallAdapter.Factory() {

  override fun get(
    returnType: Type,
    annotations: Array<Annotation>,
    retrofit: Retrofit
  ): CallAdapter<*, *>? {
    if (getRawType(returnType) == Call::class.java) {
      val callType = getParameterUpperBound(0, returnType as ParameterizedType)
      if (getRawType(callType) == ResultType::class.java) {
        val resultType = getParameterUpperBound(0, callType as ParameterizedType)
        return ResultAdapter(resultType)
      }
    }
    return null
  }

  class ResultAdapter(private val type: Type) : CallAdapter<Type, Call<ResultType<Type>>> {

    override fun responseType() = type

    override fun adapt(call: Call<Type>): Call<ResultType<Type>> = ResultCall(call)

  }

  companion object {

    fun create(): CallAdapter.Factory = ResultTypeCallAdapterFactory()

  }

}


