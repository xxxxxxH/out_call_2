package net.basicmodel

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.bumptech.glide.Glide
import es.dmoral.prefs.Prefs
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.File

class MainActivity : AppCompatActivity() {
    private var entity: ThemeEntity? = null
    private var curVideo = -1
    private var curTheme = -1

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        EventBus.getDefault().register(this)
        curVideo = Prefs.with(this).readInt("videoId", -1)
        if (curVideo != -1) {
            Glide.with(this)
                .load(curVideo)
                .into(defVideo)
        } else {
            defVideoTv.text = "No Video"
        }
        curTheme = Prefs.with(this).readInt("themeId", -1)
        if (curTheme != -1) {
            val data = get()
            for (item in data) {
                if (item.a_id == curTheme || item.b_id == curTheme) {
                    entity = item
                    break
                }
            }
            entity?.let {
                Glide.with(this)
                    .load(it.a_id)
                    .into(defThemeAnswer)
                Glide.with(this)
                    .load(it.b_id)
                    .into(defThemeReject)
            }
        } else {
            defThemeTv.text = "No Theme"
        }
        menu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        nv.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.video -> {
                    startActivity(Intent(this, VideoActivity::class.java))
                }
                R.id.theme -> {
                    startActivity(Intent(this, ThemeActivity::class.java))
                }
                R.id.block -> {

                }
                R.id.setting -> {
                    startActivity(Intent(this, SettingActivity::class.java))
                }
                R.id.cur_video -> {
                    if (curVideo == -1) {
                        Toasty.success(this, "No Video").show()
                    } else {
                        val map = getVideoData()
                        val data = ArrayList<VideoEntity>()
                        var urlName = ""
                        map.forEach { (k, v) ->
                            data.add(VideoEntity(k, v, false))
                        }
                        for (item in data) {
                            if (item.id == curVideo) {
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
                        if (File(filePath).exists()) {
                            val i = Intent(this, VideoPreviewActivity::class.java)
                            i.putExtra("url", filePath)
                            i.putExtra("name", "Current Video")
                            startActivity(i)
                        }
                    }
                }
                R.id.cur_theme -> {
                    if (curTheme == -1) {
                        Toasty.success(this, "No Theme").show()
                    } else {
                        val i = Intent(this, ThemePreviewActivity::class.java)
                        i.putExtra("id1", entity!!.a_id)
                        i.putExtra("id2", entity!!.b_id)
                        startActivity(i)
                    }
                }

                R.id.clear -> {
                    Prefs.with(this).clear()
                    curVideo = -1
                    curTheme = -1
                    Glide.with(this)
                        .load("")
                        .into(defVideo)
                    Glide.with(this)
                        .load("")
                        .into(defThemeAnswer)
                    Glide.with(this)
                        .load("")
                        .into(defThemeReject)
                    defVideoTv.text = "No Video"
                    defThemeTv.text = "No Theme"
                    Toasty.success(
                        this,
                        "All options have been cleared for you and will take effect after restart"
                    ).show()
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        val msg = event.getMessage()
        when (msg[0]) {
            "videoId" -> {
                curVideo = msg[1] as Int
                Glide.with(this)
                    .load(curVideo)
                    .into(defVideo)
                defVideoTv.text = "Current Video"

            }
            "themeId" -> {
                defThemeTv.text = "Current Theme"
                curTheme = msg[1] as Int
                val data = get()
                for (item in data) {
                    if (item.a_id == curTheme || item.b_id == curTheme) {
                        entity = item
                        break
                    }
                }
                entity?.let {
                    Glide.with(this)
                        .load(it.a_id)
                        .into(defThemeAnswer)
                    Glide.with(this)
                        .load(it.b_id)
                        .into(defThemeReject)
                }
            }
        }
    }
}