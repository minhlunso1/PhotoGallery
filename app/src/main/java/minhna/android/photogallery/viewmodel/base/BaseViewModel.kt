package minhna.android.photogallery.viewmodel.base

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import minhna.android.photogallery.app.AppComponent
import javax.inject.Inject

abstract class BaseViewModel : ViewModel(), AppComponent.Injectable {
    val loadingStatus: MutableLiveData<Boolean> = MutableLiveData()
    val errorMsgStr: MutableLiveData<Error> = MutableLiveData()

//    @Inject
//    lateinit var apiService: ApiService
    @Inject
    lateinit var application: Application

    private val job = SupervisorJob()
    protected val coroutineContext = Dispatchers.IO + job

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }
}