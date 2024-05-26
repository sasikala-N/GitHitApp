package com.sasikala.githitapp.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sasikala.githitapp.common.Constants
import com.sasikala.githitapp.common.Resource
import com.sasikala.githitapp.domine.usecase.GitHitRepoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onEmpty
import javax.inject.Inject

/**
 * Created by sasikala on 5/26/24.
 */
@HiltViewModel
class GitHitRepoViewModel @Inject constructor(
    private val gitHitUseCase: GitHitRepoUseCase,
    private val savedSateHandler: SavedStateHandle
): ViewModel() {

    private val _gitHitRepoState = mutableStateOf(ResourceState())
    val gitHitRepoState: State<ResourceState> = _gitHitRepoState

//    init {
//        savedSateHandler.get(Constants.QUERY,)
//        getGitHitRepo()
//    }
    private fun getGitHitRepo(query:String,sort:String,order:String,page:Int,perPage:Int){
        gitHitUseCase(query, sort, order, page, perPage).onEach { result->
            when(result){
                is Resource.Success->{
                      _gitHitRepoState.value = ResourceState(gitHitRepo = result.data?.items?: emptyList())
                }
                is Resource.Error->{
                    _gitHitRepoState.value= ResourceState(error = result.message?: "An unexpected Error occurred")
                }
                is Resource.Loading->{
                    _gitHitRepoState.value= ResourceState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)

    }
}