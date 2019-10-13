package minhna.android.photogallery.remote.pojo

import android.content.res.Resources
import minhna.android.photogallery.R
import minhna.android.photogallery.app.Const

data class Error(val code: Int, val message: String) {

    fun getLocalizedMessage(resouces: Resources) =
        when (code) {
            Const.HTTP_CODE.TIME_OUT -> resouces.getString(R.string.network_error)
            else -> resouces.getString(R.string.error)
        }
}