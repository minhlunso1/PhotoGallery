package minhna.android.photogallery.viewmodel.base

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import minhna.android.photogallery.app.AppComponent
import minhna.android.photogallery.remote.api.ApiService
import minhna.android.photogallery.remote.pojo.ErrorApi
import javax.inject.Inject

/**
 * The design of ViewModel acting as parent.
 * Able to update state.
 * Apply Kotlin Coroutines for worker thread handling.
 * Inherit from ViewModel:
 * @see https://developer.android.com/reference/androidx/lifecycle/ViewModel.html
 */
abstract class BaseViewModel : ViewModel(), AppComponent.Injectable {
    val loadingStatus: MutableLiveData<Boolean> = MutableLiveData()
    val errorMsgStr: MutableLiveData<ErrorApi> = MutableLiveData()

    @Inject
    lateinit var apiService: ApiService
    @Inject
    lateinit var application: Application

    private val job = SupervisorJob()
    protected val coroutineContext = Dispatchers.IO + job

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }
}