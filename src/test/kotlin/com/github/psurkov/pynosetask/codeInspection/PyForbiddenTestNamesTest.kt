package com.github.psurkov.pynosetask.codeInspection

import com.intellij.testFramework.TestDataPath

@TestDataPath("\$CONTENT_ROOT/testData/")
class PyForbiddenTestNamesTest : PyInspectionTestCase() {
    override val inspectionClass = PyForbiddenTestNames::class.java
    override val isInfo = false
    override val isWeakWarning = true
    override val isWarning = true
    override val inspectionDescription = "Don't use the letter 'c'"

    fun testSimple() {
        doInspectionAndQuickFixTest()
    }

    fun testManySuperClasses() {
        doInspectionAndQuickFixTest()
    }

    fun testMultiC() {
        doInspectionAndQuickFixTest()
    }

    fun testNoC() {
        doNegativeTest()
    }

    fun testNoTestFunctions() {
        doNegativeTest()
    }

    fun testNoTestClass() {
        doNegativeTest()
    }
}