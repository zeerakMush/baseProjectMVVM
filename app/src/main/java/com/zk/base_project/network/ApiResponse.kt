package com.zk.base_project.network

sealed class ApiResponse<T> {
    data class Success<T>(val value: T) : ApiResponse<T>()
    data class Failed<T>(val message: String?, val errorCode: Int) : ApiResponse<T>()
}