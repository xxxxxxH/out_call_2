package net.basicmodel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class ThemeEntity(
    var a_name: String = "",
    var a_id: Int = 0,
    var b_name: String = "",
    var b_id: Int = 0,
    var select: Boolean = false
) : Parcelable, Serializable
