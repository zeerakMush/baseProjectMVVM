package com.zk.base_project.network.requestModel

import com.squareup.moshi.Json
import java.io.File
import java.util.*

class UserAttachment {

    constructor() {
        dateCreated = Date().toString()
    }

    @Json(name = "attachmentLink")
    var attachmentLink: String = ""
        set(value) {
            field = value
            dateModified = Date().toString()
        }

    @Json(name = "id")
    var id: Int = 0
        set(value) {
            field = value
            dateModified = Date().toString()
        }

    @Json(name = "type")
    var type: Int = 0
        set(value) {
            field = value
            dateModified = Date().toString()
        }

    @Json(name = "userId")
    var userId: Int = 0
        set(value) {
            field = value
            dateModified = Date().toString()
        }

    @Json(name = "dateCreated")
    private var dateCreated: String = ""

    @Json(name = "dateModified")
    private var dateModified: String = ""

    @Transient
    var file: File? = null
}