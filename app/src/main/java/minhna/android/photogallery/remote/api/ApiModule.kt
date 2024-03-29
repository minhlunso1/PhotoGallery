package minhna.android.photogallery.remote.api

import android.app.Application
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import minhna.android.photogallery.BuildConfig
import minhna.android.photogallery.model.Const
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * Build and get instance of net interaction module from this class.
 * Injectable with dagger.
 * Retrofit is considered as Android base tool component for remote service.
 * @see https://developer.android.com/jetpack/docs/guide#overview
 */
@Module
class ApiModule {
    val TIME_OUT: Long = 20

    /**
     * Http cache for request header
     * @param application Provided by AppModule
     */
    @Provides
    @Singleton
    fun provideOkHttpCache(application: Application): Cache {
        val size: Long = 100 * 1024 * 1024 // 100Mb
        return Cache(application.cacheDir, size)
    }

    @Provides
    @Singleton
    @Named("logger")
    fun provideLoggerInterceptor(): Interceptor {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY

        return logger
    }

    /**
     * Header request with json type and authorization for all api calls.
     */
    @Provides
    @Singleton
    @Named("header")
    fun provideHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()

            val request = original.newBuilder()
                .header("Content-Type", "application/json")
                .header("Authorization", "Client-ID ${BuildConfig.API_KEY}")
                .method(original.method(), original.body())
                .build()

            return@Interceptor chain.proceed(request)
        }
    }

    /**
     * Interceptor for solving different kinds of network errors
     */
    @Provides
    @Singleton
    @Named("connectionError")
    fun provideConnectionInterceptor(): Interceptor {
        return Interceptor { chain ->
            try {
                chain.proceed(chain.request())
            } catch (unknownHostException: UnknownHostException) {
                okhttp3.Response.Builder().request(chain.request())
                    .protocol(Protocol.HTTP_1_1)
                    .body(ResponseBody.create(MediaType.get("application/json"), ""))
                    .message("Network error")
                    .code(Const.HTTP_CODE.TIME_OUT)
                    .build()
            } catch (exception: Exception) {
                okhttp3.Response.Builder().request(chain.request())
                    .protocol(Protocol.HTTP_1_1)
                    .body(ResponseBody.create(MediaType.get("application/json"), ""))
                    .code(0)
                    .message("Error")
                    .build()
            }
        }
    }

    @Provides
    @Singleton
    @Named("auth_okhttp")
    fun provideAuthOkHttpClient(cache: Cache, @Named("logger") logger: Interceptor,
                                  @Named("header") jsonInterceptor: Interceptor,
                                  @Named("connectionError") networkInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(cache)
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(logger)
            .addInterceptor(jsonInterceptor)
            .addInterceptor(networkInterceptor)
            .build()
    }

    /**
     * Retrofit with Moshi convert adapter.
     * Moshi for parsing model object.
     */
    @Provides
    @Singleton
    @Named("auth_retrofit")
    fun provideAuthRetrofit(@Named("auth_okhttp") okHttpClient: OkHttpClient): Retrofit {
        val moshi = Moshi.Builder().build()
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
            .baseUrl(BuildConfig.API_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(@Named("auth_retrofit") retrofit: Retrofit): ApiService {
        if (BuildConfig.API_URL.isEmpty()) {
            throw IllegalArgumentException("Base url is empty")
        }

        return retrofit.create(ApiService::class.java)
    }
}
