package com.github.psurkov.pynosetask.services

import com.intellij.openapi.project.Project
import com.github.psurkov.pynosetask.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
