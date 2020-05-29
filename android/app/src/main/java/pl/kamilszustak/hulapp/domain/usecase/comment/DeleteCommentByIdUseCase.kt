package pl.kamilszustak.hulapp.domain.usecase.comment

interface DeleteCommentByIdUseCase {
    suspend operator fun invoke(id: Long): Result<Unit>
}