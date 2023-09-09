package example.lizardo.sklocationex.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import example.lizardo.sklocationex.manager.LocationManager
import java.net.Socket
import javax.inject.Singleton

/*
@Module
@InstallIn(SingletonComponent::class)
class AppProvideModule {

    @Provides
    fun provideLocationManager(@ApplicationContext appContext: Context): LocationManager {
        return LocationManager(appContext)
    }
}*/
