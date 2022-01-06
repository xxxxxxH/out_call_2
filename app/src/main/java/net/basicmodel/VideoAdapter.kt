package net.basicmodel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class VideoAdapter(
    private val context: Context,
    private val data: ArrayList<VideoEntity>,
    private val targetWidth: Int,
    private val targetHeight: Int,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<VideoAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemRoot: RelativeLayout = itemView.findViewById(R.id.itemRoot)
        var itemImg: ImageView = itemView.findViewById(R.id.itemImg)
        var itemSelect: ImageView = itemView.findViewById(R.id.itemSelect)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemRoot.let {
            it.layoutParams = it.layoutParams.apply {
                width = targetWidth
                height = targetHeight
            }
            it.setOnClickListener {
                listener.OnItemClick(holder.layoutPosition)
            }
        }
        Glide.with(context)
            .load(data[position].id)
            .into(holder.itemImg)
        holder.itemSelect.visibility = if (data[position].select) View.VISIBLE else View.GONE
    }

    override fun getItemCount(): Int {
        return data.size
    }
}