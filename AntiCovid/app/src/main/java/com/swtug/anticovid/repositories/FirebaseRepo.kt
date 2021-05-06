package com.swtug.anticovid.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.swtug.anticovid.models.TestReport
import com.swtug.anticovid.models.User

object FirebaseRepo {

    private const val COLLECTION_USER = "User"
    private const val COLLECTION_TEST_REPORTS = "TestReports"

    private const val USER_ID = "id"
    private const val USER_NAME = "name"
    private const val USER_SURNAME = "surname"
    private const val USER_EMAIL = "email"
    private const val USER_ADDRESS = "address"
    private const val USER_SECID = "secid"
    private const val USER_PHONENUMBER = "phonenumber"
    private const val USER_PASSWORD = "password"


    fun saveUser(user: User, firebaseListener: FirebaseListener) {
        firebaseListener.onStart()
        val firebaseFireStore = FirebaseFirestore.getInstance()

        firebaseFireStore.collection(COLLECTION_USER).document(user.email).get()
            .addOnSuccessListener { document ->
                if(document == null || !document.exists()) {
                    firebaseFireStore.collection(COLLECTION_USER).document(user.email).set(user)
                    .addOnSuccessListener {
                        firebaseListener.onSuccess(user)
                    }
                    .addOnFailureListener {
                        firebaseListener.onFailure()
                    }
                } else {
                    firebaseListener.onSuccess(null)
                }
        }.addOnFailureListener {
            firebaseListener.onFailure()
        }
    }

    fun getUser(email: String, firebaseListener: FirebaseListener) {
        firebaseListener.onStart()
        var user: User? = null
        val firebaseStore = FirebaseFirestore.getInstance()

        firebaseStore.collection(COLLECTION_USER).document(email).get()
            .addOnSuccessListener { document ->

                if(document != null && document.exists()) {
                    user = User(
                        document[USER_ID] as Long,
                        document[USER_NAME] as String,
                        document[USER_SURNAME] as String,
                        document[USER_EMAIL] as String,
                        document[USER_ADDRESS] as String,
                        document[USER_SECID] as String,
                        document[USER_PHONENUMBER] as String,
                        document[USER_PASSWORD] as String
                    )
                }

                firebaseListener.onSuccess(user)
            }
            .addOnFailureListener {
                firebaseListener.onFailure()
            }
    }

    fun addTestReport(testReport: TestReport, firebaseListener: FirebaseListener) {
        firebaseListener.onStart()
        val firebaseStore = FirebaseFirestore.getInstance()

        firebaseStore.collection(COLLECTION_TEST_REPORTS).add(testReport)
            .addOnSuccessListener {
                firebaseListener.onSuccess(null)
            }
            .addOnFailureListener {
                firebaseListener.onFailure()
            }
    }
}