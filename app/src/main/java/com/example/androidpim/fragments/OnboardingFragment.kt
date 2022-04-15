package com.example.androidpim.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.example.androidpim.R
import com.example.androidpim.models.Chkaf

import kotlinx.android.synthetic.main.fragment_onboarding1.view.*

class OnboardingFragment : Fragment() {
    lateinit var registerShared: SharedPreferences
    lateinit var firstname:EditText
    lateinit var lastName:EditText
    lateinit var email:EditText
    lateinit var identifant:EditText
    lateinit var password:EditText
    lateinit var password2:EditText
    lateinit var dataPasser: Chkaf
/*
    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataPasser = context as Chkaf
    }
    */

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootLayout: View =
            inflater.inflate(R.layout.fragment_onboarding1, container, false)



        return rootLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)

        registerShared = requireActivity().getSharedPreferences("Register", Context.MODE_PRIVATE)
        firstname = view.findViewById(R.id.firstname)
        lastName = view.findViewById(R.id.lastName)
        email = view.findViewById(R.id.email)
        identifant = view.findViewById(R.id.identifant)
        password = view.findViewById(R.id.password)
        password2 = view.findViewById(R.id.password2)


        firstname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                registerShared.edit().apply {

                    putString("firstname", firstname.text.toString())
                    putString("lastName", lastName.text.toString())
                    putString("email", email.text.toString())
                    putString("identifant", identifant.text.toString())
                    putString("password", password.text.toString())
                }.apply()
            }

        })


        firstname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                registerShared.edit().apply {

                    putString("firstname", firstname.text.toString())
                    putString("lastName", lastName.text.toString())
                    putString("email", email.text.toString())
                    putString("identifant", identifant.text.toString())
                    putString("password", password.text.toString())
                }.apply()
            }

        })
        lastName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                registerShared.edit().apply {

                    putString("firstname", firstname.text.toString())
                    putString("lastName", lastName.text.toString())
                    putString("email", email.text.toString())
                    putString("identifant", identifant.text.toString())
                    putString("password", password.text.toString())
                }.apply()
            }

        })
        email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                registerShared.edit().apply {

                    putString("firstname", firstname.text.toString())
                    putString("lastName", lastName.text.toString())
                    putString("email", email.text.toString())
                    putString("identifant", identifant.text.toString())
                    putString("password", password.text.toString())
                }.apply()
            }

        })
        identifant.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                registerShared.edit().apply {

                    putString("firstname", firstname.text.toString())
                    putString("lastName", lastName.text.toString())
                    putString("email", email.text.toString())
                    putString("identifant", identifant.text.toString())
                    putString("password", password.text.toString())
                }.apply()
            }

        })
        password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                registerShared.edit().apply {

                    putString("firstname", firstname.text.toString())
                    putString("lastName", lastName.text.toString())
                    putString("email", email.text.toString())
                    putString("identifant", identifant.text.toString())
                    putString("password", password.text.toString())
                }.apply()
            }

        })
        password2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                registerShared.edit().apply {

                    putString("firstname", firstname.text.toString())
                    putString("lastName", lastName.text.toString())
                    putString("email", email.text.toString())
                    putString("identifant", identifant.text.toString())
                    putString("password", password.text.toString())
                }.apply()
            }

        })






    }


}
