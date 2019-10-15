package minhna.android.photogallery.app

import android.app.Application
import dagger.Component
import minhna.android.photogallery.remote.api.ApiModule
import minhna.android.photogallery.remote.api.ApiService
import minhna.android.photogallery.viewmodel.base.BaseViewModel
import javax.inject.Singleton

/**
 * Define which types of modules could be used and the target usage.
 */
@Singleton
@Component(modules = [(AppModule::class), (ApiModule::class)])
interface AppComponent {
    fun application(): Application

    fun apiService(): ApiService

    interface Injectable {
        fun inject(appComponent: AppComponent)
    }

    //Declare the targets here
    fun inject(app: App)
    fun inject(viewModel: BaseViewModel)
}