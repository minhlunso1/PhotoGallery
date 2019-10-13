package minhna.android.photogallery

import kotlinx.coroutines.runBlocking
import minhna.android.photogallery.app.*
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import androidx.test.rule.ActivityTestRule
import minhna.android.photogallery.ui.browse.BrowseActivity
import org.junit.Rule

class ApiTest {
    lateinit var appComponent: AppComponent

    @get:Rule
    val mActivityRule: ActivityTestRule<BrowseActivity> = ActivityTestRule(BrowseActivity::class.java)

    @Before
    fun getAppModule() {
        val app = mActivityRule.activity.application

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(app))
            .build()
        appComponent.inject(app as App)
    }

    @Test
    fun apiPhotoList() {
        runBlocking {
            val response = appComponent.apiService().getPhotoList(page = 1)
            assert(response.isSuccessful && response.code() == Const.HTTP_CODE.SUCCESSFUL)
            assertTrue(response.body()!!.isNotEmpty())
        }
    }

    @Test
    fun apiSearch() {
        runBlocking {
            val response = appComponent.apiService().search(text = "nature", page = 1)
            assert(response.isSuccessful && response.code() == Const.HTTP_CODE.SUCCESSFUL)
            assertTrue(response.body()!!.results.isNotEmpty())
        }
    }
}
