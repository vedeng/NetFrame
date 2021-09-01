package com.netlib

import androidx.annotation.CallSuper
import com.netlib.container.BaseLoadContainer
import okhttp3.ResponseBody

/**
 * 网络请求回调，封装了各种状态下的错误页面展示
 */
open class CallBackWithLC<T>(private var lc: BaseLoadContainer, private var toastFlag: Boolean = false) : BaseNetCallback<T>(toastFlag) {
    @CallSuper
    override fun onSuccess(response: T?) {
        lc.showContent()
    }

    override fun onBusinessException(netException: NetException, response: T?) {
        lc.showError()
        super.onBusinessException(netException, response)
    }

    override fun onDataStructureException(netException: NetException, response: T?) {
        lc.showError()
        super.onDataStructureException(netException, response)
    }

    override fun onResponseCodeException(netException: NetException, errorBody: ResponseBody?) {
        lc.showError()
        super.onResponseCodeException(netException, errorBody)
    }

    override fun onNetworkException(netException: NetException, t: Throwable) {
        lc.showNetError()
        super.onNetworkException(netException, t)
    }

    override fun onUnhandledException(netException: NetException, t: Throwable) {
        lc.showError()
        super.onUnhandledException(netException, t)
    }

}