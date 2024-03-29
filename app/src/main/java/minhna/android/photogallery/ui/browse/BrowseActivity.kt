package minhna.android.photogallery.ui.browse

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import minhna.android.photogallery.R
import minhna.android.photogallery.helper.obtainViewModel
import minhna.android.photogallery.ui.base.BaseActivity
import minhna.android.photogallery.viewmodel.BrowseViewModel

/**
 * MVVM BrowseActivity as View interacts with BrowseViewModel.
 * Search event view trigger.
 */
class BrowseActivity : BaseActivity() {
    //View interaction with Fragment via ViewModel.
    private lateinit var viewModel: BrowseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.browse_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, BrowseFragment.newInstance())
                .commitNow()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.browse, menu)
        setupSearch(menu)

        return true
    }

    override fun initViewModel() {
        viewModel = obtainViewModel(BrowseViewModel::class.java)
    }

    private fun setupSearch(menu: Menu?) {
        val searchViewItem = menu!!.findItem(R.id.search)
        val searchView = searchViewItem.actionView as SearchView
        searchView.maxWidth = Int.MAX_VALUE
        searchView.queryHint = getString(R.string.search_photo)

        val viewClose = searchView.findViewById<View>(R.id.search_close_btn)
        viewClose.setOnClickListener {
            searchView.onActionViewCollapsed()
            searchViewItem.collapseActionView()
            viewModel.searchQuery.postValue("")
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                searchView.clearFocus()
                viewModel.onChangeQuery(query)
                return false
            }
        })
    }

}