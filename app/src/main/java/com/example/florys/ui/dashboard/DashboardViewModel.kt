package com.example.florys.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.florys.data.repository.Repository
import com.example.florys.data.responses.LoginResult

class DashboardViewModel(private val repository: Repository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    fun getUser(): LiveData<LoginResult> {
        return repository.getUser()
    }
}