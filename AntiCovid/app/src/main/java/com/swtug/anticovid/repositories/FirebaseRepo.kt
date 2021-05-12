package com.swtug.anticovid.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.swtug.anticovid.DateTimeUtils
import com.swtug.anticovid.models.FirebaseTestReport
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

    private const val TEST_REPORT_EMAIL = "email"
    private const val TEST_REPORT_DATE = "testdate"
    private const val TEST_REPORT_RESULT = "testresult"
    private const val TEST_REPORT_VALID = "validdate"


    fun saveUser(user: User, firebaseUserListener: FirebaseUserListener) {
        firebaseUserListener.onStart()
        val firebaseFireStore = FirebaseFirestore.getInstance()

        firebaseFireStore.collection(COLLECTION_USER).document(user.email).get()
            .addOnSuccessListener { document ->
                if (document == null || !document.exists()) {
                    firebaseFireStore.collection(COLLECTION_USER).document(user.email).set(user)
                        .addOnSuccessListener {
                            firebaseUserListener.onSuccess(user)
                        }
                        .addOnFailureListener {
                            firebaseUserListener.onFailure()
                        }
                } else {
                    firebaseUserListener.onSuccess(null)
                }
            }.addOnFailureListener {
                firebaseUserListener.onFailure()
            }
    }

    fun updateUser(user: User, firebaseUserListener: FirebaseUserListener) {
        firebaseUserListener.onStart()
        val firebaseFireStore = FirebaseFirestore.getInstance()

        firebaseFireStore.collection(COLLECTION_USER).document(user.email).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    firebaseFireStore.collection(COLLECTION_USER).document(user.email).set(user)
                        .addOnSuccessListener {
                            firebaseUserListener.onSuccess(user)
                        }
                        .addOnFailureListener {
                            firebaseUserListener.onFailure()
                        }
                } else {
                    firebaseUserListener.onSuccess(null)
                }
            }.addOnFailureListener {
                firebaseUserListener.onFailure()
            }
    }

    fun getUser(email: String, firebaseUserListener: FirebaseUserListener) {
        firebaseUserListener.onStart()
        var user: User? = null
        val firebaseStore = FirebaseFirestore.getInstance()

        firebaseStore.collection(COLLECTION_USER).document(email).get()
            .addOnSuccessListener { document ->

                if (document != null && document.exists()) {
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

                firebaseUserListener.onSuccess(user)
            }
            .addOnFailureListener {
                firebaseUserListener.onFailure()
            }
    }

    fun addTestReport(firebaseTestReport: FirebaseTestReport, firebaseUserListener: FirebaseUserListener) {
        firebaseUserListener.onStart()
        val firebaseStore = FirebaseFirestore.getInstance()

        firebaseStore.collection(COLLECTION_TEST_REPORTS).add(firebaseTestReport)
            .addOnSuccessListener {
                firebaseUserListener.onSuccess(null)
            }
            .addOnFailureListener {
                firebaseUserListener.onFailure()
            }
    }

    fun getAllTestReportsFrom(userEmail: String, firebaseTestReportListener: FirebaseTestReportListener) {
        firebaseTestReportListener.onStart()
        val firebaseStore = FirebaseFirestore.getInstance()

        firebaseStore.collection(COLLECTION_TEST_REPORTS)
            .whereEqualTo(TEST_REPORT_EMAIL, userEmail).get()
            .addOnSuccessListener { documents ->
                val testReports = ArrayList<TestReport>()

                for(document in documents) {
                    if(document != null && document.exists()) {
                        testReports.add(getTestReportFrom(document))
                    }
                }

                firebaseTestReportListener.onSuccess(testReports)
            }
            .addOnFailureListener {
                firebaseTestReportListener.onFailure()
            }
    }

    private fun getTestReportFrom(document: QueryDocumentSnapshot): TestReport {
        return TestReport(
            document[TEST_REPORT_EMAIL] as String,
            DateTimeUtils.getDateFromString(document[TEST_REPORT_DATE]  as String),
            document[TEST_REPORT_RESULT] as Boolean,
            DateTimeUtils.getDateFromString(document[TEST_REPORT_VALID] as String)
        )
    }
}
