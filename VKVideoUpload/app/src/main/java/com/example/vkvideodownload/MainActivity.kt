package com.example.vkvideodownload

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope
import com.vk.api.sdk.utils.VKUtils


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fingerprints = VKUtils.getCertificateFingerprint(this, this.packageName)
        println(fingerprints)

        VK.login(this, arrayListOf(VKScope.VIDEO, VKScope.FRIENDS))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val callback = object: VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                Toast.makeText(applicationContext, "OK", Toast.LENGTH_LONG).show() //при удачной авторизации появится всплывающее сообщение "ок"

                //получение URL для загрузки видео
                VK.execute(Command("NewTitle", "myDesc"), object: VKApiCallback<List<VideoSaveURL>> {
                    override fun success(result: List<VideoSaveURL>) {

                        if (!isFinishing && result.isNotEmpty()) {
                            val v_url = result[0];
                            Log.e("---------------", v_url.upload_url)
                        }
                    }
                    override fun fail(error: Exception) {
                        Log.e("---------------", error.toString())
                    }
                })
            }

            override fun onLoginFailed(errorCode: Int) {
                Toast.makeText(applicationContext, "error", Toast.LENGTH_LONG).show() //при неудачной авторизации появится всплывающее сообщение "error"
            }
        }
        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


}
