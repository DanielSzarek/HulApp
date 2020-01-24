package pl.kamilszustak.hulapp.ui.base

import android.content.Context
import android.os.Bundle
import androidx.annotation.XmlRes
import androidx.preference.PreferenceFragmentCompat
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

open class BasePreferenceFragment(
    @XmlRes private val layoutResourceId: Int? = null
) : PreferenceFragmentCompat(), HasAndroidInjector {

    @Inject
    protected lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        if (layoutResourceId != null) {
            setPreferencesFromResource(layoutResourceId, rootKey)
        }
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}