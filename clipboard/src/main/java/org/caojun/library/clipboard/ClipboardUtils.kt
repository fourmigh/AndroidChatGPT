package org.caojun.library.clipboard

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.text.TextUtils
import android.widget.Toast


object ClipboardUtils {

    fun copy(context: Context, text: String, toast: String? = null) {
        val cmb = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        cmb.setPrimaryClip(ClipData.newPlainText(null, text))
        if (!TextUtils.isEmpty(toast)) {
            Toast.makeText(context, toast, Toast.LENGTH_SHORT).show()
        }
    }
}