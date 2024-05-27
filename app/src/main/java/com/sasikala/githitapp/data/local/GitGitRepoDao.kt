package com.sasikala.githitapp.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

/**
 * Created by sasikala on 5/27/24.
 */
@Dao
interface GitGitRepoDao {
    @Upsert
    suspend fun upsertAll(githitrepo:List<GitHitRepoEntity>)

    @Query("SELECT * FROM githitrepo_entity")
    fun pagingSource():PagingSource<Int,GitHitRepoEntity>

    @Query("DELETE FROM githitrepo_entity")
    suspend fun clearAll()


}