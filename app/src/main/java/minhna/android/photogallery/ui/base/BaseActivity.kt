package minhna.android.photogallery.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * The design of Activity acting as parent.
 * Following MVVM with the ViewModel initialization.
 * Inherit from AppCompatActivity
 * @see https://developer.android.com/reference/androidx/appcompat/app/AppCompatActivity
 */
abstract class BaseActivity: AppCompatActivity() {
    abstract fun initViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }
}