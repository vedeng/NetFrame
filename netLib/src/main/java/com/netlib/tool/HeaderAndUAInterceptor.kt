package com.netlib.tool

import com.netlib.config.NetConfig
import okhttp3.Interceptor
import okhttp3.Response


/**
 * <请求头配置>
 */
class HeaderAndUAInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request =chain.request().newBuilder()
            .addHeader("user-agent", NetConfig.USER_AGENT_VALUE)    // UA, 固定形式，参数默认”android“
        NetConfig.mHeaderParamsMap?.run {
            if (size > 0) {
                entries.forEach {
                    request.addHeader(it.key, it.value ?: "")
                }
            }
        }
        return chain.proceed(request.build())
    }

}