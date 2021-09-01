package com.netlib

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.netlib.upload.FileUploadBody
import com.netlib.upload.ProcessCallback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.File

/**
 * OkHttp请求基类
 */
abstract class BaseRequest<T, R>(private var TAG: Any? = null) {

    private var mParam: T? = null
    private var mCall: Call<R>? = null
    private val mDispatcher = APICreator.client?.dispatcher

    /**
     * 获取需要请求的Call对象
     */
    abstract fun getCall(): Call<R>

    /**
     * 根据Tag取消请求    OkHttp3.X以后 默认会把当前Request设置为Tag
     * @param tag Any
     */
    fun cancel(tag: Any? = null) {
        mDispatcher?.run {
            for (queuedCall in queuedCalls()) {
                if (tag == queuedCall.request().tag()) {
                    queuedCall.cancel()
                }
            }

            for (runningCall in runningCalls()) {
                if (tag == runningCall.request().tag()) {
                    runningCall.cancel()
                }
            }
        }
    }

    /**
     * 取消所有请求
     */
    fun cancelAll() {
        mDispatcher?.cancelAll()
    }

    /**
     * 获取RequestBody默认实现, Request类中的Param值会直接使用
     */
    fun getRequestBody(): RequestBody {
        return (mParam?.run { Gson().toJson(this) } ?: JsonObject().toString()).toRequestBody("application/json".toMediaTypeOrNull())
    }

    /**
     * 上传使用RequestBody, 从Param中取File对象组成表单
     */
    fun getMultiPartRequestBody(file: File): RequestBody {
        return file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
//        return (mParam?.run { Gson().toJson(this) } ?: JsonObject().toString()).toRequestBody("multipart/form-data".toMediaTypeOrNull())
    }

    /**
     * 上传使用RequestBody, 从Param中取File对象组成表单，可监听传输进度
     */
    fun getProcessMultiPart(file: File, callback: ProcessCallback?, flag: String = "--"): MultipartBody.Part {
        val body = getMultiPartRequestBody(file)
        return MultipartBody.Part.createFormData("file", file.name, FileUploadBody(body, file, callback, flag))
    }

    /**
     * 异步请求
     */
    fun request(param: T?, netCallback: BaseNetCallback<R>) {
        mParam = param
        mCall = getCall()
        TAG?.run { mCall?.request()?.newBuilder()?.tag(this) }

        /** 进队 开始异步请求 */
        mCall?.enqueue(netCallback)
    }

    /**
     * 同步请求
     */
    fun requestSync(param: T?): Response<R>? {
        mParam = param
        mCall = getCall()
        TAG?.run { mCall?.request()?.newBuilder()?.tag(this) }
        /** 进队 开始同步请求 */
        return mCall?.execute()
    }

}