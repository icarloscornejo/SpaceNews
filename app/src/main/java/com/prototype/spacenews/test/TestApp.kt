package com.prototype.spacenews.test

import com.prototype.spacenews.SpaceNewsApp
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module

class TestApp: SpaceNewsApp() {

    /**
     * Koin Modules getter function.
     * Used in the onCreate method by the Koin framework.
     * It overrides the parent's method and returns an empty list.
     *
     * @return Empty list of Koin Modules
     */
    override fun appModules() = emptyList<Module>()

    /**
     * Internal function used by test classes only.
     * It injects a Koin module manually.
     *
     * @param module Koin Module to be injected using the framework.
     */
    internal fun injectModule(module: Module) = loadKoinModules(module)
}