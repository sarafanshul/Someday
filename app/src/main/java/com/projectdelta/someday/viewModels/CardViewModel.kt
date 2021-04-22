package com.projectdelta.someday.viewModels

// https://www.youtube.com/watch?v=lwAvI3WDXBY

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.projectdelta.someday.repository.CardDataRepository
import com.projectdelta.someday.data.AppDatabase
import com.projectdelta.someday.data.CardData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CardViewModel(
    application: Application
) : AndroidViewModel( application ) {

    val getAllData : LiveData<List<CardData>>
    val getAllByOrder : LiveData<List<CardData>>
    private val repository : CardDataRepository
    lateinit var today : LiveData<CardData>

    init {
        val cardDataDao = AppDatabase.getDatabase(application).cardDataDao()
        repository =
            CardDataRepository(cardDataDao)
        getAllData = repository.getAllData
        getAllByOrder = repository.getAllByOrder
        today = repository.getToday
    }

    fun insertOrUpdate(cardData: CardData){
        viewModelScope.launch ( Dispatchers.IO ){
            repository.insertOrUpdate( cardData )
        }
    }

    fun updateCard( cardData: CardData){
        viewModelScope.launch ( Dispatchers.IO ){
            repository.updateCard( cardData )
        }
    }

}