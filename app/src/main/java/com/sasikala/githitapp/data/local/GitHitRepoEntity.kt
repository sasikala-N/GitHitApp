package com.sasikala.githitapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by sasikala on 5/26/24.
 */
@Entity(tableName = "githitrepo_entity")
data class GitHitRepoEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val description: String,
    val language: String,
    val full_name: String

)
