package com.bese.lib.net.request

import com.netlib.BaseRequest
import com.bese.lib.net.NetTool
import com.bese.lib.net.response.AppBaseUrlResponse
import retrofit2.Call

/**
 * desc: APP域名更换接口
 */
class AppBaseUrlRequest : BaseRequest<Any, AppBaseUrlResponse>() {
    override fun getCall(): Call<AppBaseUrlResponse> {
        return NetTool.getApi().requestAppBaseUrl(getRequestBody())
    }
    data class Param(var type: String?)
}
