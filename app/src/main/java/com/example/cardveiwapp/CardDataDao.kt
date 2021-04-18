package com.example.cardveiwapp

import androidx.lifecycle.LiveData
import androidx.room.*

// use suspend functions

@Dao
interface CardDataDao {

    @Query( "SELECT * FROM CardData ORDER BY _color ASC" )
    fun getAll(): LiveData<List<CardData>>

    @Query( "SELECT * FROM CardData WHERE title = :titleCardData " )
    suspend fun getDataById( titleCardData : String) : CardData

    @Delete
    suspend fun deleteData( data: CardData )

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    suspend fun insertOrUpdate( data: CardData )

    @Insert
    fun insertData(data: List<CardData>)

    @Update
    suspend fun updateCard(cardData: CardData)

    // https://www.youtube.com/watch?v=CcaCpRCACzU
//    private suspend fun getAll(): Result<Exception ,List<CardData> > = Result
}