package com.netlib

import com.netlib.config.NetConfig
import com.netlib.tool.GsonConverterFactory
import com.netlib.tool.HeaderAndUAInterceptor
import com.netlib.tool.TrustAllHttps
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

/**
 * 网络请求API
 */
object APICreator {

    /**
     * 在使用此Library时，请务必先初始化其中的数值配置
     */
    fun init(base: String, connectTimeout: Int = 10, requestTimeout: Int = 10, responseTimeout: Int = 10, enableHttps: Boolean = true, enableLogEcho: Boolean = true) {
        NetConfig.BASE_URL = base
        NetConfig.ENABLE_HTTPS = enableHttps
        NetConfig.CONNECT_TIMEOUT = connectTimeout
        NetConfig.REQUEST_TIMEOUT = requestTimeout
        NetConfig.RESPONSE_TIMEOUT = responseTimeout
        NetConfig.ENABLE_NET_LOG_ECHO = enableLogEcho
    }

    /** 如需修改请求Header，传入UserAgent，可初始化此方法 */
    fun initHeaderAndUA(headerMap: HashMap<String, String?>, userAgent: String?) {
        NetConfig.mHeaderParamsMap = headerMap
        userAgent?.run { NetConfig.USER_AGENT_VALUE = this }
    }

    /**
     * 网络框架 Retrofit
     * 在使用网络时，iss = true 代表需要兼容HTTPS , iss = false 代表不用兼容HTTPS
     */
    fun getRetrofit(): Retrofit {
        val clientBuilder = OkHttpClient.Builder().retryOnConnectionFailure(true)
            .connectTimeout(NetConfig.CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(NetConfig.REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(NetConfig.RESPONSE_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .addInterceptor(HeaderAndUAInterceptor())

        if (NetConfig.ENABLE_NET_LOG_ECHO) {
            clientBuilder.addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
        }

        if (NetConfig.ENABLE_HTTPS) {
            TrustAllHttps.createSSLSocketFactory()?.let { clientBuilder.sslSocketFactory(it, TrustAllHttps.TrustAllCerts()) }
            clientBuilder.hostnameVerifier(TrustAllHttps.TrustAllHostnameVerifier())
        }
        // Builder参数动态配置完毕，创建实体
        client = clientBuilder.build()

        // 创建Retrofit对象
        return Retrofit.Builder()
            .baseUrl(NetConfig.BASE_URL)
            .client(client!!)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    var client: OkHttpClient? = null
        private set

}