package com.netlib

import androidx.annotation.CallSuper

/**
 * 根据网络请求回调，加入加载框展示
 */
open class CallBackWithWD<T>(private var waitingDialog: WaitingDialog?) : BaseCallback<T>() {

    init {
        waitingDialog?.show()
    }

    @CallSuper
    override fun onSuccess(response: T?) {
        waitingDialog?.dismiss()
    }

    override fun onException(exception: Exception, content: Any?) {
        super.onException(exception, content)
        waitingDialog?.dismiss()
    }
}