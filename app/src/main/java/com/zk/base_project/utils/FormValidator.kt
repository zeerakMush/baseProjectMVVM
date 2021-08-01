package com.zk.base_project.utils

import android.content.Context
import android.util.Patterns
import com.zk.base_project.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class FormValidator @Inject constructor(
        @ApplicationContext private val context: Context
) {

    fun emptyValidate(fullName: String, fieldError: String?, confirmPassword: String?): String? {
        return when {
            fullName.isEmpty() -> fieldError
            else -> null
        }
    }

    fun emailValidate(email: String, fieldError: String?, confirmPassword: String?): String? {
        return when {
            email.isEmpty() -> context.getString(R.string.error_empty_email)
            !Patterns.EMAIL_ADDRESS.matcher(email)
                    .matches() -> context.getString(R.string.error_invalid_email)
            else -> null
        }
    }

    fun phoneNumberValidate(phoneNumber: String, fieldError: String?, confirmPassword: String?): String? {
        return when {
            phoneNumber.isEmpty() -> context.getString(R.string.error_empty_phone)
            !(PhoneNumberUtilsSmartConnect.isValidPhoneNumber(phoneNumber)
                    ?: false )-> context.getString(R.string.error_invalid_phone)
            else -> null
        }
    }

    fun passwordValidate(password: String, fieldError: String?, confirmPassword: String?): String? {
        return when {
            password.isEmpty() -> context.getString(R.string.error_empty_password)
            !Regex("^(?=.*?[A-Z])(?=(.*[a-z]){1,})(?=(.*[\\d]){1,})(?=(.*[\\W]){1,})(?!.*\\s).{8,}\$")
                .matches(password)->"Password must contain at least eight characters, one number, one special character, one lowercase and one uppercase letter"
            password.length < 8 -> context.getString(R.string.error_short_password)
            else -> null
        }
    }

    fun comparePassword(password: String, fieldError: String?, confirmPassword: String?): String? {
        return when {
            confirmPassword != password || confirmPassword.isEmpty() -> context.getString(R.string.error_password_not_match)
            else -> null
        }
    }

    companion object {

    }
}
