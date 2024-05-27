package com.sasikala.githitapp.presentation.gitrepo_list

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.flatMap
import androidx.paging.map
import com.sasikala.githitapp.common.Constants
import com.sasikala.githitapp.common.Resource
import com.sasikala.githitapp.data.local.GitHitRepoEntity
import com.sasikala.githitapp.data.mappers.toGitHitRepo
import com.sasikala.githitapp.domine.models.GitHitRepo
import com.sasikala.githitapp.domine.usecase.GitHitRepoUseCase
import com.sasikala.githitapp.presentation.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * Created by sasikala on 5/26/24.
 */
@HiltViewModel
class GitHitRepoViewModel @Inject constructor(
    private val gitHitUseCase: GitHitRepoUseCase,
    private val savedStateHandle: SavedStateHandle,
    pager: Pager<Int,GitHitRepoEntity>
): ViewModel() {


    private val _gitHitRepoState = mutableStateOf(ResourceState())
    val gitHitRepoState: State<ResourceState> = _gitHitRepoState

    private fun getGitHitRepo(query:String,sort:String,order:String){

        gitHitUseCase(query, sort, order).onEach { result->
//            val gitHitRepoList: MutableList<GitHitRepo> = mutableListOf()
//
//            result.data?.map {
//                gitHitRepoList.addAll(listOf(it))
//            }
            when(result){
                is Resource.Success->{
                      _gitHitRepoState.value = ResourceState(gitHitRepo = result.data ?: emptyList())
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

    val gitHitRepoPagingFlow=pager
        .flow
        .map { pagingData-> pagingData.map { it.toGitHitRepo() } }
        .cachedIn(viewModelScope)

    fun updateQuery(query: String) {
        savedStateHandle.set(Constants.QUERY, query)
        fetchGitRepos()
    }

    fun fetchGitRepos() {
        Log.e( "fetchGitRepos: ", "Sasikaala")
        val query = savedStateHandle.get<String>(Constants.QUERY) ?: ""
        val sort = savedStateHandle.get<String>(Constants.SORT) ?: "stars"
        val order = savedStateHandle.get<String>(Constants.ORDER) ?: "desc"
        getGitHitRepo(query, sort, order)
    }

    fun updateSortOrder(sort: String, order: String) {
        savedStateHandle.set(Constants.SORT, sort)
        savedStateHandle.set(Constants.ORDER, order)
        fetchGitRepos()
    }

}