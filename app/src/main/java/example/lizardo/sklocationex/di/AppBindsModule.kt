package example.lizardo.sklocationex.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import example.lizardo.sklocationex.data.repository.LocationRepository
import example.lizardo.sklocationex.data.repository.LocationRepositoryImpl
import example.lizardo.sklocationex.data.repository.SocketServiceRepository
import example.lizardo.sklocationex.data.repository.SocketServiceRepositoryImpl
import example.lizardo.sklocationex.domain.GetLocationUseCase
import example.lizardo.sklocationex.domain.GetLocationUseCaseImpl
import example.lizardo.sklocationex.domain.SendDataToSocketUseCase
import example.lizardo.sklocationex.domain.SendDataToSocketUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
interface AppBindsModule {

    @Binds
    fun bindsLocationRepository(
        locationRepository: LocationRepositoryImpl
    ): LocationRepository

    @Binds
    fun bindSocketSerViceRepository(
        socketServiceRepository: SocketServiceRepositoryImpl
    ): SocketServiceRepository

    @Binds
    fun bindsGetLocationUseCase(
        getLocationUseCase: GetLocationUseCaseImpl
    ): GetLocationUseCase

    @Binds
    fun bindSendDataToSocketUseCase(
        sendDataToSocketUseCase: SendDataToSocketUseCaseImpl
    ): SendDataToSocketUseCase
}