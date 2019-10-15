package minhna.android.photogallery.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import minhna.android.photogallery.helper.whenNotNull
import minhna.android.photogallery.model.PhotoEntity
import minhna.android.photogallery.remote.pojo.ErrorApi
import minhna.android.photogallery.viewmodel.base.BaseViewModel

/**
 * Search and get photos controller
 */
class BrowseViewModel : BaseViewModel() {
    val taskCompleted: MutableLiveData<List<PhotoEntity?>> = MutableLiveData()
    val searchQuery: MutableLiveData<String> = MutableLiveData()
    lateinit var currentJob: Job

    fun executeGetData(page: Int) {
        val query = searchQuery.value
        when {
            query.isNullOrEmpty() -> getPhotos(page)
            else -> searchPhotos(query, page)
        }
    }

    private fun getPhotos(page: Int) {
        currentJob = viewModelScope.launch(coroutineContext) {
            loadingStatus.postValue(true)
            val response = apiService.getPhotoList(page = page)

            if (response.isSuccessful) {
                loadingStatus.postValue(false)
                whenNotNull(response.body()) {
                    taskCompleted.postValue(response.body())
                }
            } else {
                loadingStatus.postValue(false)
                errorMsgStr.postValue(ErrorApi(response.code(), response.message()))
            }
        }
    }

    private fun searchPhotos(query: String, page: Int) {
        currentJob = viewModelScope.launch(coroutineContext) {
            loadingStatus.postValue(true)
            val response = apiService.search(text = query, page = page)

            if (response.isSuccessful) {
                loadingStatus.postValue(false)
                whenNotNull(response.body()) {
                    taskCompleted.postValue(response.body()!!.results)
                }
            } else {
                loadingStatus.postValue(false)
                errorMsgStr.postValue(ErrorApi(response.code(), response.message()))
            }
        }
    }

    fun onChangeQuery(query: String) {
        whenNotNull(currentJob) {
            if (it.isActive) it.cancel()
        }
        searchQuery.postValue(query)
    }
}
