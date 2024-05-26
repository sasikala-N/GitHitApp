package com.sasikala.githitapp.domine.repository

import com.sasikala.githitapp.data.remote.dto.GitHitRepositoriesDto

/**
 * Created by sasikala on 5/25/24.
 */
interface GitHitRepoRepository {
    suspend fun getGitHitRepositories(query: String,sort:String,order:String,page:Int,perPage:Int):GitHitRepositoriesDto

}