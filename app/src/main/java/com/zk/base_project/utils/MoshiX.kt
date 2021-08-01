package com.zk.base_project.utils

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

inline fun <reified T> Moshi.toJson(value: T): String {
    val jsonAdapter: JsonAdapter<T> = adapter(T::class.java)
    return jsonAdapter.toJson(value)
}

inline fun <reified T> Moshi.fromJson(json: String): T? {
    val jsonAdapter: JsonAdapter<T> = adapter(T::class.java)
    return jsonAdapter.fromJson(json)
}