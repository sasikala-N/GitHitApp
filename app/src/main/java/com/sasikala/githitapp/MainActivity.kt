package com.sasikala.githitapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.sasikala.githitapp.presentation.Screen
import com.sasikala.githitapp.presentation.gitrepo_list.GitHitRepoScreen
import com.sasikala.githitapp.presentation.gitrepo_list.GitHitRepoViewModel
import com.sasikala.githitapp.ui.theme.GitHitAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GitHitAppTheme {
               Surface(color = MaterialTheme.colors.background) {
                   val navController= rememberNavController()
                   val viewModel = hiltViewModel<GitHitRepoViewModel>()
                   val beers = viewModel.gitHitRepoPagingFlow.collectAsLazyPagingItems()
                   NavHost(
                       navController = navController,
                       startDestination = Screen.GitHitRepoScreen.route
                   ){
                       composable(
                           route=Screen.GitHitRepoScreen.route
                       ){
                           GitHitRepoScreen(navController,beers,viewModel)
                       }
                       composable(
                           route=Screen.GitHitRepoDetailScreen.route
                       ){

                       }

                   }

               }
            }
        }
    }
}

