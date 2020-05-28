package pl.kamilszustak.hulapp.domain.mapper.point

import pl.kamilszustak.hulapp.domain.mapper.Mapper
import pl.kamilszustak.hulapp.domain.model.point.MapPointEntity
import pl.kamilszustak.hulapp.domain.model.point.MapPointJson
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MapPointJsonMapper @Inject constructor() : Mapper<MapPointJson, MapPointEntity>() {
    override fun map(model: MapPointJson): MapPointEntity =
        MapPointEntity(
            authorId = model.author.id,
            name = model.name,
            description = model.description,
            rating = model.rating,
            latitude = model.latitude,
            longitude = model.longitude
        ).apply {
            this.id = model.id
        }
}