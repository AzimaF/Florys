package com.example.florys.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.florys.data.api.ApiService
import com.example.florys.data.pref.UserPreference
import com.example.florys.data.responses.LoginResult

class Repository(private val apiService: ApiService, private val userPreference: UserPreference) {

    suspend fun login(loginResult: LoginResult) {
        userPreference.login(loginResult)
    }

    fun getUser(): LiveData<LoginResult> {
        return userPreference.getUser().asLiveData()
    }

    suspend fun logout() {
        userPreference.logout()
    }

//    fun getStoryData(): LiveData<PagingData<ListStoryItem>> {
//        return Pager(
//            config = PagingConfig(
//                pageSize = 5
//            ),
//            pagingSourceFactory = {
//                StoryPagingSource(apiService, userPreference.getToken())
//            }
//        ).liveData
//    }
}