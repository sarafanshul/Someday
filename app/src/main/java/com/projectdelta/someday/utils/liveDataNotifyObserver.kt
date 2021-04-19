package com.projectdelta.someday.utils

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.notifyObserver() {
	this.value = this.value
}

//fun <T> LiveData<T>.notifyObserver() {
//	this.value = this.value
//}