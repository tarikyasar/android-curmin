package com.tarikyasar.curmin.di

import com.tarikyasar.curmin.data.repository.settings.SettingsRepositoryImpl
import com.tarikyasar.curmin.data.repository.settings.datasource.SettingsCacheDataSource
import com.tarikyasar.curmin.data.repository.settings.datasource.SettingsDataSource
import com.tarikyasar.curmin.domain.repository.SettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Singleton
    @Binds
    fun bindSettingsRepository(repository: SettingsRepositoryImpl): SettingsRepository
}