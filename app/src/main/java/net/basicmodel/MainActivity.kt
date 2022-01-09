package net.basicmodel

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.bumptech.glide.Glide
import es.dmoral.prefs.Prefs
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val curVideo = Prefs.with(this).readInt("videoId", -1)
        if (curVideo != -1) {
            Glide.with(this)
                .load(curVideo)
                .into(defVideo)
        } else {
            defVideoTv.text = "No Video"
        }
        val curTheme = Prefs.with(this).readInt("themeId", -1)
        if (curTheme != -1) {
            val data = get()
            var entity: ThemeEntity? = null
            for (item in data) {
                if (item.a_id == curTheme || item.b_id == curTheme) {
                    entity = item
                    break
                }
            }
            entity?.let {
                Glide.with(this)
                    .load(entity.a_id)
                    .into(defThemeAnswer)
                Glide.with(this)
                    .load(entity.b_id)
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
                R.id.cur_video -> {
                    if (curVideo == -1){
                        Toasty.success(this,"No Video").show()
                    }else{

                    }
                }
                R.id.cur_theme ->{
                    if (curTheme == -1){
                        Toasty.success(this,"No Theme").show()
                    }else{

                    }
                }

                R.id.clear -> {
                    Prefs.with(this).clear()
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
}