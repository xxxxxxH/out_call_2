package net.basicmodel

import DisconectClass
import RecivedClass
import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.media.AudioManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.IBinder
import android.provider.Contacts
import android.provider.ContactsContract
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.VideoView
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import es.dmoral.prefs.Prefs
import java.io.File

class PhoneService : Service() {
    var phoneStateListener: PhoneStateListener? = null
    var telephonyManager: TelephonyManager? = null
    var wm: WindowManager? = null
    var audioManager: AudioManager? = null
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    @SuppressLint("WrongConstant")
    override fun onCreate() {
        super.onCreate()
        wm = getSystemService("window") as WindowManager
        audioManager = getSystemService("audio") as AudioManager
        telephonyManager = getSystemService(Service.TELEPHONY_SERVICE) as TelephonyManager
        phoneStateListener = object : PhoneStateListener() {
            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onCallStateChanged(state: Int, number: String) {
                super.onCallStateChanged(state, number)
                if (state == TelephonyManager.CALL_STATE_RINGING) {
                    showMyScreen(number)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun showMyScreen(num: String) {
        var LAYOUT_FLAG: Int
        LAYOUT_FLAG = if (Build.VERSION.SDK_INT >= 26) {
            2038
        } else {
            2002
        }
        val view = LayoutInflater.from(this).inflate(R.layout.layout_my_screen, null)
        view.findViewById<TextView>(R.id.callerName).text = getContactName(this, num)
        view.findViewById<TextView>(R.id.callerNumber).text = num
        val videoPath = Prefs.with(this).readInt("videoId", -1)
        val video = view.findViewById<VideoView>(R.id.callVideo)
        if (videoPath != -1) {
            val map = getVideoData()
            val data = ArrayList<VideoEntity>()
            var urlName = ""
            map.forEach { (k, v) ->
                data.add(VideoEntity(k, v, false))
            }
            for (item in data) {
                if (item.id == videoPath) {
                    urlName = item.name
                    break
                }
            }
            FileUtils.copyFilesFromRaw(
                this,
                resources.getIdentifier(urlName, "raw", this.packageName),
                "$urlName.mp4",
                Environment.getExternalStorageDirectory().toString()
            )
            val filePath = Environment.getExternalStorageDirectory()
                .toString() + File.separator + "$urlName.mp4"
            video.setVideoPath(filePath)
            video.start()
            video.setOnPreparedListener {
                it.isLooping = true
                it.setVolume(0f, 0f)
            }
        }

        val curTheme = Prefs.with(this).readInt("themeId", -1)
        var data = ArrayList<ThemeEntity>()
        data = get()
        var entity: ThemeEntity? = null
        for (item in data) {
            if (item.a_id == curTheme || item.b_id == curTheme) {
                entity = item
                break
            }
        }

        val answer = view.findViewById<ImageView>(R.id.answerCall)
        val disconect = view.findViewById<ImageView>(R.id.disconectCall)

        if (curTheme != -1) {
            Glide.with(this).load(entity!!.a_id)
                .into(answer)
            Glide.with(this).load(entity.b_id)
                .into(disconect)
        }

        disconect.setOnClickListener {
            if (Build.VERSION.SDK_INT >= 26) {
                DisconectClass(this).rejectCall(this)
            } else {
                DisconectClass(this).disconnectCall()
            }
            wm!!.removeViewImmediate(view)
        }
        answer.setOnClickListener {
            RecivedClass(this).sendHeadsetHookLollipop()
            wm!!.removeViewImmediate(view)
        }
        audioManager!!.setStreamVolume(3, 0, 0)
        wm!!.addView(view, WindowManager.LayoutParams(-1, -1, LAYOUT_FLAG, 19399552, -3))
    }

    private fun getContactName(context: Context, number: String?): String? {
        if (TextUtils.isEmpty(number)) {
            return null
        }
        val resolver = context.contentResolver
        var lookupUri: Uri?
        val projection =
            arrayOf(ContactsContract.PhoneLookup._ID, ContactsContract.PhoneLookup.DISPLAY_NAME)
        var cursor: Cursor? = null
        try {
            lookupUri = Uri.withAppendedPath(
                ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(number)
            )
            cursor = resolver.query(lookupUri, projection, null, null, null)
        } catch (ex: Exception) {
            ex.printStackTrace()
            try {
                lookupUri = Uri.withAppendedPath(
                    Contacts.Phones.CONTENT_FILTER_URL,
                    Uri.encode(number)
                )
                cursor = resolver.query(lookupUri, projection, null, null, null)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        var ret: String? = "unknow"
        if (cursor != null && cursor.count > 0 && cursor.moveToFirst()) {
            ret = cursor.getString(1)
            cursor.close()
        }
        return ret
    }

    private fun getVideoData(): HashMap<String, Int> {
        val result = HashMap<String, Int>()
        result["theme1"] = R.raw.them1
        result["theme2"] = R.raw.them2
        result["theme3"] = R.raw.them3
        result["theme4"] = R.raw.them4
        result["theme5"] = R.raw.them5
        result["theme6"] = R.raw.them6
        result["theme7"] = R.raw.theme7
        result["theme8"] = R.raw.theme8
        result["theme9"] = R.raw.theme9
        result["theme10"] = R.raw.theme10
        result["theme11"] = R.raw.theme11
        result["theme12"] = R.raw.theme12
        result["theme13"] = R.raw.theme13
        result["theme14"] = R.raw.theme14
        result["theme15"] = R.raw.theme15
        result["theme16"] = R.raw.theme16
        result["theme17"] = R.raw.theme17
        result["theme18"] = R.raw.theme18
        result["theme19"] = R.raw.theme19
        return result
    }

    private fun get(): ArrayList<ThemeEntity> {
        val result = ArrayList<ThemeEntity>()
        val entity_a =
            ThemeEntity("a_answer", R.mipmap.a_answer, "a_reject", R.mipmap.a_reject, false)
        val entity_b =
            ThemeEntity("b_answer", R.mipmap.b_answer, "b_reject", R.mipmap.b_reject, false)
        val entity_c =
            ThemeEntity("c_answer", R.mipmap.c_answer, "c_reject", R.mipmap.c_reject, false)
        val entity_d =
            ThemeEntity("d_answer", R.mipmap.d_answer, "d_reject", R.mipmap.d_reject, false)
        val entity_e =
            ThemeEntity("e_answer", R.mipmap.e_answer, "e_reject", R.mipmap.e_reject, false)
        val entity_f =
            ThemeEntity("f_answer", R.mipmap.f_answer, "f_reject", R.mipmap.f_reject, false)
        val entity_g =
            ThemeEntity("g_answer", R.mipmap.g_answer, "g_reject", R.mipmap.g_reject, false)
        result.add(entity_a)
        result.add(entity_b)
        result.add(entity_c)
        result.add(entity_d)
        result.add(entity_e)
        result.add(entity_f)
        result.add(entity_g)
        return result
    }
}