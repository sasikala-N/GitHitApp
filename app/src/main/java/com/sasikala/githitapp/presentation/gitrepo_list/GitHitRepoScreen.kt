package com.sasikala.githitapp.presentation.gitrepo_list

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.sasikala.githitapp.presentation.Screen
import androidx.paging.compose.items
import com.sasikala.githitapp.domine.models.GitHitRepo
import com.sasikala.githitapp.presentation.gitrepo_list.components.GitHitRepoItem

/**
 * Created by sasikala on 5/26/24.
 */
@Composable
fun GitHitRepoScreen(
    navcontroller: NavController,
    beers: LazyPagingItems<GitHitRepo>,
    viewModel: GitHitRepoViewModel
){

    val context = LocalContext.current
    LaunchedEffect(key1 = beers.loadState) {
        if(beers.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (beers.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

     val state=viewModel.gitHitRepoState.value
   // Log.e("GitHitRepoScreen: ", (+state.gitHitRepo.size).toString())
    var inputValue by remember { mutableStateOf("") }
    //val beers = viewModel.gitHitRepoPagingFlow.collectAsLazyPagingItems()
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp, vertical = 30.dp)
        ){
        if(beers.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }else{
            Column(modifier = Modifier.fillMaxSize()) {
                TextField(
                    value = inputValue,
                    onValueChange = { newvalue ->
                        inputValue = newvalue
                          viewModel.updateQuery(newvalue)
                    },
                    label = { Text("Search") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .background(Color.White),

                    )
                LazyColumn(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)) {

                    items(beers) { gitHitRepo ->
                        if (gitHitRepo != null) {
                            GitHitRepoItem(
                                gitHitRepo = gitHitRepo,
                                oncItemClick = {
                                    navcontroller.navigate(Screen.GitHitRepoDetailScreen.route)
                                }
                            )
                        }

                    }
                    item {
                        if(beers.loadState.append is LoadState.Loading) {
                            CircularProgressIndicator()
                        }
                    }
                }

            }
        }

//        if(state.error.isNotBlank()){
//            Text(
//                text = state.error,
//                color = MaterialTheme.colors.error,
//                textAlign=TextAlign.Center,
//                modifier= Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 20.dp)
//                    .align(Alignment.Center)
//            )
//        }
//
//        if(state.isLoading){
//            //CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
//        }
    }

}
