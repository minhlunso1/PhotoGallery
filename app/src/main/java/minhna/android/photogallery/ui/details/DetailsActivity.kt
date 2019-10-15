package minhna.android.photogallery.ui.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.details_activity.*
import minhna.android.minhimageloader.ImageLoader
import minhna.android.photogallery.R
import minhna.android.photogallery.helper.*
import minhna.android.photogallery.model.Const
import minhna.android.photogallery.ui.base.BaseActivity
import minhna.android.photogallery.viewmodel.DetailsViewModel

/**
 * MVVM DetailsActivity as View interacts with DetailsViewModel.
 * Show photo.
 * Show metadata view trigger.
 */
class DetailsActivity: BaseActivity() {
    private lateinit var viewModel: DetailsViewModel
    private var photoId = ""
    private var menuItemMetadata: MenuItem? = null

    companion object {
        fun launch(context: Context, photoId: String, photoUrl: String, photoDescription: String, bundle: Bundle) {
            Intent(context, DetailsActivity::class.java)
                .putExtra(Const.BundleKey.ID, photoId)
                .putExtra(Const.BundleKey.URL, photoUrl)
                .putExtra(Const.BundleKey.DESCRIPTION, photoDescription)
                .let { context.startActivity(it, bundle) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_activity)

        intent?.let {
            whenNotNull(it.getStringExtra(Const.BundleKey.ID)) { photoId = it }

            setupActionBar(it.getStringExtra(Const.BundleKey.DESCRIPTION))

            //Expect to get a regular size image url or higher for quality display
            val url = it.getStringExtra(Const.BundleKey.URL)
            if (!url.isNullOrEmpty())
                loadImage(url)
        }
    }

    override fun initViewModel() {
        viewModel = obtainViewModel(DetailsViewModel::class.java).apply {
            photoDetailsTask.observe(this@DetailsActivity, Observer {
                parentView.showAlertDialogItems(resources.getString(R.string.metadata),
                    it.displayMetaData(), {})
            })

            errorMsgStr.observe(this@DetailsActivity, Observer {
                it.message.log()
                toast(it.getLocalizedMessage(resources))
            })

            loadingStatus.observe(this@DetailsActivity, Observer {isLoading ->
                if (isLoading)
                    menuItemMetadata?.actionView = ProgressBar(this@DetailsActivity)
                else
                    menuItemMetadata?.actionView = null
            })
        }
    }

    /**
     * Show photo.
     */
    private fun loadImage(imgUrl: String) {
        ImageLoader.getInstance().displayImage(imgUrl, img, null)
    }

    private fun setupActionBar(title: String?) {
        var textTitle = title
        supportActionBar?.apply {
            if (textTitle.isNullOrEmpty())
                textTitle = getString(R.string.details_photo)
            this.title = textTitle

            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details, menu)
        menuItemMetadata = menu?.findItem(R.id.metadata)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()

            R.id.metadata -> whenNotNull(photoId) { viewModel.getPhotoDetails(it) }

            else -> return false
        }
        return true
    }
}