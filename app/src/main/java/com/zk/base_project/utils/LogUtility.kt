package com.zk.base_project.utils

import android.util.Log

/**
 * Created by ZeeraMu on 2/23/2017 android
 */
object LogUtility {
    sealed class LogTag(val tagStr: String) {
        object LogDefault : LogTag("smart-connect")
    }

    private const val SHOULD_SHOW_LOGS = true

    fun errorLog(message: String?, tag: LogTag = LogTag.LogDefault) {
        if (SHOULD_SHOW_LOGS) Log.e(tag.tagStr, message ?: "error message is null")
    }

    fun debugLog(message: String, tag: LogTag = LogTag.LogDefault) {
        if (SHOULD_SHOW_LOGS) Log.d(tag.tagStr, info + message)
    }

    fun infoLog(message: String, tag: LogTag = LogTag.LogDefault) {
        Log.d(tag.tagStr, message)
    }

    fun debugLog(message: String, vararg value: String, tag: LogTag = LogTag.LogDefault) {
        var completeValue = ""
        for (s in value) {
            completeValue += "$s \n"
        }
        if (SHOULD_SHOW_LOGS) Log.d(tag.tagStr, "$info$message--->$completeValue")
    }

    fun horribleSituationLog(message: String?, tag: LogTag = LogTag.LogDefault) {
        if (SHOULD_SHOW_LOGS) Log.wtf(tag.tagStr, message)
    }

    private val info: String
        get() {
            val stackTrace = Exception().stackTrace[3]
            var fileName = stackTrace.fileName
            if (fileName == null) fileName = ""
            return (stackTrace.methodName + " (" + fileName + ":"
                    + stackTrace.lineNumber + ") ")
        }


}