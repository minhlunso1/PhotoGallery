package minhna.android.photogallery.ui.adapter

import android.view.View
import minhna.android.photogallery.model.PhotoEntity

interface IPhoto {
    fun onItemTap(item: PhotoEntity, view: View)
}
