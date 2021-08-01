package com.zk.base_project.network.interceptor

import com.zk.base_project.utils.PreferencesHelper
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(private val pref: PreferencesHelper) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val headers: Headers = chain.request().headers
        //val authenticate: Boolean = headers.values(KEY_AUTHENTICATE).isNotEmpty()
       // val refreshTokenRequest: Boolean = headers.values(KEY_REFRESH_TOKEN_REQUEST).isNotEmpty()
        val actualRequest: Request.Builder = chain.request().newBuilder()
        pref.getToken()?.let(actualRequest::addAuthToken)
        val response = chain.proceed(actualRequest.build())
        return response
    }
}

private fun Request.Builder.addAuthToken(token: String): Request.Builder {
    return header("access_token", token)
}

private fun Request.Builder.addUserName(loggedInUserId: String): Request.Builder {
    return header("logged_in_user_id", loggedInUserId)
}