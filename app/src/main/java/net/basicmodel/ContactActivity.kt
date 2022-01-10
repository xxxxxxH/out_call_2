package net.basicmodel

import android.os.Bundle
import android.provider.ContactsContract
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.angcyo.dsladapter.DslAdapter
import kotlinx.android.synthetic.main.activity_block.*
import java.util.*
import kotlin.collections.ArrayList

class ContactActivity : AppCompatActivity() {
    var data:ArrayList<ContactsEntity>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_block)
        data = get()
        recycler.layoutManager = LinearLayoutManager(this)
        val adapter = DslAdapter()
        data!!.forEach {
            val item = ContactItem(this)
            item.itemEntity = it
            adapter.addLastItem(item)
        }
        recycler.adapter = adapter
        searchLocation.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val index = findItem(s.toString())
                if (index != -1) {
                    recycler.smoothScrollToPosition(index)
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }
    private fun findItem(key: String): Int {
        var index = -1
        for ((i, item) in data!!.withIndex()) {
            if (item.name.toLowerCase(Locale.ROOT).contains(key.toLowerCase(Locale.ROOT))) {
                index = i
                break
            }
        }
        return index
    }
    private fun get(): ArrayList<ContactsEntity> {
        val result = ArrayList<ContactsEntity>()
        val c = this.contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        while (c!!.moveToNext()) {
            val entity = ContactsEntity()
            val id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID))
            val name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
            val av = c.getString(c.getColumnIndex(ContactsContract.Contacts.PHOTO_URI))
            entity.id = id
            entity.name = name
            entity.avt = av
            val c1 = this.contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + id, null, null
            )
            while (c1!!.moveToNext()) {
                val phone =
                    c1.getString(c1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                entity.phone = phone
            }
            result.add(entity)
            c1.close()
        }
        c.close()

        return result
    }
}