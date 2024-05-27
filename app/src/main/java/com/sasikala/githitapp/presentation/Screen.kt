package com.sasikala.githitapp.presentation

/**
 * Created by sasikala on 5/26/24.
 */
sealed class Screen(val route: String) {
    object GitHitRepoScreen:Screen(route="git_hit_repo_list_screen")
    object GitHitRepoDetailScreen:Screen(route="git_hit_repo_detail_screen")


}