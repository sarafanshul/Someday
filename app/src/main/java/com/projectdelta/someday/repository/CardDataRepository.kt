package com.projectdelta.someday.repository

import androidx.lifecycle.LiveData
import com.projectdelta.someday.data.CardDataDao
import com.projectdelta.someday.data.CardData

class CardDataRepository(
    private val cardDataDao: CardDataDao
) {
    val getAllData : LiveData<List<CardData>> = cardDataDao.getAll()

    suspend fun insertOrUpdate(card : CardData){
        cardDataDao.insertOrUpdate( card )
    }

    suspend fun updateCard( card: CardData){
        cardDataDao.updateCard( card )
    }

    fun getDataById( title : String ) : LiveData<CardData> {
        return cardDataDao.getDataById( title )
    }

}