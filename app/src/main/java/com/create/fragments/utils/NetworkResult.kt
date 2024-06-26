package com.create.fragments.utils

sealed class NetworkResult <T>(val data:T? = null , val
message: String?=null ) {

    class Sucess<T>(data: T? = null):NetworkResult<T>(data)
    class Error<T>(message: String? , data: T? = null):NetworkResult<T>(data , message)
    class Loading<T>():NetworkResult<T>()

}