package pl.kamilszustak.hulapp.domain.usecase.point

interface DeleteMapPointByIdUseCase {
    suspend operator fun invoke(id: Long): Result<Unit>
}