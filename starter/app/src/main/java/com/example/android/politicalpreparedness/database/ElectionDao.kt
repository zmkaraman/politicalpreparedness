package com.example.android.politicalpreparedness.database

import androidx.room.*
import com.example.android.politicalpreparedness.network.models.Election

@Dao
interface ElectionDao {
/*
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(election: Election)

    //Add select all election query
    @Query("SELECT * FROM election_table")
    suspend fun queryAll()

    //Add select single election query
    @Query("SELECT * FROM election_table where id=:id")
    suspend fun queryById(id: String)

    //Add delete query
    @Query("DELETE FROM election_table where id=:id")
    suspend fun delete(id: String)

    //Add clear query
    @Query("DELETE FROM election_table")
    suspend fun clear()
*/
}