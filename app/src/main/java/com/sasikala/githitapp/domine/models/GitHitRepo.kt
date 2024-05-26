package com.sasikala.githitapp.domine.models

import com.sasikala.githitapp.data.remote.dto.RePoItem

/**
 * Created by sasikala on 5/25/24.
 */
data class GitHitRepo(
    val id: Int,
    val name: String,
    val description: String,
    val language: String,
    val full_name: String
)

data class GitHitRepoItem(
    val items: List<RePoItem>
)
