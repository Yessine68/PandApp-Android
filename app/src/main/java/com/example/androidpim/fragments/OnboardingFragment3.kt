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

class OnboardingFragment3 : Fragment() {
    lateinit var registerClubShared: SharedPreferences


    lateinit var clubName:EditText
    lateinit var clubOwner:EditText
    lateinit var login:EditText
    lateinit var password:EditText


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootLayout: View =
            inflater.inflate(R.layout.fragment_onboarding3, container, false)



        return rootLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)

        registerClubShared = requireActivity().getSharedPreferences("RegisterClub", Context.MODE_PRIVATE)
        clubName = view.findViewById(R.id.clubName)
        clubOwner = view.findViewById(R.id.clubOwner)
        login = view.findViewById(R.id.login)
        password = view.findViewById(R.id.password)



        clubName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                registerClubShared.edit().apply {

                    putString("clubName", clubName.text.toString())
                    putString("clubOwner", clubOwner.text.toString())
                    putString("login", login.text.toString())
                    putString("password", password.text.toString())
                }.apply()
            }

        })


        clubOwner.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                registerClubShared.edit().apply {

                    putString("clubName", clubName.text.toString())
                    putString("clubOwner", clubOwner.text.toString())
                    putString("login", login.text.toString())
                    putString("password", password.text.toString())
                }.apply()
            }

        })
        login.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                registerClubShared.edit().apply {

                    putString("clubName", clubName.text.toString())
                    putString("clubOwner", clubOwner.text.toString())
                    putString("login", login.text.toString())
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
                registerClubShared.edit().apply {

                    putString("clubName", clubName.text.toString())
                    putString("clubOwner", clubOwner.text.toString())
                    putString("login", login.text.toString())
                    putString("password", password.text.toString())
                }.apply()
            }

        })

    }


}
