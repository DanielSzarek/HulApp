package pl.kamilszustak.hulapp.domain.usecase

interface SuspendUseCase {
    suspend operator fun invoke()
}