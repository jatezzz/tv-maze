package com.jatezzz.tvmaze.common.retrofit

import retrofit2.Response

class RetrofitException private constructor(
  message: String?,
  val kind: Kind,
  exception: Throwable?
) : RuntimeException(message, exception) {

  enum class Kind {
    HTTP,
    NETWORK,
    UNEXPECTED
  }

  companion object {

    fun httpError(response: Response<*>): RetrofitException {
      val message = response.code().toString() + " " + response.message()
      return RetrofitException(
        message, Kind.HTTP, null
      )
    }

    fun networkError(exception: Throwable): RetrofitException {
      return RetrofitException(
        exception.message, Kind.NETWORK, exception
      )
    }

    fun unexpectedError(exception: Throwable): RetrofitException {
      return RetrofitException(
        exception.message, Kind.UNEXPECTED, exception
      )
    }
  }
}
