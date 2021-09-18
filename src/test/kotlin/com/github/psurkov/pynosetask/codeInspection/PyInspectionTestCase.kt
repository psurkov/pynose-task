package com.github.psurkov.pynosetask.codeInspection

import com.intellij.codeInspection.ex.InspectionProfileImpl
import com.intellij.lang.annotation.HighlightSeverity
import com.jetbrains.python.inspections.PyInspection


abstract class PyInspectionTestCase : PyTestCase() {
    protected abstract val inspectionClass: Class<out PyInspection>
    protected abstract val isInfo: Boolean
    protected abstract val isWeakWarning: Boolean
    protected abstract val isWarning: Boolean
    protected abstract val inspectionDescription: String

    protected open fun doInspectionAndQuickFixTest() {
        myFixture.enableInspections(inspectionClass)
        val testFileName = getTestName(true)
        myFixture.configureByFile("$testFileName.py")
        val warnings = myFixture.doHighlighting(HighlightSeverity.WARNING)
            .filter { it.description == inspectionDescription }
        assertNotEmpty(warnings)
        myFixture.getAllQuickFixes().forEach {
            myFixture.launchAction(it)
        }
        myFixture.checkResultByFile("${testFileName}_after.py", true)
    }

    protected open fun doNegativeTest() {
        myFixture.enableInspections(inspectionClass)
        val testFileName = getTestName(true)
        myFixture.configureByFile("$testFileName.py")
        val warnings = myFixture.doHighlighting(HighlightSeverity.WARNING)
            .filter { it.description == inspectionDescription }
        assertEmpty(warnings)
    }

    override fun setUp() {
        super.setUp()
        InspectionProfileImpl.INIT_INSPECTIONS = true
        myFixture.setCaresAboutInjection(true)
    }

    override fun tearDown() {
        InspectionProfileImpl.INIT_INSPECTIONS = false
        super.tearDown()
    }
}