package minhna.android.photogallery.app

import android.app.Application

/**
 * Apply dagger injection for getting single instance modules.
 */
open class App : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        //DaggerAppComponent only available in compiling time
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
        appComponent.inject(this)
    }
}