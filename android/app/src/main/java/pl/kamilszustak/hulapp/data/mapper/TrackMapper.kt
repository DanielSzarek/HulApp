package pl.kamilszustak.hulapp.data.mapper

import pl.kamilszustak.hulapp.data.model.track.TrackEntity
import pl.kamilszustak.hulapp.data.model.track.TrackJson

class TrackMapper(
    private val userId: Long
) : ModelMapper<TrackJson, TrackEntity> {

    override fun map(model: TrackJson): TrackEntity {
        return TrackEntity(
            startDate = model.startDate,
            endDate = model.endDate,
            duration = model.duration,
            distance = model.distance,
            userId = userId
        ).apply {
            this.id = model.id
        }
    }
}
