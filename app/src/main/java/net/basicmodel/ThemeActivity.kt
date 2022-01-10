package net.basicmodel

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.poliveira.parallaxrecyclerview.ParallaxRecyclerAdapter
import es.dmoral.prefs.Prefs
import kotlinx.android.synthetic.main.activity_theme.*
import org.greenrobot.eventbus.EventBus

@RequiresApi(Build.VERSION_CODES.N)
class ThemeActivity : AppCompatActivity() {

    var data = ArrayList<ThemeEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_theme)
        val selectId = Prefs.with(this).readInt("themeId", -1)
        data = get()
        data.forEach {
            it.select = it.a_id == selectId || it.b_id == selectId
        }
        val adapter = object : ParallaxRecyclerAdapter<ThemeEntity>(data) {
            override fun onBindViewHolderImpl(
                p0: RecyclerView.ViewHolder?,
                p1: ParallaxRecyclerAdapter<ThemeEntity>?,
                p2: Int
            ) {

                (p0 as ThemeActivity.ViewHolder).screenLayout.let {
                    it.layoutParams = it.layoutParams.apply {
                        width = getScreenWidth() / 2
                        height = getScreenHeight() / 2
                    }
                }
                Glide.with(this@ThemeActivity)
                    .load(data[p2].a_id)
                    .into(p0.answerButton)
                Glide.with(this@ThemeActivity)
                    .load(data[p2].b_id)
                    .into(p0.rejectButton)
                p0.sel_lay.visibility = if (data[p2].select) View.VISIBLE else View.GONE
                if (p2 != 0){
                    Glide.with(this@ThemeActivity)
                        .load(R.mipmap.def)
                        .into(p0.centerImage)
                }
            }

            override fun onCreateViewHolderImpl(
                p0: ViewGroup?,
                p1: ParallaxRecyclerAdapter<ThemeEntity>?,
                p2: Int
            ): RecyclerView.ViewHolder {
                val v = layoutInflater.inflate(R.layout.layout_item_2,p0,false)
                return ThemeActivity.ViewHolder(v)
            }

            override fun getItemCountImpl(p0: ParallaxRecyclerAdapter<ThemeEntity>?): Int {
                return data.size
            }

        }

        adapter.data = data
        recycler.layoutManager = GridLayoutManager(this,2)
        recycler.adapter = adapter
        adapter.setOnClickEvent { _, i ->
            for ((index,item) in data.withIndex()){
                if (i == index){
                    item.select = true
                    Prefs.with(this).writeInt("themeId", item.a_id)
                    EventBus.getDefault().post(MessageEvent("themeId", item.a_id))
                }else{
                    item.select = false
                }
            }
            adapter.notifyDataSetChanged()
            finish()
        }
    }

    private fun getScreenWidth(): Int {
        val display = this.windowManager.defaultDisplay
        return display.width
    }

    private fun getScreenHeight(): Int {
        val display = this.windowManager.defaultDisplay
        return display.height
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

    internal class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var screenLayout: RelativeLayout = itemView.findViewById(R.id.screenLayout)
        var answerButton: ImageView = itemView.findViewById(R.id.answerButton)
        var rejectButton: ImageView = itemView.findViewById(R.id.rejectButton)
        var sel_lay: ImageView = itemView.findViewById(R.id.sel_lay)
        var centerImage:ImageView = itemView.findViewById(R.id.centerImage)
    }
}