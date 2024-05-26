package com.sasikala.githitapp.di

import com.sasikala.githitapp.common.Constants
import com.sasikala.githitapp.data.remote.GitHitApi
import com.sasikala.githitapp.data.repository.GitHitRepoRepositoryImpl
import com.sasikala.githitapp.domine.repository.GitHitRepoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by sasikala on 5/26/24.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGitHitApi(): GitHitApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GitHitApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGitHitRepository(api: GitHitApi): GitHitRepoRepository {
        return GitHitRepoRepositoryImpl(api)
    }



}