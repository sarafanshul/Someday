package com.projectdelta.someday.Repository

import androidx.lifecycle.LiveData
import com.projectdelta.someday.Constant.DAY_VALUE
import com.projectdelta.someday.Data.CardDataDao
import com.projectdelta.someday.Data.CardData

class CardDataRepository(
    private val cardDataDao: CardDataDao
) {
    val getAllData : LiveData<List<CardData>> = cardDataDao.getAll()

    val getAllByOrder : LiveData<List<CardData>> = cardDataDao.getAllByOrder( DAY_VALUE )

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