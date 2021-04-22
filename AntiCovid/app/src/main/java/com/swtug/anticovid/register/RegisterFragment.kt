package com.swtug.anticovid.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.swtug.anticovid.BaseFragment
import com.swtug.anticovid.R

class RegisterFragment : BaseFragment()
{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.fragment_register, null)
    }
}


/*class Register(private val context: Context): AppCompatActivity(), View.OnClickListener{

    private val activity = this@Register

    private lateinit var registerButton: Button
    private var databaseSchema = DatabaseSchema(context)

    fun register(name: String, surname: String, email: String, address: String, secid: String,
        phonenumber: String, password: String) : Boolean
    {
        if(!stringValidityCheck(name, surname, email, address, secid, phonenumber, password))
        {
            return false
        }

        if(databaseSchema.alreadyExistUser(email) == true)
        {
            return false
        }

        databaseSchema.newUser(User(-1, name, surname, email, address, secid, phonenumber, password));

        return true
    }

    companion object {
        fun stringValidityCheck(name: String, surname: String, email: String, address: String, secid: String,
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

            return true

        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.fragment_register)
        registerButton = findViewById(R.id.register) as Button
        registerButton!!.setOnClickListener(this)
        Log.d("TAG", "message")

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.register -> register(findViewById<EditText>(R.id.editTextTextPersonName).text.toString(),
                findViewById<EditText>(R.id.editTextTextPersonName2).text.toString(),
                findViewById<EditText>(R.id.username).text.toString(),
                findViewById<EditText>(R.id.editTextTextPersonName3).text.toString(),
                findViewById<EditText>(R.id.editTextTextPersonName4).text.toString(),
                findViewById<EditText>(R.id.phoneNumber).text.toString(),
                findViewById<EditText>(R.id.password).text.toString())
        }
    }





}*/