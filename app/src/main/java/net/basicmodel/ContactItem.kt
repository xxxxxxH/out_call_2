package net.basicmodel

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.angcyo.dsladapter.DslAdapterItem
import com.angcyo.dsladapter.DslViewHolder
import com.bumptech.glide.Glide

class ContactItem(val context: Context) : DslAdapterItem() {
    init {
        itemLayoutId = R.layout.item_contact
    }

    var itemEntity: ContactsEntity? = null
    override fun onItemBind(
        itemHolder: DslViewHolder,
        itemPosition: Int,
        adapterItem: DslAdapterItem,
        payloads: List<Any>
    ) {
        super.onItemBind(itemHolder, itemPosition, adapterItem, payloads)
        val root = itemHolder.view(R.id.listContainer)
        val name = itemHolder.tv(R.id.contactName)
        val phone = itemHolder.tv(R.id.contactNumber)
        val avt = itemHolder.img(R.id.contactImage)
        name!!.text = itemEntity!!.name
        phone!!.text = itemEntity!!.phone
        Glide.with(context).load(itemEntity!!.avt).placeholder(R.mipmap.def).into(avt!!)
        root!!.setOnClickListener {
            call(itemEntity!!.phone)
        }
    }


    fun call(phoneNumber: String) {
        val i = Intent(Intent.ACTION_DIAL)
        val data = Uri.parse("tel:$phoneNumber")
        i.data = data
        context.startActivity(i)
    }
}