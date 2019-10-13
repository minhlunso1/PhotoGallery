package minhna.android.photogallery.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import minhna.android.photogallery.helper.whenNotNull
import minhna.android.photogallery.model.PhotoEntity
import minhna.android.photogallery.remote.pojo.ErrorApi
import minhna.android.photogallery.viewmodel.base.BaseViewModel

class DetailsViewModel : BaseViewModel() {
    val photoDetailsTask: MutableLiveData<PhotoEntity> = MutableLiveData()

    fun getPhotoDetails(id: String) {

        if (photoDetailsTask.value != null)
            photoDetailsTask.postValue(photoDetailsTask.value)
        else {
            viewModelScope.launch(coroutineContext) {
                loadingStatus.postValue(true)
                val response = apiService.getPhotoDetails(id)

                if (response.isSuccessful) {
                    loadingStatus.postValue(false)
                    whenNotNull(response.body()) {
                        photoDetailsTask.postValue(response.body())
                    }
                } else {
                    loadingStatus.postValue(false)
                    errorMsgStr.postValue(ErrorApi(response.code(), response.message()))
                }
            }
        }
    }
}
