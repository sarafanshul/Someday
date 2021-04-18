package com.example.cardveiwapp

import androidx.lifecycle.LiveData

class CardDataRepository(
    private val cardDataDao: CardDataDao
) {
    val getAllData : LiveData<List<CardData>> = cardDataDao.getAll()

    suspend fun insertOrUpdate(card : CardData){
        cardDataDao.insertOrUpdate( card )
    }

    suspend fun updateCard( card: CardData ){
        cardDataDao.updateCard( card )
    }
}