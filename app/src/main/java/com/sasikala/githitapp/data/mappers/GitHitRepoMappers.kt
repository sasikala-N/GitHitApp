package com.sasikala.githitapp.data.mappers

import com.sasikala.githitapp.data.local.GitHitRepoEntity
import com.sasikala.githitapp.data.remote.dto.GitHitRepositoriesDto
import com.sasikala.githitapp.domine.models.GitHitRepo

/**
 * Created by sasikala on 5/27/24.
 */

fun GitHitRepositoriesDto.toGitHitRepoEntity(): List<GitHitRepoEntity>{

    return items.map { item ->
        GitHitRepoEntity(
            id = item.id,
            name = item.name ?: "",
            description = item.description ?: "",
            language = item.language ?: "",
            full_name = item.full_name ?: ""
        )
    }
}

fun GitHitRepoEntity.toGitHitRepo(): GitHitRepo{
    return GitHitRepo(
        id=id,
        name=name,
        description=description,
        language=language,
        full_name=full_name)
}