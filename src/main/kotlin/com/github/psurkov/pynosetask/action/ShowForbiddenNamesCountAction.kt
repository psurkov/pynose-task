package com.github.psurkov.pynosetask.action

import com.github.psurkov.pynosetask.PluginBundle
import com.github.psurkov.pynosetask.codeInspection.PyForbiddenTestNames
import com.intellij.codeInspection.InspectionManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.ui.Messages
import com.intellij.psi.PsiDocumentManager

class ShowForbiddenNamesCountAction : AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project ?: return
        val openFiles = FileEditorManager.getInstance(project).openFiles
        val documentManager = FileDocumentManager.getInstance()
        val psiDocumentManager = PsiDocumentManager.getInstance(project)
        val message = openFiles.joinToString(separator = "\n") { virtualFile ->
            val psiFile = documentManager.getDocument(virtualFile)?.let {
                psiDocumentManager.getPsiFile(it)
            } ?: return@joinToString PluginBundle.message("actions.show.forbidden.error", virtualFile.name)
            val size = PyForbiddenTestNames().processFile(psiFile, InspectionManager.getInstance(project)).size
            PluginBundle.message("actions.show.forbidden.names.count.message", virtualFile.name, size)
        }
        Messages.showMessageDialog(
            message,
            PluginBundle.message("actions.show.forbidden.names.title"),
            Messages.getInformationIcon()
        )
    }
}