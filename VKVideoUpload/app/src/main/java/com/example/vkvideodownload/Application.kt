package com.example.vkvideodownload

import android.widget.Toast
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKTokenExpiredHandler

class Application : android.app.Application() {

    private val tokenTracker = object: VKTokenExpiredHandler {
        override fun onTokenExpired() {
            Toast.makeText(applicationContext, "Token Expired", Toast.LENGTH_LONG).show()

//            val intent: Intent = Intent(this@Application, MainActivity::class.java).apply {
//                putExtra ("", "")
//            }
//            startActivity(intent)

        }
    }

    override fun onCreate() {
        super.onCreate()

        VK.initialize(this)
        VK.addTokenExpiredHandler(tokenTracker)
    }


}

