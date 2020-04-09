package com.bese.lib.net

import com.netlib.APICreator

object NetTool {

    const val BaseUrl = "https://mock.yonyoucloud.com/mock/3936/"

    /** 获取网络请求url接口 */
    fun getApi() : API {
        return APICreator.getRetrofit().create(API::class.java)
    }

}