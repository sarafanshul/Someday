package com.projectdelta.someday.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.projectdelta.someday.Data.AppDatabase
import com.projectdelta.someday.Data.CardData
import com.projectdelta.someday.Repository.CardDataRepository
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