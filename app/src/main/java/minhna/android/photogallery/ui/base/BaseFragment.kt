package minhna.android.photogallery.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment

/**
 * The design of Activity acting as parent.
 * Following MVVM with the ViewModel initialization.
 * Inherit from Fragment:
 * @see https://developer.android.com/reference/kotlin/androidx/fragment/app/package-summary
 */
abstract class BaseFragment: Fragment() {
    abstract fun initViewModel()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()
    }
}