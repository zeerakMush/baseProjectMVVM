package com.zk.base_project.network.repositeries

import com.zk.base_project.network.ApiResponse
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import java.lang.Exception
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class BaseRepository {
    internal inline fun <T> apiCall(call: () -> T): ApiResponse<T> {
        return try {
            ApiResponse.Success(call())
        } catch (e: HttpException) {
            val firstMessage = when (e.code()) {
                in 500..511 -> "Server error, please try again later !"
                else -> getFirstErrorMessage(e)
            }
            ApiResponse.Failed(firstMessage, e.code())
            //TODO: handle network errors properly
        } catch (e: SocketTimeoutException) {
            ApiResponse.Failed(e.message, -0)
        } catch (e: SocketException) {
            ApiResponse.Failed(e.message, -0)
        } catch (e: UnknownHostException) {
            ApiResponse.Failed(e.message, -0)
        }catch (e:Exception){
            ApiResponse.Failed(e.message,-0)
        }
    }

    private fun getFirstErrorMessage(e: HttpException): String? {
        return try {
            val errorJson = e.response()?.errorBody()?.string()?.let(::JSONObject)
            when{
                errorJson?.has("info") ==  true-> errorJson.getString("info")
                errorJson?.has("failure") == true -> errorJson.getString("failure")
                else -> "Unknown error"
            }
        } catch (e: JSONException) {
            null
        }
    }
}