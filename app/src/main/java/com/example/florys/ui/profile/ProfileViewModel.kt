package com.example.florys.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.florys.data.repository.Repository
import com.example.florys.data.responses.LoginResult

class ProfileViewModel (private val repository: Repository) : ViewModel(){

    fun getUser(): LiveData<LoginResult> {
        return repository.getUser()
    }

}