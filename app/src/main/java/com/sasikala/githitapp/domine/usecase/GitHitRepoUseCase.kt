package com.sasikala.githitapp.domine.usecase

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadType
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.map
import androidx.room.withTransaction
import com.sasikala.githitapp.common.Resource
import com.sasikala.githitapp.data.local.GitHitRepoDatabase
import com.sasikala.githitapp.data.local.GitHitRepoEntity
import com.sasikala.githitapp.data.mappers.toGitHitRepo
import com.sasikala.githitapp.data.mappers.toGitHitRepoEntity
import com.sasikala.githitapp.data.remote.GitHitRepoMediator
import com.sasikala.githitapp.data.remote.dto.GitHitRepositoriesDto
import com.sasikala.githitapp.domine.models.GitHitRepo
import com.sasikala.githitapp.domine.repository.GitHitRepoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * Created by sasikala on 5/25/24.
 */

class GitHitRepoUseCase @Inject constructor(
    private val repository:GitHitRepoRepository,
    private val githitrepoDb: GitHitRepoDatabase,
){

    lateinit var gitHitRepoEntity:List<GitHitRepoEntity>
    lateinit var gitHitRepoList :PagingData<GitHitRepo>


//    val gitHitRepoPagingFlow=pager.flow

    operator fun invoke(query: String,sort:String,order:String): Flow<Resource<List<GitHitRepoEntity>>> =flow{
        try {
            Log.d("HHHHH", "start")
            emit(Resource.Loading())
            Log.d("HHHHH", "loading")
           val gitHitRepoItem = repository.getGitHitRepositories(query = query, sort = sort, order = order, page = GitHitRepoMediator.page, perPage = GitHitRepoMediator.per_page)
            Log.d("HHHHH", "apihit")
            githitrepoDb.withTransaction {
                if(GitHitRepoMediator.typeOfLoad==LoadType.REFRESH){
                    githitrepoDb.dao.clearAll()
                }
                gitHitRepoEntity= gitHitRepoItem.toGitHitRepoEntity()
                githitrepoDb.dao.upsertAll(gitHitRepoEntity)
                Log.d("HHHHH", "adding to db")
            }

//            gitHitRepoPagingFlow.collect{ pagingData->
//                gitHitRepoList= pagingData.map { it.toGitHitRepo() }
//
//            }
            emit(Resource.Success(gitHitRepoEntity))
            Log.d("HHHHH", "success")
        }catch (e:HttpException){
            Log.d("HHHHH", "exception")
          emit(Resource.Error(e.localizedMessage?:"An unexpected error occured"))
        }catch (e:IOException){
            Log.d("HHHHH", "exxception")
          emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }


}