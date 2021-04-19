package com.example.cardveiwapp.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.notifyObserver() {
	this.value = this.value
}

//fun <T> LiveData<T>.notifyObserver() {
//	this.value = this.value
//}