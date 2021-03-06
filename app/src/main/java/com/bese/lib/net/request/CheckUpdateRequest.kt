package com.bese.lib.net.request

import com.netlib.BaseRequest
import com.bese.lib.net.NetTool
import com.bese.lib.net.response.CheckUpdateResponse
import retrofit2.Call

/**
 * desc: 检查更新接口
 */
class CheckUpdateRequest(var url: String) : BaseRequest<Any, CheckUpdateResponse>() {
    override fun getCall(): Call<CheckUpdateResponse> {
        return NetTool.getApi().requestCheckUpdate(url, getRequestBody())
    }
    data class Param(var version: String?)
}
