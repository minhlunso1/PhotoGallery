@file:JvmName("ExtensionUtils")

package minhna.android.photogallery.helper

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import minhna.android.photogallery.BuildConfig
import minhna.android.photogallery.app.App
import minhna.android.photogallery.app.AppFactory

/**
 * Created by Minh on 2/20/2018.
 */

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

operator fun ViewGroup.get(pos: Int): View = getChildAt(pos)
//usage
//val view = viewGroup[2]

fun Context.toast(message: CharSequence) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Context.getDrawable(resId: Int): Drawable? = ContextCompat.getDrawable(this, resId)

fun AppCompatActivity.getAppCompatDrawable(resId: Int): Drawable? = ContextCompat.getDrawable(this, resId)

//launch activity
inline fun <reified T : Any> Activity.launchActivityForResult(
    requestCode: Int = -1,
    noinline init: Intent.() -> Unit = {}) {
        startActivityForResult(newIntent<T>(this, init), requestCode)
}

inline fun <reified T : Any> Activity.launchActivity(
    noinline init: Intent.() -> Unit = {},
    finish: Boolean) {
    startActivity(newIntent<T>(this, init))
    if (finish) finish()
}

inline fun <reified T : Any> Context.launchActivity(
    noinline init: Intent.() -> Unit = {}) {
        startActivity(newIntent<T>(this, init))
}

inline fun <reified T : Any> newIntent(context: Context, noinline init: Intent.() -> Unit = {}): Intent {
    val intent = Intent(context, T::class.java)
    intent.init()
    return  intent
}

fun View.margin(left: Float? = null, top: Float? = null, right: Float? = null, bottom: Float? = null) {
    layoutParams<ViewGroup.MarginLayoutParams> {
        left?.run { leftMargin = dpToPx(this) }
        top?.run { topMargin = dpToPx(this) }
        right?.run { rightMargin = dpToPx(this) }
        bottom?.run { bottomMargin = dpToPx(this) }
    }
}
inline fun <reified T : ViewGroup.LayoutParams> View.layoutParams(block: T.() -> Unit) {
    if (layoutParams is T) block(layoutParams as T)
}
fun View.dpToPx(dp: Float): Int = context.dpToPx(dp)
fun Context.dpToPx(dp: Float): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()
//

fun String.log(tag: String = BuildConfig.VERSION_NAME) {
    if (BuildConfig.DEBUG)
        Log.d(tag, this)
}

fun Context.getBitmap(drawableRes: Int): Bitmap {
    val drawable = getDrawable(drawableRes)!!
    val canvas = Canvas()
    val bitmap =
        Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
    canvas.setBitmap(bitmap)
    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
    drawable.draw(canvas)

    return bitmap
}

fun getScreenWidth(): Int {
    return Resources.getSystem().displayMetrics.widthPixels
}

fun getScreenHeight(): Int {
    return Resources.getSystem().displayMetrics.heightPixels
}

fun <T : ViewModel> Fragment.obtainViewModel(
    viewModelClass: Class<T>,
    activityObserver: Boolean = true
) =
activity?.let {
    if (activityObserver)
        ViewModelProviders.of(it, AppFactory(it.application as App)).get(viewModelClass)
    else
        ViewModelProviders.of(this, AppFactory(it.application as App)).get(viewModelClass)
}

fun <T : ViewModel> Fragment.obtainViewModel(viewModelClass: Class<T>) =
ViewModelProviders.of(this, AppFactory(this.context?.applicationContext as App)).get(
    viewModelClass
)

fun <T : ViewModel> AppCompatActivity.obtainViewModel(viewModelClass: Class<T>) =
ViewModelProviders.of(this, AppFactory(this.application as App)).get(
    viewModelClass
)

inline fun <T, R> whenNotNull(input: T?, callback: (T)->R): R? {
    return input?.let(callback)
}

fun Activity.makeSceneTransitionAnimation(vararg pairs: androidx.core.util.Pair<View, String>): ActivityOptionsCompat {
    val updatedPairs = pairs.toMutableList()
        val navBar = this.findViewById<View>(android.R.id.navigationBarBackground)
        if (navBar != null) {
            updatedPairs.add(androidx.core.util.Pair(navBar, Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME))
    }
    return ActivityOptionsCompat.makeSceneTransitionAnimation(this, *updatedPairs.toTypedArray())
}