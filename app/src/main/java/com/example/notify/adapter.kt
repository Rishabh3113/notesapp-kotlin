package com.example.notify

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class adapter(private val arraylist:ArrayList<model>,
              private val context: Context,
              ) :RecyclerView.Adapter<adapter.viewholder>() {

    inner class viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.heading)
        val desc: TextView = itemView.findViewById(R.id.description)
        val cardView: CardView = itemView.findViewById(R.id.card)
        val btnDelete: ImageButton = itemView.findViewById(R.id.delete)
        val btnCopy: ImageButton = itemView.findViewById(R.id.copy)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val view = LayoutInflater.from(context).inflate(R.layout.items, parent, false)
        return viewholder(view)

    }

    override fun getItemCount(): Int {
        return arraylist.size
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        val models = arraylist[position]
        holder.textView.text = models.title
        holder.desc.text = models.body

        holder.btnCopy.setOnClickListener() {
            val noteToCopy = arraylist[position]
            copyNoteToClipboard(noteToCopy)
            Toast.makeText(context, "Note copied!", Toast.LENGTH_SHORT).show()
        }
        holder.btnDelete.setOnClickListener() {
            deleteNoteFromFirebase(position)



        }
    }

    private fun deleteNoteFromFirebase(position: Int) {
        if (position in 0 until arraylist.size) {
            val noteToDelete = arraylist[position]
            val reference:DatabaseReference = FirebaseDatabase.getInstance().getReference("notes")

            val noteReference = reference.orderByChild("title").equalTo(noteToDelete.title)

            noteReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val noteSnapshot = snapshot.children.first()
                        noteSnapshot.ref.removeValue().addOnSuccessListener {
                            arraylist.removeAt(position)
                            //notifyDataSetChanged()
                            notifyItemRemoved(position)


                            Toast.makeText(context, "Note deleted!", Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener {
                            Toast.makeText(context, "Failed to delete note, please check network", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context, "Note not found in the database", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context, "Database error: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }









    }



    private fun copyNoteToClipboard(noteToCopy: model) {
        val clipboardManager =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("Note", "${noteToCopy.title}\n${noteToCopy.body}")
        clipboardManager.setPrimaryClip(clipData)


    }
}

