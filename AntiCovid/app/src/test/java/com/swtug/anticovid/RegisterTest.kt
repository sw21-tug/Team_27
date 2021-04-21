package com.swtug.anticovid

import com.swtug.anticovid.Register.Register
import org.junit.Test

import org.junit.Assert.*

class RegisterTest {
    @Test
    fun registerValidityCheckWrong()
    {
        val valid = Register.stringValidityCheck("aaa", "bbb", "aaa", "", "", "", "")
        assertEquals(false, valid)
    }

    @Test
    fun registerValidityCheckWrongPassword()
    {
        val valid = Register.stringValidityCheck("aaa", "bbb", "aaa", "aaa", "aaa", "aaa", "asd")
        assertEquals(false, valid)
    }

    @Test
    fun registerValidityCheckTrue()
    {
        val valid = Register.stringValidityCheck("aaa", "bbb", "aaa", "aaa", "aaa", "aaa", "asdasdasd")
        assertEquals(true, valid)
    }


}