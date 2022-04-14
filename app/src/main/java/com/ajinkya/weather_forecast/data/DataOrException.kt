package com.ajinkya.weather_forecast.data

class DataOrException<T, Boolean, E : Exception>(
    var data: T? = null,
    var loading: kotlin.Boolean? = null,
    var exception: E? = null,
)


/*data class DataOrException<out T>(val status:Status,val data:T? , val exception: Exception?) {
    companion object{
        fun <T> success(data: T?):DataOrException<T>{
            return DataOrException(Status.SUCCESS,data,null)
        }
        fun <T> error(ex: Exception?,data: T?):DataOrException<T>{
            return DataOrException(Status.ERROR,data, ex)
        }
        fun <T> loading(data: T?):DataOrException<T>{
            return DataOrException(Status.LOADING,data,null)
        }
    }

}*/