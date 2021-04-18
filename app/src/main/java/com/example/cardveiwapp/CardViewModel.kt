package com.example.cardveiwapp

// https://www.youtube.com/watch?v=lwAvI3WDXBY

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CardViewModel(
    application: Application
) : AndroidViewModel( application ) {

    private val getAllData : LiveData<List<CardData>>
    private val repository : CardDataRepository

    init {
        val cardDataDao = AppDatabase.getDatabase(application).cardDataDao()
        repository = CardDataRepository(cardDataDao)
        getAllData = repository.getAllData
    }

    fun insertOrUpdate(cardData: CardData){
        viewModelScope.launch ( Dispatchers.IO ){
            repository.insertOrUpdate( cardData )
        }
    }

}