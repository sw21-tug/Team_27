package com.swtug.anticovid.repositories

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.swtug.anticovid.models.User

object FirebaseRepo {


    fun saveUser(context: Context, user: User) {
        //TODO: store new user in firebase
    }

    fun getUser(email: String, firebaseListener: FirebaseListener) {
        firebaseListener.onStart()
        var user: User? = null
        val db = FirebaseFirestore.getInstance()

        db.collection("User")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { documents ->

                if (documents.size() != 0) {

                    val document: QueryDocumentSnapshot = documents.first()
                    user = User(
                        document["id"] as Long,
                        document["name"] as String,
                        document["surname"] as String,
                        document["email"] as String,
                        document["address"] as String,
                        document["secid"] as String,
                        document["phonenumber"] as String,
                        document["password"] as String
                    )
                }
                firebaseListener.onSuccess(user)
            }
            .addOnFailureListener {
                firebaseListener.onFailure()

            }
    }
}