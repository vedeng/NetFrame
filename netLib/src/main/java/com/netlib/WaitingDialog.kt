package com.netlib

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatDialog
import androidx.core.widget.ContentLoadingProgressBar

/**
 * 全局的加载对话框
 */
class WaitingDialog(ctx: Context?, @StyleRes val theme: Int = R.style.Dialog_Fullscreen) : AppCompatDialog(ctx, theme) {

    private var anim: ContentLoadingProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loading)
        anim = findViewById(R.id.loading)
        setCancelable(true)
        setCanceledOnTouchOutside(false)
        window?.run {
            setDimAmount(0f)
        }
    }

    override fun show() {
        try {
            super.show()
            anim?.show()
        } catch (e: Exception) {
            Log.e("LoadDialog-show-Error", "${e.message}")
        }
    }

    override fun dismiss() {
        try {
            anim?.hide()
            super.dismiss()
        } catch (e: Exception) {
            Log.e("LoadDialog-hide-Error", "${e.message}")
        }
    }

}