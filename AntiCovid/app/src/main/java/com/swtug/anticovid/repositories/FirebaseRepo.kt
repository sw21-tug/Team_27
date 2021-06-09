package com.swtug.anticovid.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.swtug.anticovid.models.FirebaseTestReport
import com.swtug.anticovid.models.TestReport
import com.swtug.anticovid.models.User
import com.swtug.anticovid.models.Vaccination
import com.swtug.anticovid.utils.DateTimeUtils

object FirebaseRepo {

    private const val COLLECTION_USER = "User"
    private const val COLLECTION_TEST_REPORTS = "TestReports"
    private const val COLLECTION_VACCINATIONS = "Vaccinations"

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

    private const val VACCINATION_MANUFACTORER = "manufacturor"
    private const val VACCINATION_FIRST_DOSE_DATE = "firstDose"
    private const val VACCINATION_SECOND_DOSE_DATE = "secondDose"
    private const val VACCINATION_INSTITUTION = "institution"


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

    fun addTestReport(
        firebaseTestReport: FirebaseTestReport,
        firebaseUserListener: FirebaseUserListener
    ) {
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

    fun getAllTestReportsFrom(
        userEmail: String,
        firebaseTestReportListener: FirebaseTestReportListener
    ) {
        firebaseTestReportListener.onStart()
        val firebaseStore = FirebaseFirestore.getInstance()

        firebaseStore.collection(COLLECTION_TEST_REPORTS)
            .whereEqualTo(TEST_REPORT_EMAIL, userEmail).get()
            .addOnSuccessListener { documents ->
                val testReports = ArrayList<TestReport>()

                for (document in documents) {
                    if (document != null && document.exists()) {
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
            DateTimeUtils.getDateFromString(document[TEST_REPORT_DATE] as String),
            document[TEST_REPORT_RESULT] as Boolean,
            DateTimeUtils.getDateFromString(document[TEST_REPORT_VALID] as String)
        )
    }

    fun deleteTestReport(
        firebaseTestReport: FirebaseTestReport,
        firebaseTestReportListener: FirebaseTestReportListener
    ) {
        firebaseTestReportListener.onStart()
        val firebaseStore = FirebaseFirestore.getInstance()

        firebaseStore.collection(COLLECTION_TEST_REPORTS)
            .whereEqualTo(TEST_REPORT_EMAIL, firebaseTestReport.email)
            .whereEqualTo(TEST_REPORT_DATE, firebaseTestReport.testdate)
            .whereEqualTo(TEST_REPORT_VALID, firebaseTestReport.validdate)
            .whereEqualTo(TEST_REPORT_RESULT, firebaseTestReport.testresult).get()
            .addOnSuccessListener { documents ->

                if (documents != null && !documents.isEmpty) {
                    val documentID = documents.documents[0].id
                    firebaseStore.collection(COLLECTION_TEST_REPORTS).document(documentID).delete()
                        .addOnSuccessListener {
                            firebaseTestReportListener.onSuccess(ArrayList())
                        }.addOnFailureListener {
                        firebaseTestReportListener.onFailure()
                    }
                } else {
                    firebaseTestReportListener.onFailure()
                }

            }.addOnFailureListener {
                firebaseTestReportListener.onFailure()
            }
    }

    fun getVaccination(
        userEmail: String,
        firebaseVaccinationListener: FirebaseVaccinationListener
    ) {
        firebaseVaccinationListener.onStart()
        val firebaseStore = FirebaseFirestore.getInstance()
        var vaccination: Vaccination? = null

        firebaseStore.collection(COLLECTION_VACCINATIONS)
            .whereEqualTo(USER_EMAIL, userEmail).get()
            .addOnSuccessListener { documents ->

                for (document in documents) {
                    if (document != null && document.exists()) {
                        vaccination = Vaccination(
                            document[USER_EMAIL] as String,
                            document[VACCINATION_MANUFACTORER] as String,
                            document[VACCINATION_FIRST_DOSE_DATE] as String,
                            document[VACCINATION_SECOND_DOSE_DATE] as String,
                            document[VACCINATION_INSTITUTION] as String
                        )
                        break;
                    }
                }

                firebaseVaccinationListener.onSuccess(vaccination)
            }
            .addOnFailureListener {
                firebaseVaccinationListener.onFailure()
            }
    }

    fun saveOrUpdateVaccination(
        userEmail: String,
        vaccination: Vaccination,
        firebaseVaccinationListener: FirebaseVaccinationListener
    ) {
        firebaseVaccinationListener.onStart()
        val firebaseStore = FirebaseFirestore.getInstance()

        firebaseStore.collection(COLLECTION_VACCINATIONS)
            .whereEqualTo(USER_EMAIL, userEmail).get()
            .addOnSuccessListener { documents ->

                if (documents == null || documents.isEmpty) {
                    firebaseStore.collection(COLLECTION_VACCINATIONS).add(vaccination)
                        .addOnSuccessListener {
                            firebaseVaccinationListener.onSuccess(vaccination)
                        }
                        .addOnFailureListener {
                            firebaseVaccinationListener.onFailure()
                        }
                } else {
                    val documentID = documents.documents[0].id
                    firebaseStore.collection(COLLECTION_VACCINATIONS).document(documentID)
                        .set(vaccination).addOnSuccessListener {
                            firebaseVaccinationListener.onSuccess(vaccination)
                        }.addOnFailureListener {
                            firebaseVaccinationListener.onFailure()
                        }
                }
            }
            .addOnFailureListener {
                firebaseVaccinationListener.onFailure()
            }
    }


}
