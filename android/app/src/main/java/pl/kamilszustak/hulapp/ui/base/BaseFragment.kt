package pl.kamilszustak.hulapp.ui.base

import android.content.Context
import androidx.annotation.LayoutRes
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

open class BaseFragment : Fragment, HasAndroidInjector {

    constructor() : super()

    constructor(@LayoutRes layoutResourceId: Int) : super(layoutResourceId)

    @Inject
    protected lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    private val actionBar: ActionBar?
        get() = (activity as? BaseActivity)?.supportActionBar

    protected var actionBarTitle: CharSequence?
        get() = actionBar?.title
        set(value) {
            actionBar?.title = value
        }

    protected fun hideActionBar() {
        actionBar?.hide()
    }

    protected fun showActionBar() {
        actionBar?.show()
    }
}