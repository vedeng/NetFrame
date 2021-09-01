package com.netlib

import androidx.annotation.CallSuper

/**
 * 根据网络请求回调，加入加载框展示
 */
open class CallBackWithWD<T>(private var waitingDialog: WaitingDialog?) : BaseNetCallback<T>() {

    init {
        waitingDialog?.show()
    }

    @CallSuper
    override fun onSuccess(response: T?) {
        waitingDialog?.dismiss()
    }

    override fun onException(netException: NetException, content: Any?) {
        super.onException(netException, content)
        waitingDialog?.dismiss()
    }
}