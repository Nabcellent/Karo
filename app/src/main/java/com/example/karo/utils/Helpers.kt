package com.example.karo.utils

import android.content.Context
import android.widget.Toast
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

object Helpers {
    fun showToast(c: Context?, message: String?) {
        Toast.makeText(c, message, Toast.LENGTH_SHORT).show()
    }

    val collectionReferenceForNotes: CollectionReference
        get() {
            val currentUser = FirebaseAuth.getInstance().currentUser
            return FirebaseFirestore.getInstance().collection("notes").document(currentUser!!.uid)
                .collection("notes")
        }

    fun timestampToString(timestamp: Timestamp): String {
        return SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(timestamp.toDate())
    }
}