package com.example.voting.di.repo_module

import android.app.Application
import com.example.data.remote.api.AdminApi
import com.example.data.remote.api.VoterApi
import com.example.data.repository.AdminRepoImp
import com.example.data.repository.TokenManagerRepoImp
import com.example.data.repository.VoterRepoImp
import com.example.domain.repository.AdminRepo
import com.example.domain.repository.TokenManagerRepo
import com.example.domain.repository.VoterRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {


    @Provides
    @Singleton
    fun provideTokenManagerRepository(context:Application): TokenManagerRepo =
        TokenManagerRepoImp(context)


    @Provides
    @Singleton
    fun provideAdminRepo(api: AdminApi): AdminRepo =
        AdminRepoImp(api)

    @Provides
    @Singleton
    fun provideVoterRepo(api: VoterApi): VoterRepo =
        VoterRepoImp(api)
}