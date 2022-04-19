package com.example.androidpim.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.example.androidpim.R
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.fragment_onboarding2.view.*

class OnboardingFragment2 : Fragment() {
    lateinit var registerShared: SharedPreferences
  /*  private lateinit var title: String
    private lateinit var description: String
    private var imageResource = 0
    private lateinit var tvTitle: AppCompatTextView
    private lateinit var tvDescription: AppCompatTextView
    private lateinit var image: LottieAnimationView
    /
   */


    lateinit var phone: EditText
    lateinit var className: EditText
    lateinit var description: EditText
     var imagepick: ImageView?=null
    lateinit var pickButton: Button
    private var selectedImageUri: Uri? = null




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootLayout: View =
            inflater.inflate(R.layout.fragment_onboarding2, container, false)

        return rootLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerShared = requireActivity().getSharedPreferences("Register", Context.MODE_PRIVATE)
        phone = view.findViewById(R.id.phone)
        className = view.findViewById(R.id.className)
        description = view.findViewById(R.id.description)
        imagepick = view.findViewById(R.id.imagepick)
        pickButton = view.findViewById(R.id.pickButton)

        phone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                registerShared.edit().apply {

                    putString("phone", phone.text.toString())
                    putString("className", className.text.toString())
                    putString("description", description.text.toString())
                    putString("imageUrl", selectedImageUri.toString())
                }.apply()
            }

        })

        className.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(p0: Editable?) {
                    registerShared.edit().apply {

                        putString("phone", phone.text.toString())
                        putString("className", className.text.toString())
                        putString("description", description.text.toString())
                        putString("imageUrl", selectedImageUri.toString())
                    }.apply()
                }

            })


        description.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(p0: Editable?) {
                    registerShared.edit().apply {

                        putString("phone", phone.text.toString())
                        putString("className", className.text.toString())
                        putString("description", description.text.toString())
                        putString("imageUrl", selectedImageUri.toString())
                    }.apply()
                }

            })

        pickButton.setOnClickListener(

            View.OnClickListener {
                ImagePicker.with(this)
                    .crop()	    			//Crop image(Optional), Check Customization for more option
                    .compress(1024)			//Final image size will be less than 1 MB(Optional)
                    .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                    .start()
            })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        registerShared = requireActivity().getSharedPreferences("Register", Context.MODE_PRIVATE)
        if (resultCode == Activity.RESULT_OK && requestCode == ImagePicker.REQUEST_CODE) {
            selectedImageUri = data?.data
            imagepick?.setImageURI(selectedImageUri)
            registerShared.edit().apply {

                putString("phone", phone.text.toString())
                putString("className", className.text.toString())
                putString("description", description.text.toString())
                putString("imageUrl", selectedImageUri.toString())
            }.apply()


        }
    }


}