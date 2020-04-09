package com.bese.lib.net

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bese.lib.net.request.AppBaseUrlRequest
import com.bese.lib.net.response.AppBaseUrlResponse
import com.netlib.BaseCallback
import kotlinx.android.synthetic.main.activity_request.*

class NetRequestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request)

        request?.setOnClickListener {
            replaceBaseUrl()
        }

    }

    private fun replaceBaseUrl() {
        AppBaseUrlRequest().request(AppBaseUrlRequest.Param("1"), object : BaseCallback<AppBaseUrlResponse>() {
            override fun onSuccess(response: AppBaseUrlResponse?) {
                response?.data?.appUrl?.run {
                    Toast.makeText(this@NetRequestActivity, "返回的域名：$this", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

}
