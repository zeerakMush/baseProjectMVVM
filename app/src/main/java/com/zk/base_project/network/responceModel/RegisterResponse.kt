package com.zk.base_project.network.responceModel

import com.squareup.moshi.Json

data class RegisterResponse(
        @field:Json(name = "success")
        val success: String,
        @field:Json(name = "userId")
        val userId: String,
        @field:Json(name = "accessToken")
        val accessToken: String,
        @field:Json(name = "dateCreated")
        val dateCreated: String,
        @field:Json(name = "expiryDate")
        val expiryDate: String,
        @field:Json(name = "name")
        val name: String,
        @field:Json(name = "isPublic")
        val isPublic: String,
        @field:Json(name = "sendContactSuggestions")
        val sendContactSuggestions: String,
        @field:Json(name = "userTypeId")
        val userTypeId: String,
        @field:Json(name = "isVerified")
        val isVerified: String,
        @field:Json(name = "userHasSyncedContacts")
        val userHasSyncedContacts:String,
        @field:Json(name="info")
        val info:String?,
        @field:Json(name="failure")
        val failure:String?

)

