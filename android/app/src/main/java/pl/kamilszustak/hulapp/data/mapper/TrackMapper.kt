package pl.kamilszustak.hulapp.data.mapper

import dagger.Reusable
import pl.kamilszustak.hulapp.data.model.track.TrackEntity
import pl.kamilszustak.hulapp.data.model.track.TrackJson
import javax.inject.Inject

@Reusable
class TrackMapper @Inject constructor() : ModelMapper<TrackJson, TrackEntity>() {
    override fun map(model: TrackJson): TrackEntity {
        return TrackEntity(
            startDate = model.startDate,
            endDate = model.endDate,
            duration = model.duration,
            distance = model.distance,
            userId = model.userId
        ).apply {
            this.id = model.id
        }
    }
}
