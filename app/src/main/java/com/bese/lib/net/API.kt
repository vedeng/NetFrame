package com.bese.lib.net

import com.bese.lib.net.response.AppBaseUrlResponse
import com.bese.lib.net.response.CheckUpdateResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Retrofit调用的API
 */
interface API {

    @POST("app/appUrl")
    fun requestAppBaseUrl(@Body body: RequestBody): Call<AppBaseUrlResponse>

    @POST("app/update")
    fun requestCheckUpdate(@Body body: RequestBody): Call<CheckUpdateResponse>

}
