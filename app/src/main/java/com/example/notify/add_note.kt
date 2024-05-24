package com.example.notify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.ImageButton
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class add_note : AppCompatActivity(){


     private lateinit var reference:DatabaseReference
    private lateinit var image:ImageButton
    private lateinit var Title:TextInputEditText
    private lateinit var describe:TextInputEditText
    private lateinit var buttons:MaterialButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_note)


        image=findViewById(R.id.back)
        Title=findViewById(R.id.head)
        describe=findViewById(R.id.body)
        buttons=findViewById(R.id.button)

        reference=FirebaseDatabase.getInstance().getReference("notes")

        // data is in this format structure

        image.setOnClickListener() {
            super.onBackPressed()
        }


        buttons.setOnClickListener(){
            val title=Title.text.toString()
            val body=describe.text.toString()
            if( TextUtils.isEmpty(title)|| TextUtils.isEmpty(body)){
                Title.error="fill both the details"
                Title.requestFocus()
                describe.requestFocus()

            }
            else{
                 val model=model(title,body)
                addnote(model)
            }

        }




}


        private fun addnote(model: model) {
            val notekey = reference.push()
            notekey.setValue(model).addOnSuccessListener {
                    Toast.makeText(this, "Note added, please check notes screen", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(this, "Failed to save note, please check network", Toast.LENGTH_SHORT).show()
                }

        }
}


