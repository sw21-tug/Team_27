package com.swtug.anticovid.view.changePassword

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.content.res.ResourcesCompat
import com.swtug.anticovid.R
import com.swtug.anticovid.models.User
import com.swtug.anticovid.repositories.PreferencesRepo
import com.swtug.anticovid.view.BaseFragment


class ChangePassword : BaseFragment() {
    private lateinit var btnChangePassword: Button
    private lateinit var editTextCurrentPassword: EditText
    private lateinit var editTextNewPassword: EditText
    private lateinit var editTextRepeatPassword: EditText

    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_change_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFields(view)
        bindFields()
        initListeners()

        user = PreferencesRepo.getUser(requireContext())!!

    }


    private fun initFields(view: View) {
        btnChangePassword = view.findViewById(R.id.btnChangePassword)
        editTextCurrentPassword = view.findViewById(R.id.editTextOldPassword)
        editTextNewPassword = view.findViewById(R.id.editTextNewPassword)
        editTextRepeatPassword = view.findViewById(R.id.editTextRepeatPassword)
    }

    private fun bindFields() {


    }


    private fun initListeners() {
        editTextCurrentPassword.afterTextChanged {

            if (user.password == it)
                setTick(editTextCurrentPassword)
            else
                setError(editTextCurrentPassword, getString(R.string.wrong_password))


        }



        editTextNewPassword.afterTextChanged {
            if (it.length < 8 || it == user.password)
                setError(editTextNewPassword, getString(R.string.password_does_not_match_criteria))
            else
                setTick(editTextNewPassword)


        }

        editTextRepeatPassword.afterTextChanged {
            if (it == user.password && it == editTextNewPassword.text.toString())
                setTick(editTextRepeatPassword)
            else
                setError(editTextRepeatPassword, getString(R.string.passwords_do_not_match))

        }

        btnChangePassword.setOnClickListener {
            //TODO

            //findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
        }
    }

    private fun setError(editText: EditText, error: String) {
        val icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_error_24, null)

        icon?.setBounds(
            0, 0,
            icon.intrinsicWidth,
            icon.intrinsicHeight
        )
        editText.setError(error, icon);
    }

    private fun setTick(editText: EditText) {
        val icon = ResourcesCompat.getDrawable(
            resources,
            R.drawable.ic_baseline_check_circle_24,
            null
        )

        icon?.setBounds(
            0, 0,
            icon.intrinsicWidth,
            icon.intrinsicHeight
        )
        editText.setCompoundDrawables(null, null, icon, null)

    }

    private fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }
        })
    }

    private fun EditText.setDrawables(start: Drawable?, top: Drawable?, end: Drawable?, buttom: Drawable?) {
        setCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, buttom)
    }


}


