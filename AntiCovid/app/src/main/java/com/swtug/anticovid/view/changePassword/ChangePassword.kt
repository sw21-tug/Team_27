package com.swtug.anticovid.view.changePassword

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.findNavController
import com.swtug.anticovid.R
import com.swtug.anticovid.models.User
import com.swtug.anticovid.repositories.FirebaseListener
import com.swtug.anticovid.repositories.FirebaseRepo
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
        initListeners()

        user = PreferencesRepo.getUser(requireContext())!!

    }


    private fun initFields(view: View) {
        btnChangePassword = view.findViewById(R.id.btnChangePassword)
        editTextCurrentPassword = view.findViewById(R.id.editTextOldPassword)
        editTextNewPassword = view.findViewById(R.id.editTextNewPassword)
        editTextRepeatPassword = view.findViewById(R.id.editTextRepeatPassword)

    }

    private fun initListeners() {
        editTextCurrentPassword.afterTextChanged {
            val value = editTextCurrentPassword.text.toString()

            if (user.password == value)
                setTick(editTextCurrentPassword)
            else
                setError(editTextCurrentPassword, getString(R.string.wrong_password))
        }

        editTextNewPassword.afterTextChanged {
            val value = editTextNewPassword.text.toString()

            if (value.length < 8 || value == user.password)
                setError(editTextNewPassword, getString(R.string.password_does_not_match_criteria))
            else
                setTick(editTextNewPassword)
        }

        editTextRepeatPassword.afterTextChanged {
            if (editTextRepeatPassword.text.toString() == editTextNewPassword.text.toString())
                setTick(editTextRepeatPassword)
            else
                setError(editTextRepeatPassword, getString(R.string.passwords_do_not_match))
        }

        btnChangePassword.setOnClickListener {
            val newPassword = editTextNewPassword.text.toString()
            val repeatPassword = editTextRepeatPassword.text.toString()
            val currentPassword = editTextCurrentPassword.text.toString()

            if ((newPassword == repeatPassword) && (currentPassword == user.password)) {
                val user = getNewUser(editTextNewPassword.text.toString())

                saveNewPasswordFirebase(user)
            }else
                Toast.makeText(
                    requireContext(),
                    requireContext().getString(R.string.error_fill_out_filds),
                    Toast.LENGTH_LONG
                ).show()
        }
    }


    private fun saveNewPasswordFirebase(user: User) {
        FirebaseRepo.updateUser(user, object : FirebaseListener {
            override fun onSuccess(user: User?) {
                btnChangePassword.isEnabled = true

                user?.let { PreferencesRepo.saveUser(requireContext(), it) }
                findNavController().navigate(R.id.action_changePasswordFragment_to_profileFragment)

                Toast.makeText(
                    requireContext(),
                    requireContext().getString(R.string.changes_saved),
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onStart() {
                btnChangePassword.isEnabled = false
            }

            override fun onFailure() {
                btnChangePassword.isEnabled = true

                Toast.makeText(
                    requireContext(),
                    requireContext().getString(R.string.error_firebase_communication),
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun getNewUser(password: String): User {
        return User(
            user.id,
            user.name,
            user.surname,
            user.email,
            user.address,
            user.secid,
            user.phonenumber,
            password
        )
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
        editText.setCompoundDrawables(null, null, null, null)
        editText.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null)

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

}


