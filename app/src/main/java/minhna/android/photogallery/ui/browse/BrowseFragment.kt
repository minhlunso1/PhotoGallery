package minhna.android.photogallery.ui.browse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.util.Pair
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.browse_fragment.*
import minhna.android.photogallery.R
import minhna.android.photogallery.helper.*
import minhna.android.photogallery.model.PhotoEntity
import minhna.android.photogallery.ui.adapter.IPhoto
import minhna.android.photogallery.ui.adapter.PhotoAdapter
import minhna.android.photogallery.ui.base.BaseFragment
import minhna.android.photogallery.ui.details.DetailsActivity
import minhna.android.photogallery.viewmodel.BrowseViewModel

class BrowseFragment : BaseFragment(), IPhoto {
    companion object {
        fun newInstance() = BrowseFragment()
    }

    private lateinit var viewModel: BrowseViewModel
    private val photoList = ArrayList<PhotoEntity?>()
    private lateinit var photoAdapter: PhotoAdapter
    private var page = 1
    private var searchKey = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.browse_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        srl.setOnRefreshListener {
            onResetData()
        }
        setupList()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()

        viewModel.getPhotos(page)
    }

    override fun initViewModel() {
        viewModel = obtainViewModel(BrowseViewModel::class.java, true)!!.apply {
            taskCompleted.observe(this@BrowseFragment, Observer {
                photoAdapter.addData(it)
                "Page load: $page".log()
            })

            errorMsgStr.observe(this@BrowseFragment, Observer {
                it.message.log()
                context?.toast(it.getLocalizedMessage(resources))
            })

            loadingStatus.observe(this@BrowseFragment, Observer {
                srl.isRefreshing = it
            })

            searchQuery.observe(this@BrowseFragment, Observer {
                searchKey = it
                onResetData()
            })
        }
    }

    private fun setupList() {
        rvPhotos.apply {
            val mLayoutManager = GridLayoutManager(context, 2)
            layoutManager = mLayoutManager
            addDividers(this)

            photoAdapter = PhotoAdapter(photoList, this@BrowseFragment)
            adapter = photoAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy > 0) {
                        val visibleItemCount = mLayoutManager.childCount
                        val totalItemCount = mLayoutManager.itemCount
                        val pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition()

                        if (!srl.isRefreshing) {
                            if (visibleItemCount + pastVisibleItems >= totalItemCount)
                                executeGetData(++page)
                        }
                    }
                }
            })
        }
    }

    private fun executeGetData(page: Int) {
        when {
            searchKey.isEmpty() -> viewModel.getPhotos(page)
            else -> viewModel.searchPhotos(searchKey, page)
        }
    }

    private fun addDividers(rv: RecyclerView) {
        val verticalDividerItemDecoration = DividerItemDecoration(
            activity, DividerItemDecoration.VERTICAL
        ).apply {
            setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.divider_vrt)!!)
        }
        rv.addItemDecoration(verticalDividerItemDecoration)

        val horizontalDividerItemDecoration = DividerItemDecoration(
            activity, DividerItemDecoration.HORIZONTAL
        ).apply {
            setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.divider_hrz)!!)
        }
        rv.addItemDecoration(horizontalDividerItemDecoration)
    }

    override fun onItemTap(item: PhotoEntity, view: View) {
        whenNotNull(context) {
            val options = activity?.makeSceneTransitionAnimation(
                Pair(view, getString(R.string.transition_img))
            )
            DetailsActivity.launch(context!!, item.getId(), item.getUrls().getRegular(),
                item.getDescription(), options?.toBundle()!!)
        }
    }

    private fun onResetData() {
        photoAdapter.clearData()
        page = 1
        executeGetData(page)
    }
}

