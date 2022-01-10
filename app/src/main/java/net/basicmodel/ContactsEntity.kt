package net.basicmodel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class ContactsEntity(
    var name: String = "",
    var phone: String = "",
    var avt: String = "",
    var id: String = ""
) : Parcelable, Serializable
