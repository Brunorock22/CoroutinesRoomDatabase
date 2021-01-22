package com.devtides.coroutinesroom.model

import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User): Long

    @Query("SELECT * FROM USER WHERE user_name = :username")
    suspend fun getUser(username: String): User

    @Query("DELETE FROM USER WHERE id = :id")
    suspend fun deleteUser(id: Long)

}