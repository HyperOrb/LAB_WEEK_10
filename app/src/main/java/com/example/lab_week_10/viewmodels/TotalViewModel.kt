package com.example.lab_week_10.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TotalViewModel : ViewModel() {
    // _total bersifat private dan mutable (bisa diubah)
    private val _total = MutableLiveData<Int>()
    
    // total bersifat public dan immutable (hanya bisa dibaca/diobserve)
    val total: LiveData<Int> = _total

    init {
        _total.postValue(0)
    }

    fun incrementTotal() {
        _total.postValue(_total.value?.plus(1))
    }
}