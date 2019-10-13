package minhna.android.photogallery.ui.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.details_activity.*
import minhna.android.minhimageloader.ImageLoader
import minhna.android.photogallery.R
import minhna.android.photogallery.helper.obtainViewModel
import minhna.android.photogallery.model.Const
import minhna.android.photogallery.ui.base.BaseActivity
import minhna.android.photogallery.viewmodel.DetailsViewModel

class DetailsActivity: BaseActivity() {
    private lateinit var viewModel: DetailsViewModel

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
            setupActionBar(it.getStringExtra(Const.BundleKey.DESCRIPTION))

            val url = it.getStringExtra(Const.BundleKey.URL)
            if (!url.isNullOrEmpty())
                loadImage(url)
        }
    }

    override fun initViewModel() {
        viewModel = obtainViewModel(DetailsViewModel::class.java)
    }

    private fun loadImage(imgUrl: String) {
//        supportPostponeEnterTransition()
        ImageLoader.getInstance().displayImage(imgUrl, img, null)
    }

    private fun setupActionBar(title: String?) {
        supportActionBar?.title = title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()

            R.id.metadata -> {

            }

            else -> return false
        }
        return true
    }
}