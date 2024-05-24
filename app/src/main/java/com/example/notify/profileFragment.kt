package com.example.notify

import android.app.Activity.RESULT_OK
import android.content.Intent


import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

class profileFragment:Fragment() {
    private lateinit var photo: ImageView
    private lateinit var name: TextInputEditText
    private lateinit var mail: TextInputEditText
    private lateinit var phone: TextInputEditText
    private lateinit var button: MaterialButton
    private lateinit var imageuri: Uri
    private val REQ_CODE = 1000

    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.profile, container, false)

        photo = view.findViewById(R.id.user_image)
        name = view.findViewById(R.id.name)
        mail = view.findViewById(R.id.mail)
        phone = view.findViewById(R.id.phone)
        button = view.findViewById(R.id.save)

        photo.setOnClickListener() {
            val intent = Intent(Intent.ACTION_PICK)
            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            startActivityForResult(intent, REQ_CODE)
        }

        button.setOnClickListener() {
            val username = name.text.toString().trim()
            val usermail = mail.text.toString().trim()
            val usermobile = phone.text.toString().trim()

            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(usermail) || TextUtils.isEmpty(
                    usermobile
                )
            ) {
                name.error = "fill all details"
                name.requestFocus()
            } else {
                val data = data(email = usermail, name = username, mobile = usermobile)
                adddata(data)

            }

        }




        return view
    }


    private fun adddata(data: data) {
        val details = firestore.collection("details").document(UUID.randomUUID().toString())
        details.set(data).addOnSuccessListener {
            name.setText("")
            mail.setText("")
            phone.setText("")
            Toast.makeText(activity, "Data stored to firestore", Toast.LENGTH_SHORT).show()

        }
            .addOnFailureListener() {
                Toast.makeText(activity, "Error storing data", Toast.LENGTH_SHORT).show()
            }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {

                if (requestCode == REQ_CODE) {
                    imageuri = data?.data!!

                    imageuri?.let {
                        photo.setImageURI(it)
                    }

                }


        }


    }
}