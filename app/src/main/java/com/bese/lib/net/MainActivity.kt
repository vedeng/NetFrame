package com.bese.lib.net

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_normal?.setOnClickListener {
            startActivity(Intent(this, NetRequestActivity::class.java))
        }

        tv_net_wd?.setOnClickListener {
            startActivity(Intent(this, NetRequestWithWDActivity::class.java))
        }

        tv_net_lc?.setOnClickListener {
            startActivity(Intent(this, NetRequestWithLCActivity::class.java))
        }

    }

}
