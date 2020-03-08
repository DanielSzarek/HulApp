package pl.kamilszustak.hulapp.ui.main.tracking.details.user

import android.app.Application
import pl.kamilszustak.hulapp.data.repository.TrackRepository
import pl.kamilszustak.hulapp.ui.main.tracking.details.base.BaseTrackDetailsViewModel
import javax.inject.Inject

class UserTrackDetailsViewModel @Inject constructor(
    application: Application,
    trackRepository: TrackRepository
) : BaseTrackDetailsViewModel(application, trackRepository)