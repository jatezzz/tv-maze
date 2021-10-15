package com.jatezzz.tvmaze.common.retrofit


/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class ResultType<out R> {

  data class Success<out T>(val data: T) : ResultType<T>()
  data class Error(val exception: Exception) : ResultType<Nothing>()
  object Loading : ResultType<Nothing>()
  object Complete : ResultType<Nothing>()

  override fun toString(): String {
    return when (this) {
      is Complete -> "Complete"
      is Success<*> -> "Success[data=$data]"
      is Error -> "Error[exception=$exception]"
      Loading -> "Loading"
    }
  }
}

/**
 * `true` if [ResultType] is of type [ResultType.Success] & holds non-null [ResultType.Success.data].
 */
val ResultType<*>.succeeded
  get() = (this is ResultType.Success)
          || this is ResultType.Complete
