package com.zk.base_project

import com.zk.base_project.utils.PhoneNumberUtilsSmartConnect
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(JUnit4::class)
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun validatePhonerNumber(){
        val string = PhoneNumberUtilsSmartConnect.isValidPhoneNumber("+9231128835")
        assertEquals(true,PhoneNumberUtilsSmartConnect.isValidPhoneNumber("+923112883585"))
        assertEquals(true,PhoneNumberUtilsSmartConnect.isValidPhoneNumber("+9202134112496"))
        assertEquals(false,PhoneNumberUtilsSmartConnect.isValidPhoneNumber("+9231128899"))
        assertEquals(false,PhoneNumberUtilsSmartConnect.isValidPhoneNumber("+923411249"))
        print(string)
    }


}