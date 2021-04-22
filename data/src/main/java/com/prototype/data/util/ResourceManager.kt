package com.prototype.data.util

import android.content.Context
import android.content.res.Resources
import androidx.annotation.StringRes

class ResourceManager(context: Context) {

    private var manager: Resources = context.resources

    fun getString(@StringRes resourceId: Int): String = manager.getString(resourceId)
}