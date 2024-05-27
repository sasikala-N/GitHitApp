package com.sasikala.githitapp.presentation

import androidx.paging.PagingData
import com.sasikala.githitapp.data.local.GitHitRepoEntity
import com.sasikala.githitapp.data.remote.dto.RePoItem
import com.sasikala.githitapp.domine.models.GitHitRepo

/**
 * Created by sasikala on 5/26/24.
 */
data class ResourceState (
  val isLoading:Boolean=false,
  val gitHitRepo:List<GitHitRepoEntity> = emptyList(),
  val error:String=""

) {
}