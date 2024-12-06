package com.example.florys.helper

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.florys.data.repository.Repository
import com.example.florys.dependencyInjection.Injection
import com.example.florys.ui.addproductitem.AddProductItemViewModel
import com.example.florys.ui.chat.ChatViewModel
import com.example.florys.ui.dashboard.DashboardViewModel
import com.example.florys.ui.home.HomeViewModel
import com.example.florys.ui.login.LoginViewModel
import com.example.florys.ui.main.MainViewModel
import com.example.florys.ui.productactivedetail.ProductActiveDetailViewModel
import com.example.florys.ui.producthistory.ProductHistoryViewModel
import com.example.florys.ui.profile.ProfileViewModel
import com.example.florys.ui.productmanagement.ProductManagementViewModel
import com.example.florys.ui.qr.QRViewModel
import com.example.florys.ui.register.RegisterViewModel

class ViewModelFactory(private val repository: Repository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(repository) as T
            }
            modelClass.isAssignableFrom(QRViewModel::class.java) -> {
                QRViewModel(repository) as T
            }
            modelClass.isAssignableFrom(DashboardViewModel::class.java) -> {
                DashboardViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ChatViewModel::class.java) -> {
                ChatViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ProductHistoryViewModel::class.java) -> {
                ProductHistoryViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ProductManagementViewModel::class.java) -> {
                ProductManagementViewModel(repository) as T
            }
            modelClass.isAssignableFrom(AddProductItemViewModel::class.java) -> {
                AddProductItemViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ProductActiveDetailViewModel::class.java) -> {
                ProductActiveDetailViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = com.example.florys.helper.ViewModelFactory(
                        Injection.provideRepository(context)
                    )
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}