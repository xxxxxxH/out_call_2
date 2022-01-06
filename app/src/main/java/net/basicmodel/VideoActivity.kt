package net.basicmodel

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import es.dmoral.prefs.Prefs
import kotlinx.android.synthetic.main.activity_video.*

@RequiresApi(Build.VERSION_CODES.N)
class VideoActivity : AppCompatActivity(), OnItemClickListener {
    val data = ArrayList<VideoEntity>()
    var adapter: VideoAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
        val map = getData()
        val selectId = Prefs.with(this).readInt("videoId", -1)
        map.forEach { (key, value) ->
            val entity = VideoEntity()
            if (selectId != -1) {
                entity.select = selectId == value
            }
            entity.name = key
            entity.id = value
            data.add(entity)
        }
        adapter = VideoAdapter(this, data, getScreenWidth() / 2, getScreenHeight() / 2, this)
        recycler.layoutManager = GridLayoutManager(this, 2)
        recycler.adapter = adapter
    }

    private fun getScreenHeight(): Int {
        val display = this.windowManager.defaultDisplay
        return display.height
    }

    private fun getScreenWidth(): Int {
        val display = this.windowManager.defaultDisplay
        return display.width
    }

    private fun getData(): HashMap<String, Int> {
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

    override fun OnItemClick(position: Int) {
        for ((index, item) in data.withIndex()) {
            if (index == position) {
                item.select = true
                Prefs.with(this).writeInt("videoId", item.id)
            } else {
                item.select = false
            }
        }
        adapter!!.notifyDataSetChanged()
    }
}