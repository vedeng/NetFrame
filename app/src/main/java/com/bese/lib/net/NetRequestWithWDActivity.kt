package com.bese.lib.net

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bese.lib.net.request.AppBaseUrlRequest
import com.bese.lib.net.response.AppBaseUrlResponse
import com.netlib.CallBackWithWD
import com.netlib.WaitingDialog
import kotlinx.android.synthetic.main.activity_request_wd.*

class NetRequestWithWDActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_wd)
        request_wd?.setOnClickListener {
            replaceBaseUrl()
        }
    }

    private fun replaceBaseUrl() {
        AppBaseUrlRequest().request(
            AppBaseUrlRequest.Param("1"),
            object : CallBackWithWD<AppBaseUrlResponse>(WaitingDialog(this)) {
                override fun onSuccess(response: AppBaseUrlResponse?) {
                    super.onSuccess(response)
                    response?.data?.appUrl?.run {
                        Toast.makeText(this@NetRequestWithWDActivity, this, Toast.LENGTH_SHORT).show()
                    }
                }
            })
    }

}
