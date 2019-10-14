package minhna.android.photogallery.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_photo.view.*
import minhna.android.minhimageloader.ImageLoader
import minhna.android.photogallery.R
import minhna.android.photogallery.helper.inflate
import minhna.android.photogallery.helper.whenNotNull
import minhna.android.photogallery.model.PhotoEntity

class PhotoAdapter(private val list: ArrayList<PhotoEntity?>, private val iView: IPhoto): RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(parent, viewType)

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list, iView)
    }

    fun addData(newList: List<PhotoEntity?>) {
        val size = list.size
        list.addAll(newList)
        val sizeNew = list.size
        notifyItemRangeChanged(size, sizeNew)
    }

    fun clearData() {
        list.clear()
        notifyDataSetChanged()
    }

    class ViewHolder(parent: ViewGroup, viewType: Int) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_photo)) {
        fun bind(list: List<PhotoEntity?>, listener: IPhoto) = with(itemView) {

            whenNotNull(list[adapterPosition]) {
                ImageLoader.getInstance().displayImage(list[adapterPosition]!!.getUrls().getSmall(),
                    img, R.drawable.bg_dim)

                itemView.setOnClickListener {
                    listener.onItemTap(list[adapterPosition]!!, it)
                }
            }
        }
    }
}