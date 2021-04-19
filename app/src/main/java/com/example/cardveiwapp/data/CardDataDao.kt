package com.example.cardveiwapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.cardveiwapp.data.CardData

// use suspend functions

@Dao
interface CardDataDao {

    @Query( "SELECT * FROM CardData ORDER BY _color ASC" )
    fun getAll(): LiveData<List<CardData>>

    @Query( "SELECT * FROM CardData WHERE title = :titleCardData " )
    suspend fun getDataById( titleCardData : String) : CardData

    @Delete
    suspend fun deleteData( data: CardData)

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    suspend fun insertOrUpdate( data: CardData)

    @Insert
    fun insertData(data: List<CardData>)

    @Update
    suspend fun updateCard(cardData: CardData)

}