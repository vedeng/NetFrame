package com.netlib

import androidx.annotation.CallSuper
import com.netlib.container.BaseLoadContainer
import okhttp3.ResponseBody

/**
 * 网络请求回调，封装了各种状态下的错误页面展示
 */
open class CallBackWithLC<T>(private var lc: BaseLoadContainer, private var toastFlag: Boolean = false) : BaseCallback<T>(toastFlag) {
    @CallSuper
    override fun onSuccess(response: T?) {
        lc.showContent()
    }

    override fun onBusinessException(exception: Exception, response: T?) {
        lc.showError()
        super.onBusinessException(exception, response)
    }

    override fun onDataStructureException(exception: Exception, response: T?) {
        lc.showError()
        super.onDataStructureException(exception, response)
    }

    override fun onResponseCodeException(exception: Exception, errorBody: ResponseBody?) {
        lc.showError()
        super.onResponseCodeException(exception, errorBody)
    }

    override fun onNetworkException(exception: Exception, t: Throwable) {
        lc.showNetError()
        super.onNetworkException(exception, t)
    }

    override fun onUnhandledException(exception: Exception, t: Throwable) {
        lc.showError()
        super.onUnhandledException(exception, t)
    }

}