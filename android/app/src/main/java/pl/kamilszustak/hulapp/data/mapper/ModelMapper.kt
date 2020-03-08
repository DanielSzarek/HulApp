package pl.kamilszustak.hulapp.data.mapper

import pl.kamilszustak.hulapp.data.model.entity.DbEntity
import pl.kamilszustak.hulapp.data.model.json.JsonModel

interface ModelMapper<J : JsonModel, E : DbEntity> : Mapper<J, E>