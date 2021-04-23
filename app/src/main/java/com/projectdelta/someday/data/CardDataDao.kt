package com.projectdelta.someday.data

import androidx.lifecycle.LiveData
import androidx.room.*

// use suspend functions
@Dao
interface CardDataDao {

    @Query( "SELECT * FROM CardData ORDER BY _color ASC" )
    fun getAll(): LiveData<List<CardData>>


    @Query( " SELECT * FROM ( SELECT * FROM CardData WHERE _color >= :dayValue ORDER BY _color ASC ) UNION ALL SELECT * FROM ( SELECT * FROM Carddata WHERE _color < :dayValue ORDER BY _color ASC)" )
    fun getAllByOrder(dayValue : Int): LiveData< List< CardData > >

    @Query( "SELECT * FROM CardData WHERE title = :titleCardData " )
    fun getDataById( titleCardData : String) : LiveData<CardData>

    @Query("SELECT * FROM CardData WHERE _color == :dayValue")
    suspend fun getToday(dayValue : Int) : CardData

    @Delete
    suspend fun deleteData( data: CardData)

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    suspend fun insertOrUpdate( data: CardData)

    @Insert
    fun insertData(data: List<CardData>)

    @Update
    suspend fun updateCard(cardData: CardData)

}