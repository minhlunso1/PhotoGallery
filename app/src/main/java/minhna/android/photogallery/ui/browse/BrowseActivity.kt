package minhna.android.photogallery.ui.browse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import minhna.android.photogallery.R

class BrowseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.browse_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, BrowseFragment.newInstance())
                .commitNow()
        }
    }

}
