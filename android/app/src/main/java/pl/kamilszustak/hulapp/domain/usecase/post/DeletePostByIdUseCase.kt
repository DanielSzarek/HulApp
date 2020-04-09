package pl.kamilszustak.hulapp.domain.usecase.post

interface DeletePostByIdUseCase {
    suspend operator fun invoke(id: Long): Result<Unit>
}