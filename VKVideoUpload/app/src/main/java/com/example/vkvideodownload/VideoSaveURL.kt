package com.example.vkvideodownload

import android.os.Parcel
import android.os.Parcelable
import org.json.JSONObject

class VideoSaveURL (
    val upload_url: String = "",
    val video_id: Int = 0,
    val title: String = "",
    val description: String = "",
    val owner_id: Int = 0) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readInt()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt()!!)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(upload_url)
        parcel.writeInt(video_id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeInt(owner_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VideoSaveURL> {
        override fun createFromParcel(parcel: Parcel): VideoSaveURL {
            return VideoSaveURL(parcel)
        }

        override fun newArray(size: Int): Array<VideoSaveURL?> {
            return arrayOfNulls(size)
        }

        fun parse(json: JSONObject)
                = VideoSaveURL(
            upload_url = json.optString("first_name", ""),
            video_id = json.optInt("id", 0),
            title = json.optString("first_name", ""),
            description = json.optString("last_name", ""),
            owner_id = json.optInt("id", 0))
    }
}