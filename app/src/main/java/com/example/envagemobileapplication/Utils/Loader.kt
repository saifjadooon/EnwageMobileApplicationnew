package com.example.envagemobileapplication.Utils

import android.app.Dialog
import android.content.Context
import android.view.Window
import com.example.envagemobileapplication.R

class Loader(private val context: Context) {

    private val dialog: Dialog = Dialog(context)

    init {
        // Set a custom dialog without title
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.loader_layout)


    }

    fun show() {
        dialog.show()
    }

    fun hide() {
        dialog.dismiss()
    }
}