package com.sasikala.githitapp.data.remote

import com.sasikala.githitapp.data.remote.dto.GitHitRepositoriesDto
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by sasikala on 5/25/24.
 */
interface GitHitApi {
    @GET("/search/repositories")
    suspend fun getGitHitRepositories(
        @Query("q") query: String,
        @Query("sort") sort: String,
        @Query("order") order: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): GitHitRepositoriesDto

}