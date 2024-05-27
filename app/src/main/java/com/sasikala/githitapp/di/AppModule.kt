package com.sasikala.githitapp.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.sasikala.githitapp.common.Constants
import com.sasikala.githitapp.data.local.GitHitRepoDatabase
import com.sasikala.githitapp.data.local.GitHitRepoEntity
import com.sasikala.githitapp.data.remote.GitHitApi
import com.sasikala.githitapp.data.remote.GitHitRepoMediator
import com.sasikala.githitapp.data.repository.GitHitRepoRepositoryImpl
import com.sasikala.githitapp.domine.repository.GitHitRepoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by sasikala on 5/26/24.
 */
@OptIn(ExperimentalPagingApi::class)
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

    @Provides
    @Singleton
    fun provideBeerDatabase(@ApplicationContext context: Context):GitHitRepoDatabase{
        return Room.databaseBuilder(
            context,
            GitHitRepoDatabase::class.java,
            "githit.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideGitHitRepoPager(db: GitHitRepoDatabase,repo: GitHitRepoRepository): Pager<Int,GitHitRepoEntity>{
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = GitHitRepoMediator(repo,db),
            pagingSourceFactory = {
                db.dao.pagingSource()
            }
        )
    }




}