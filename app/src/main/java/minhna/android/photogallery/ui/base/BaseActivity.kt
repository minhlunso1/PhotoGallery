package minhna.android.photogallery.ui.base

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity: AppCompatActivity() {
    abstract fun initViewModel()
}