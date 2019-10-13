package minhna.android.photogallery.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment

abstract class BaseFragment: Fragment() {
    abstract fun initViewModel()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()
    }
}