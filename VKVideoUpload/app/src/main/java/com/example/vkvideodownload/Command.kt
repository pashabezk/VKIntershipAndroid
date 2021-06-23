package com.example.vkvideodownload

import com.vk.api.sdk.VKApiManager
import com.vk.api.sdk.VKApiResponseParser
import com.vk.api.sdk.VKMethodCall
import com.vk.api.sdk.exceptions.VKApiIllegalResponseException
import com.vk.api.sdk.internal.ApiCommand
import org.json.JSONException
import org.json.JSONObject

class Command (private val v_name: String, private val v_desc: String): ApiCommand<List<VideoSaveURL>>() {
    override fun onExecute(manager: VKApiManager): List<VideoSaveURL> {

        val call = VKMethodCall.Builder()
            .method("video.save")
            .args("name", v_name)
            .args("description", v_desc)
            .version("5.131")
            .build()

        return manager.execute(call, ResponseApiParser())
    }


    private class ResponseApiParser : VKApiResponseParser<List<VideoSaveURL>> {
        override fun parse(response: String): List<VideoSaveURL> {
            try {
                val ja = JSONObject(response).getJSONArray("response")
                val r = ArrayList<VideoSaveURL>(ja.length())
                for (i in 0 until ja.length()) {
                    val url = VideoSaveURL.parse(ja.getJSONObject(i))
                    r.add(url)
                }
                return r
            } catch (ex: JSONException) {
                throw VKApiIllegalResponseException(ex)
            }
        }
    }
}