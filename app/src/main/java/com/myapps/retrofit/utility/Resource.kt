package com.myapps.retrofit.utility

import android.os.Message

sealed  class Resource<out T> {
    data class Success<out T>(val data: T?):Resource<T>()
    data class Error(val message: String):Resource<Nothing>()
    object Empty:Resource<Nothing>()
}
