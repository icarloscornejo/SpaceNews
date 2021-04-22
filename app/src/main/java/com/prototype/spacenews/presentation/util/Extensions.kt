package com.prototype.spacenews.presentation.util

import android.app.Activity
import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.google.android.material.snackbar.Snackbar
import com.prototype.spacenews.R

// Sets an observer on a certaing LiveData, it's just a shortcut
inline fun <Type> LiveData<Type?>.setObserver(
    owner: LifecycleOwner,
    crossinline action: (Type) -> Unit
) {
    observe(owner, { value -> value?.let { action(it) } })
}

fun Activity.showSnackbarMessage(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(
        findViewById(android.R.id.content),
        message,
        duration
    ).show()
}
