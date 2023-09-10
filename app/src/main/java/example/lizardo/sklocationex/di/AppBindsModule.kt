package example.lizardo.sklocationex.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import example.lizardo.sklocationex.data.repository.SocketServiceRepository
import example.lizardo.sklocationex.data.repository.SocketServiceRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
interface AppBindsModule {

    @Binds
    fun bindSocketSerViceRepository(
        socketServiceRepository: SocketServiceRepositoryImpl
    ): SocketServiceRepository

}