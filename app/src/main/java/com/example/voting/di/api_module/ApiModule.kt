package com.example.voting.di.api_module

import com.example.data.remote.api.AdminApi
import com.example.data.remote.api.VoterApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {



    @Provides
    @Singleton
    fun provideAdminApi(retrofit: Retrofit): AdminApi =
        retrofit.create(AdminApi::class.java)

    @Provides
    @Singleton
    fun provideVoterApi(retrofit: Retrofit): VoterApi =
        retrofit.create(VoterApi::class.java)

}