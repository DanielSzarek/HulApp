package pl.kamilszustak.hulapp.domain.mapper

import pl.kamilszustak.hulapp.domain.model.entity.IdentifiedDatabaseEntity
import pl.kamilszustak.hulapp.domain.model.json.IdentifiedJsonModel

abstract class JsonModelMapper<J : IdentifiedJsonModel, E : IdentifiedDatabaseEntity> : Mapper<J, E>()