package net.basicmodel

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.mylhyl.acp.Acp
import com.mylhyl.acp.AcpListener
import com.mylhyl.acp.AcpOptions
import es.dmoral.prefs.Prefs
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity:AppCompatActivity() {

    private val handle:Handler= @SuppressLint("HandlerLeak")
    object :Handler(){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            Toasty.success(this@SplashActivity,"Thank you for your choice").show()
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Toasty.Config.getInstance()
            .apply()
        Glide.with(this)
            .asGif()
            .load(R.drawable.welcome)
            .into(img)
        Acp.getInstance(this).request(
            AcpOptions.Builder().setPermissions(
                "android.permission.ANSWER_PHONE_CALLS",
                "android.permission.READ_PHONE_STATE",
                "android.permission.READ_CONTACTS",
                "android.permission.WRITE_CONTACTS",
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE",
                "android.permission.CALL_PHONE",
                "android.permission.MODIFY_AUDIO_SETTINGS",
                "android.permission.VIBRATE",
                "android.permission.CAMERA",
                "android.permission.FLASHLIGHT",
                "android.permission.RECEIVE_BOOT_COMPLETED",
                "android.permission.INTERNET",
                "android.permission.ACCESS_NETWORK_STATE",
                "android.permission.READ_CALL_LOG",
        ).build(), object : AcpListener {
            override fun onGranted() {
                handle.sendEmptyMessageDelayed(1,1500)
            }

            override fun onDenied(permissions: MutableList<String>?) {
                finish()
            }

        })
    }
}