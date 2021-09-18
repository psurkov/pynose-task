package com.github.psurkov.pynosetask.codeInspection

import com.github.psurkov.pynosetask.PluginBundle
import com.intellij.codeInspection.*
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElementVisitor
import com.jetbrains.python.inspections.PyInspection
import com.jetbrains.python.inspections.PyInspectionVisitor
import com.jetbrains.python.psi.PyFunction

class PyForbiddenTestNames : PyInspection() {
    override fun buildVisitor(
        holder: ProblemsHolder,
        isOnTheFly: Boolean,
        session: LocalInspectionToolSession
    ): PsiElementVisitor {
        return Visitor(holder, session)
    }

    class Visitor(
        holder: ProblemsHolder,
        session: LocalInspectionToolSession
    ) : PyInspectionVisitor(holder, session) {

        override fun visitPyFunction(pyFunction: PyFunction) {
            pyFunction.containingClass?.superClassExpressions?.find {
                it.text == "unittest.TestCase"
            } ?: return

            val identifier = pyFunction.nameIdentifier
            if (identifier != null &&
                identifier.text.startsWith("test_") &&
                identifier.text.contains('c')
            ) {
                holder!!.registerProblem(
                    identifier,
                    PluginBundle.message("codeInspection.forbidden.names"),
                    ProblemHighlightType.WARNING,
                    FixFunctionName()
                )
            }
        }
    }

    class FixFunctionName : LocalQuickFix {
        override fun getFamilyName(): String {
            return PluginBundle.message("codeInspection.forbidden.names.quickfix")
        }

        override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
            val pyFunction = descriptor.psiElement.parent as PyFunction
            val newName = pyFunction.name?.replace("c", "py") ?: return
            pyFunction.setName(newName)
        }

    }
}