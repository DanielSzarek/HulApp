package pl.kamilszustak.hulapp.domain.mapper.track

import dagger.Reusable
import pl.kamilszustak.hulapp.domain.mapper.JsonModelMapper
import pl.kamilszustak.hulapp.domain.model.track.TrackEntity
import pl.kamilszustak.hulapp.domain.model.track.TrackJson
import javax.inject.Inject

@Reusable
class TrackMapper @Inject constructor() : JsonModelMapper<TrackJson, TrackEntity>() {
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
