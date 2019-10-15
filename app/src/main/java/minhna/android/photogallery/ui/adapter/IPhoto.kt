package minhna.android.photogallery.ui.adapter

import android.view.View
import minhna.android.photogallery.model.PhotoEntity

interface IPhoto {
    /**
     * @param item The PhotoEntity object of item tapped.
     * @param view The view container of item tapped.
     */
    fun onItemTap(item: PhotoEntity, view: View)
}
