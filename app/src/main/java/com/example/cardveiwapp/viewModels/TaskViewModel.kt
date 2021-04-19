package com.example.cardveiwapp.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cardveiwapp.data.AppDatabase
import com.example.cardveiwapp.data.CardData
import com.example.cardveiwapp.repository.CardDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel (
		application: Application
) : AndroidViewModel( application ){

	val getAllData : LiveData<List<CardData>>
	private val repository : CardDataRepository

	init {
		val cardDataDao = AppDatabase.getDatabase(application).cardDataDao()
		repository =
				CardDataRepository(cardDataDao)
		getAllData = repository.getAllData
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

	fun getDataById( title : String ) : LiveData<CardData> {
		return repository.getDataById( title )
	}

}