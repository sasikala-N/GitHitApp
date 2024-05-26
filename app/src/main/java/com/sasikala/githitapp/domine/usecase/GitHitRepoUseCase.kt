package com.sasikala.githitapp.domine.usecase

import com.sasikala.githitapp.common.Resource
import com.sasikala.githitapp.data.remote.dto.GitHitRepositoriesDto
import com.sasikala.githitapp.domine.repository.GitHitRepoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * Created by sasikala on 5/25/24.
 */
class GitHitRepoUseCase @Inject constructor(
    private val repository:GitHitRepoRepository
) {
        operator fun invoke(query: String,sort:String,order:String,page:Int,perPage:Int): Flow<Resource<GitHitRepositoriesDto>> =flow{
        try {
            emit(Resource.Loading())
            val gitHitRepoItem = repository.getGitHitRepositories(query = query, sort = sort, order = order, page = page, perPage = perPage)
            emit(Resource.Success(gitHitRepoItem))
        }catch (e:HttpException){
          emit(Resource.Error(e.localizedMessage?:"An unexpected error occured"))
        }catch (e:IOException){
          emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }
}