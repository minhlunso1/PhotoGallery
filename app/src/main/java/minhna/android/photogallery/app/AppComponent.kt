package minhna.android.photogallery.app

import android.app.Application
import dagger.Component
import minhna.android.photogallery.viewmodel.BrowseViewModel
import minhna.android.photogallery.viewmodel.base.BaseViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent {
    fun application(): Application

//    fun apiService(): ApiService

    interface Injectable {
        fun inject(appComponent: AppComponent)
    }

    fun inject(app: App)

    fun inject(viewModel: BaseViewModel)
    fun inject(viewModel: BrowseViewModel)
}