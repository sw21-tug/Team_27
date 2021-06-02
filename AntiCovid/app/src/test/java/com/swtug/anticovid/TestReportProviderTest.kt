package com.swtug.anticovid

import com.swtug.anticovid.models.TestReport
import com.swtug.anticovid.utils.DateTimeUtils
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TestReportProviderTest {

    private val exampleTestReportOutDated = TestReport("test@test.com",
        DateTimeUtils.getDateFromString("01.06.2021 12:00"),
        false,
        DateTimeUtils.getDateFromString("03.06.2021 12:00"))

    private val exampleTestReportValidDate = TestReport("test@test.com",
        DateTimeUtils.getDateFromString("06.06.2021 12:00"),
        false,
        DateTimeUtils.getDateFromString("08.06.2021 12:00"))

    private val testDateAndTime = DateTimeUtils.getDateFromString("05.06.2021 12:00")

    @Before
    fun setUp() {
        val testListOfReports = ArrayList<TestReport>()
        testListOfReports.add(exampleTestReportOutDated)
        testListOfReports.add(exampleTestReportValidDate)
        TestReportProvider.setTestReports(testListOfReports)
    }

    @Test
    fun testGetAllReports() {
        val allReports = TestReportProvider.getAllTestReports()

        assertEquals(allReports[0], exampleTestReportValidDate)
        assertEquals(allReports[1], exampleTestReportOutDated)
        assertTrue(allReports.size == 2)
    }

    @Test
    fun testGetOnlyValidReports() {
        val validReports = TestReportProvider.getOnlyValidTestReports(testDateAndTime)

        assertEquals(validReports[0], exampleTestReportValidDate)
        assertTrue(validReports.size == 1)
    }
}