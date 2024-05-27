package com.sasikala.githitapp.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.sasikala.githitapp.data.local.GitHitRepoDatabase
import com.sasikala.githitapp.data.local.GitHitRepoEntity
import com.sasikala.githitapp.data.mappers.toGitHitRepoEntity
import com.sasikala.githitapp.domine.repository.GitHitRepoRepository
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException
import android.util.Log
/**
 * Created by sasikala on 5/27/24.
 */
@OptIn(ExperimentalPagingApi::class)
class GitHitRepoMediator(
    private val repository: GitHitRepoRepository,
    private val githitrepoDb: GitHitRepoDatabase,
    private var query:String,
    private var sort:String,
    private var order:String

):RemoteMediator<Int,GitHitRepoEntity>() {
//    override suspend fun load(
//        loadType: LoadType,
//        state: PagingState<Int, GitHitRepoEntity>
//    ): MediatorResult {
//        return try {
//            // Determine the load key (page number) based on the load type
//            val loadKey = when (loadType) {
//                LoadType.REFRESH -> {
//                    Log.d("Paging", "LoadType.REFRESH")
//                    1
//                }
//                LoadType.PREPEND -> {
//                    Log.d("Paging", "LoadType.PREPEND")
//                    return MediatorResult.Success(endOfPaginationReached = true)
//                }
//                LoadType.APPEND -> {
//                    Log.d("Paging", "LoadType.APPEND")
//                    val lastItem = state.lastItemOrNull()
//                    if (lastItem == null) {
//                        Log.d("Paging", "Last item is null, starting from page 1")
//                        1 // If no items are loaded, start from the first page
//                    } else {
//                        val nextPage = (lastItem.id / state.config.pageSize) + 1
//                        Log.d("Paging", "Last item ID: ${lastItem.id}, loading page: $nextPage")
//                        nextPage
//                    }
//                }
//            }
//
//            // Simulate network delay
//            delay(2000L)
//
//            // Fetch data from the repository
//            Log.d("Paging", "Fetching data for page: $loadKey, pageSize: ${state.config.pageSize}")
//            val gitHitRepo = repository.getGitHitRepositories(
//                query = "google",
//                page = loadKey,
//                perPage = state.config.pageSize,
//                order = "desc",
//                sort = "stars"
//            )
//            Log.d("Paging", "Data fetched: ${gitHitRepo.items.size} items")
//
//            // Run database operations within a transaction
//            githitrepoDb.withTransaction {
//                if (loadType == LoadType.REFRESH) {
//                    Log.d("Paging", "Clearing all items in the database")
//                    githitrepoDb.dao.clearAll()
//                }
//
//                val gitHitEntities = gitHitRepo.toGitHitRepoEntity()
//                Log.d("Paging", "Inserting ${gitHitEntities.size} items into the database")
//                githitrepoDb.dao.upsertAll(gitHitEntities)
//            }
//
//            // Determine if this is the end of the pagination
//            val endOfPaginationReached = gitHitRepo.items.isEmpty()
//            Log.d("Paging", "End of pagination reached: $endOfPaginationReached")
//
//            MediatorResult.Success(
//                endOfPaginationReached = endOfPaginationReached
//            )
//        } catch (e: IOException) {
//            Log.e("Paging", "IOException: ${e.message}", e)
//            MediatorResult.Error(e)
//        } catch (e: HttpException) {
//            Log.e("Paging", "HttpException: ${e.message}", e)
//            MediatorResult.Error(e)
//        } catch (e: Exception) {
//            Log.e("Paging", "Exception: ${e.message}", e)
//            MediatorResult.Error(e)
//        }
//    }


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, GitHitRepoEntity>
    ): MediatorResult {
        return try {
           val loadkey=when(loadType){
               LoadType.REFRESH->1
                   LoadType.PREPEND->return MediatorResult.Success(endOfPaginationReached = true)
                       LoadType.APPEND->{
                           val lastItem=state.lastItemOrNull()
                           if(lastItem==null){
                               1
                           }else{
                               (lastItem.id/state.config.pageSize)+1
                           }
                       }
           }

            delay(2000L)
            val gitHitRepo=repository.getGitHitRepositories(
                query=query,
                page = loadkey,
                perPage = state.config.pageSize,
                order=order,
                sort=sort
            )
            githitrepoDb.withTransaction {
                if(loadType== LoadType.REFRESH){
                    githitrepoDb.dao.clearAll()
                }
                val gitHitEntites= gitHitRepo.toGitHitRepoEntity()
                githitrepoDb.dao.upsertAll(gitHitEntites)
            }
            MediatorResult.Success(
                endOfPaginationReached =gitHitRepo.items.isEmpty()
            )
        }catch (e:IOException){
            MediatorResult.Error(e)
        }catch (e:HttpException){
            MediatorResult.Error(e)
        }
    }
}