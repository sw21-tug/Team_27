package com.swtug.anticovid.Register

import android.content.Context
import com.swtug.anticovid.Database.DatabaseSchema

class Register(private val context: Context) {

    private lateinit var databaseSchema: DatabaseSchema

    fun register(name: String, surname: String, email: String, address: String, secid: String,
        phonenumber: String, password: String) : Boolean
    {
        if(name.isEmpty() || surname.isEmpty() || email.isEmpty() || address.isEmpty()
          || secid.isEmpty() || phonenumber.isEmpty() || password.isEmpty())
        {
            return false
        }

        if(password.length < 8)
        {
            return false
        }

        if(databaseSchema.alreadyExistUser(email) == true)
        {
            return false;
        }

        return true
    }
}