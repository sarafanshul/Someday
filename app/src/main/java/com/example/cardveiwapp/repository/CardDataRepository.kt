package com.example.cardveiwapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cardveiwapp.data.CardDataDao
import com.example.cardveiwapp.data.CardData

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