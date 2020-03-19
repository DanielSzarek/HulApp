package pl.kamilszustak.hulapp.ui.main.tracking.details.base

import androidx.annotation.LayoutRes
import pl.kamilszustak.hulapp.ui.base.BaseFragment

abstract class BaseTrackDetailsFragment : BaseFragment {

    constructor() : super()
    constructor(@LayoutRes layoutResource: Int) : super(layoutResource)
}