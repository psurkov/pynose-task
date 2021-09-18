package com.github.psurkov.pynosetask.codeInspection

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.roots.impl.FilePropertyPusher
import com.intellij.testFramework.LightProjectDescriptor
import com.intellij.testFramework.TestApplicationManager
import com.intellij.testFramework.UsefulTestCase
import com.intellij.testFramework.fixtures.CodeInsightTestFixture
import com.intellij.testFramework.fixtures.IdeaTestFixtureFactory
import com.intellij.testFramework.fixtures.impl.LightTempDirTestFixtureImpl
import com.jetbrains.python.namespacePackages.PyNamespacePackagesService
import com.jetbrains.python.psi.impl.PythonLanguageLevelPusher
import javax.swing.SwingUtilities

abstract class PyTestCase : UsefulTestCase() {
    protected open val testDataPath: String = "src/test/testData"
    protected lateinit var myFixture: CodeInsightTestFixture

    override fun setUp() {
        TestApplicationManager.getInstance()
        super.setUp()
        val factory = IdeaTestFixtureFactory.getFixtureFactory()
        val fixtureBuilder = factory.createLightFixtureBuilder(LightProjectDescriptor())
        val fixture = fixtureBuilder.fixture
        myFixture = IdeaTestFixtureFactory.getFixtureFactory()
            .createCodeInsightFixture(fixture, LightTempDirTestFixtureImpl(true))
        myFixture.testDataPath = testDataPath
        if (SwingUtilities.isEventDispatchThread()) {
            myFixture.setUp()
        } else {
            ApplicationManager.getApplication().invokeAndWait {
                try {
                    myFixture.setUp()
                } catch (e: Exception) {
                    throw RuntimeException("Error running setup", e)
                }
            }
        }
    }

    override fun tearDown() {
        try {
            PyNamespacePackagesService.getInstance(myFixture.module).resetAllNamespacePackages()
            myFixture.tearDown()
            FilePropertyPusher.EP_NAME.findExtensionOrFail(PythonLanguageLevelPusher::class.java)
                .flushLanguageLevelCache()
        } catch (e: Throwable) {
            addSuppressedException(e)
        } finally {
            super.tearDown()
        }
    }
}