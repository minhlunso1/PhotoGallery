package minhna.android.photogallery.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import minhna.android.photogallery.app.App
import minhna.android.photogallery.app.AppComponent

class AppFactory(val application: App) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val t: T = super.create(modelClass)
        if (t is AppComponent.Injectable) {
            (t as AppComponent.Injectable).inject(application.appComponent)
        }

        return t
    }
}