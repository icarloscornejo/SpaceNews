package com.prototype.spacenews

import androidx.test.core.app.ApplicationProvider
import com.prototype.data.util.ResourceManager
import com.prototype.spacenews.test.TestApp
import io.mockk.unmockkAll
import org.junit.After
import org.koin.core.context.stopKoin
import org.koin.dsl.ModuleDeclaration
import org.koin.dsl.module
import org.koin.test.KoinTest

abstract class InjectableTest: TestCommons(), KoinTest {

    lateinit var context: TestApp
    lateinit var resourceManager: ResourceManager

    fun initCommons() {
        context = ApplicationProvider.getApplicationContext()
        resourceManager = ResourceManager(context)
    }

    fun injectModule(moduleDeclaration: ModuleDeclaration) {
        context.injectModule(module(moduleDeclaration = moduleDeclaration))
    }

    @After
    fun autoClose() {
        unmockkAll()
        stopKoin()
    }
}