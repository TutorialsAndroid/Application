package com.app.noteva

import android.app.Activity
import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.TextView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import androidx.core.graphics.toColorInt

private const val termsUrl = "https://noteva-android.web.app/terms.html"
private const val privacyUrl = "https://noteva-android.web.app/privacy.html"

fun showTermsDialog(
    context: Context,
    onAccept: () -> Unit
) {
    val text =
        context.getString(R.string.terms_dialog_text)

    val spannable = SpannableString(text)

    val termsText = "Terms & Conditions"
    val privacyText = "Privacy Policy"

    val termsStart = text.indexOf(termsText)
    val privacyStart = text.indexOf(privacyText)

    // Terms & Conditions click
    spannable.setSpan(object : ClickableSpan() {
        override fun onClick(widget: View) {
            openCustomTab(
                context,
                termsUrl
            )
        }
    }, termsStart, termsStart + termsText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

    // Privacy Policy click
    spannable.setSpan(object : ClickableSpan() {
        override fun onClick(widget: View) {
            openCustomTab(
                context,
                privacyUrl
            )
        }
    }, privacyStart, privacyStart + privacyText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

    // Optional styling
    listOf(
        termsStart to termsText.length,
        privacyStart to privacyText.length
    ).forEach { (start, length) ->
        spannable.setSpan(
            ForegroundColorSpan(context.getThemeColor(com.google.android.material.R.attr.colorSecondary)),
                    start,
            start + length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannable.setSpan(
            UnderlineSpan(),
            start,
            start + length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }

    val messageView = TextView(context).apply {
        setText(spannable)
        movementMethod = LinkMovementMethod.getInstance()
        setPadding(32, 24, 32, 0)
    }

    MaterialAlertDialogBuilder(context)
        .setTitle(context.getString(R.string.terms_dialog_title))
        .setView(messageView)
        .setCancelable(false)
        .setPositiveButton(context.getString(R.string.terms_dialog_pBtn)) { dialog, _ ->
            dialog.dismiss()
            onAccept()
        }
        .setNegativeButton(context.getString(R.string.terms_dialog_nButton)) { dialog, _ ->
            dialog.dismiss()
            (context as? Activity)?.finishAffinity()
        }
        .show()
}

fun Context.getThemeColor(attr: Int): Int {
    val typedValue = android.util.TypedValue()
    theme.resolveAttribute(attr, typedValue, true)
    return typedValue.data
}
