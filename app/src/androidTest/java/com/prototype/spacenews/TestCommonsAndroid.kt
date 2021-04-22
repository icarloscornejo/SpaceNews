package com.prototype.spacenews

import io.mockk.mockk
import org.junit.Assert

abstract class TestCommonsAndroid {

    inline fun <reified Class> mock(): Class = mockk()

    fun assertNotNull(any: Any?) = Assert.assertNotNull(any)
    fun assertEquals(expected: Any?, actual: Any?) = Assert.assertEquals(expected, actual)
    fun assertTrue(condition: Boolean) = Assert.assertTrue(condition)
    fun assertFalse(condition: Boolean) = Assert.assertFalse(condition)
}