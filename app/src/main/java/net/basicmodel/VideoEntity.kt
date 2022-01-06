package net.basicmodel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class VideoEntity(
    var name:String = "",
    var id:Int = 0,
    var select:Boolean = false
):Parcelable,Serializable
