package com.app.noteva

import android.app.Dialog
import android.content.Context
import android.widget.ProgressBar
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun showSimpleLoading(context: Context): Dialog {
    val progressBar = ProgressBar(context)

    return MaterialAlertDialogBuilder(context, R.style.LoadingDialogStyle)
        .setView(progressBar)
        .setCancelable(false)
        .show()
}