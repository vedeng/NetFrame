package com.bese.lib.net

import com.bese.lib.net.response.AppBaseUrlResponse
import com.bese.lib.net.response.CheckUpdateResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url

/**
 * Retrofit调用的API
 */
interface API {

    @POST("app/appUrl")
    fun requestAppBaseUrl(@Body body: RequestBody): Call<AppBaseUrlResponse>

    // Url 写在方法体中，POST注解就不能加Url了。
    @POST
    fun requestCheckUpdate(@Url url: String, @Body body: RequestBody): Call<CheckUpdateResponse>

}
