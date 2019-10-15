package minhna.android.photogallery.app

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @param application Provide this as an application instance.
 * Injectable with dagger.
 */
@Module
class AppModule(private val application: Application) {
    @Provides
    @Singleton
    fun provideApplication(): Application = application
}