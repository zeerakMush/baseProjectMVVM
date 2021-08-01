package com.zk.base_project.utils

import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber

object PhoneNumberUtilsSmartConnect {
    fun getCountryCode(phonenumber: String?): Pair<Int, String?> {
        val phonenumberWithPlus = appendPlus(phonenumber)
        val phoneNumberUtil = PhoneNumberUtil.getInstance()
        val numberProto: Phonenumber.PhoneNumber = try {
            phoneNumberUtil.parse(phonenumberWithPlus, "")
        } catch (e: Exception) {
            return Pair(-1, null)
        }

        return Pair(
            numberProto.countryCode,
            numberProto.nationalNumber.toString()
        )
    }

    fun isValidPhoneNumber(contact: String?): Boolean? {
        val phonenumberWithPlus = appendPlus(contact)

        val phoneNumberUtil = PhoneNumberUtil.getInstance()
        val countryCode = getCountryCode(phonenumberWithPlus)
        var phoneNumber: Phonenumber.PhoneNumber? = null
        var finalNumber: String? = null
        val isoCode = phoneNumberUtil.getRegionCodeForCountryCode(countryCode.first)
        var isValid = false
        var isMobile: PhoneNumberUtil.PhoneNumberType? = null
        try {
            phoneNumber = phoneNumberUtil.parse(phonenumberWithPlus, isoCode)
            isValid = phoneNumberUtil.isValidNumber(phoneNumber)
            isMobile = phoneNumberUtil.getNumberType(phoneNumber)
        } catch (e: NumberParseException) {
            e.printStackTrace()
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
        return isValid
        /*  if (isValid
                  && (PhoneNumberType.MOBILE == isMobile || PhoneNumberType.FIXED_LINE_OR_MOBILE == isMobile)) {
              finalNumber = phoneNumberUtil.format(phoneNumber,
                      PhoneNumberFormat.E164).substring(1)
          }
          return finalNumber*/
    }

    fun appendPlus(phonenumber: String?): String {
        return if (phonenumber?.startsWith("+") == true) phonenumber else "+ $phonenumber"
    }
}