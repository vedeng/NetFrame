package com.netlib

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.UnknownHostException

/**
 * 回调基类
 */
abstract class BaseNetCallback<T>(private var toastFlag: Boolean = true) : Callback<T> {
    enum class NetException(var desc: String) {
        Business("业务异常"),
        DataStructure("数据结构异常"),
        ResponseCode("响应码异常"),
        Network("网络异常"),
        Unhandled("未处理异常")
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        // retrofit2根据响应码是否在区间[200, 300)判断响应是否成功
        if (response.isSuccessful) {
            // 检查数据结构是否符合约定
            onSuccess(response.body())
            onLoadEnd(true)
        } else {
            onException(NetException.ResponseCode, response.errorBody())
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        if (t is ConnectException || t is UnknownHostException || t is HttpException || t is InterruptedIOException) {
            onException(NetException.Network, t)
        } else {
            onException(NetException.Unhandled, t)
        }
    }


    /** 业务正常期望回调 */
    abstract fun onSuccess(response: T?)

    /** 异常回调 转发 */
    @Suppress("UNCHECKED_CAST")
    open fun onException(netException: NetException, content: Any?) {
        when (netException) {
            NetException.Business -> onBusinessException(netException, content as T?)
            NetException.DataStructure -> onDataStructureException(netException, content as T?)
            NetException.ResponseCode -> onResponseCodeException(netException, content as ResponseBody?)
            NetException.Network -> onNetworkException(netException, content as Throwable)
            NetException.Unhandled -> onUnhandledException(netException, content as Throwable)
        }
        onLoadEnd(false)
    }

    /** 业务异常 */
    open fun onBusinessException(netException: NetException, response: T?) {
        // 通用处理业务异常
        (response as? BaseResponse)?.code?.run { }
        response.run { showLog((response as? BaseResponse)?.message ?: "业务异常") }
    }

    /** 数据结构异常 */
    open fun onDataStructureException(netException: NetException, response: T?) {
        showLog(netException.desc.plus("：\n").plus(response?.toString()))
    }

    /** 响应码异常    正常范围为[200..300) */
    open fun onResponseCodeException(netException: NetException, errorBody: ResponseBody?) {
        showLog("服务器异常，请稍后再试")
    }

    /** 网络异常 */
    open fun onNetworkException(netException: NetException, t: Throwable) {
        showLog(netException.desc.plus("，请检查网络"))
    }

    /** 未处理异常 */
    open fun onUnhandledException(netException: NetException, t: Throwable) {
        showLog(netException.desc.plus("：\n").plus(t.localizedMessage))
    }

    // 错误日志打印
    open fun showLog(msg: String?) {
        Log.e("Callback-Exception: ", "$msg")
        if (toastFlag) showShort(msg)
    }

    open fun showShort(msg: String?) {
        Toast.makeText(reflectContext(), msg, Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("PrivateApi")
    private fun reflectContext() : Context? {
        val activityThread = Class.forName("android.app.ActivityThread")
        val thread = activityThread.getMethod("currentActivityThread").invoke(null)
        val app = activityThread.getMethod("getApplication").invoke(thread)
        return app as? Application
    }

    /**
     * 如果有特殊处理，如loading结束隐藏 等，可以统一回调，无需分别对成功和失败做处理
     *      调用时机为 其他函数处理结束，最后调用LoadEnd
     *  @param isSuccess 告知子方法，该请求是否成功
     */
    open fun onLoadEnd(isSuccess: Boolean?) { }
}
