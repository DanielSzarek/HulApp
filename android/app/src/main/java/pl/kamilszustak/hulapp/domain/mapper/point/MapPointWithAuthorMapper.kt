package pl.kamilszustak.hulapp.domain.mapper.point

import com.google.android.gms.maps.model.LatLng
import pl.kamilszustak.hulapp.data.repository.UserDetailsRepository
import pl.kamilszustak.hulapp.domain.mapper.Mapper
import pl.kamilszustak.hulapp.domain.model.point.MapPoint
import pl.kamilszustak.hulapp.domain.model.point.MapPointWithAuthor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MapPointWithAuthorMapper @Inject constructor(
    private val userDetailsRepository: UserDetailsRepository
) : Mapper<MapPointWithAuthor, MapPoint>() {

    private val myId: Long = userDetailsRepository.getValue(UserDetailsRepository.UserDetailsKey.USER_ID)

    override fun map(model: MapPointWithAuthor): MapPoint {
        val isMine = (model.author.id == myId)
        val location = LatLng(model.mapPoint.latitude, model.mapPoint.longitude)

        return MapPoint(
            id = model.mapPoint.id,
            author = model.author,
            name = model.mapPoint.name,
            description = model.mapPoint.description,
            rating = model.mapPoint.rating,
            isMine = isMine,
            location = location
        )
    }
}