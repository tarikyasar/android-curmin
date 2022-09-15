package com.tarikyasar.curmin.di

import com.tarikyasar.curmin.data.repository.settings.datasource.SettingsCacheDataSource
import com.tarikyasar.curmin.data.repository.settings.datasource.SettingsDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Singleton
    @Binds
    fun bindSettingsCacheDataSource(dataSource: SettingsCacheDataSource): SettingsDataSource.Cache
}