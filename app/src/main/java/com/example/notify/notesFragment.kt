package com.example.notify

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

//ctrl+o and ctrl+I TO IMPLEMTNT FUNCTION
class notesFragment:Fragment() {
    private lateinit var reference:DatabaseReference

     private lateinit var lottieAnimationView: LottieAnimationView
    private lateinit var floatingActionButton:ExtendedFloatingActionButton

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: adapter
    private lateinit var linearLayout: LinearLayout

    private var arrayList: ArrayList<model> = ArrayList()



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.notes, container, false)
        floatingActionButton=view.findViewById(R.id.add_note)
        linearLayout=view.findViewById(R.id.relative)
        lottieAnimationView=view.findViewById(R.id.animation)
        lottieAnimationView.setAnimation(R.raw.lottie)
        lottieAnimationView.playAnimation()
        recyclerView=view.findViewById(R.id.recycler)
        arrayList= ArrayList()
        reference=FirebaseDatabase.getInstance().getReference("notes")
        adapter=adapter(arrayList, requireContext())
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

       // reference=FirebaseDatabase.getInstance().getReference("notes")

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                arrayList.clear()
                for (dataSnapshot in snapshot.children) {
                    val model = dataSnapshot.getValue(model::class.java)
                    model?.let { arrayList.add(it) }
                }
                adapter.notifyDataSetChanged()

                checkEmptyViewVisibility()


            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
                floatingActionButton.setOnClickListener {
                // Create an Intent to start the activity
                val intent = Intent(requireActivity(), add_note::class.java)
                startActivity(intent)
                // requireActivity().finish()
            }






        return  view
    }

    private fun checkEmptyViewVisibility() {
        if(arrayList.isEmpty()){
            linearLayout.visibility=View.VISIBLE
            recyclerView.visibility=View.GONE
        }
        else{
            linearLayout.visibility=View.GONE
            recyclerView.visibility=View.VISIBLE
        }

    }
}

