package com.sasikala.githitapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Created by sasikala on 5/27/24.
 */
@Database(
    entities = [GitHitRepoEntity::class],
    version = 1
)
abstract class GitHitRepoDatabase: RoomDatabase() {
    abstract val dao: GitGitRepoDao

}