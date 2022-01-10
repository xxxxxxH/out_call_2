package net.basicmodel

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class PhoneReceiver:BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context!!.startService(Intent(context, PhoneService::class.java))
    }
}