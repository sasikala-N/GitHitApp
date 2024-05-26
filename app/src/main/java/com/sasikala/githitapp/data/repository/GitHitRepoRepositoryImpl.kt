package com.sasikala.githitapp.data.repository

import com.sasikala.githitapp.data.remote.GitHitApi
import com.sasikala.githitapp.data.remote.dto.GitHitRepositoriesDto
import com.sasikala.githitapp.domine.repository.GitHitRepoRepository
import retrofit2.http.Query
import javax.inject.Inject
import kotlin.contracts.Returns

/**
 * Created by sasikala on 5/25/24.
 */
class GitHitRepoRepositoryImpl @Inject constructor(
    private val api:GitHitApi
) : GitHitRepoRepository {
    override suspend fun getGitHitRepositories(query: String,sort:String,order:String,page:Int,perPage:Int): GitHitRepositoriesDto {
        return api.getGitHitRepositories(query = query, sort = sort, order = order, page = page, perPage = perPage)
    }

}