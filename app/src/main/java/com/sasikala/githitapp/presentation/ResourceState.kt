package com.sasikala.githitapp.presentation

import com.sasikala.githitapp.data.remote.dto.RePoItem

/**
 * Created by sasikala on 5/26/24.
 */
data class ResourceState (
  val isLoading:Boolean=false,
    val gitHitRepo:List<RePoItem> = emptyList(),
    val error:String=""

)