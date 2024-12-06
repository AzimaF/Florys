package com.example.florys.ui.producthistory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.florys.adapter.ProductHistoryListAdapter
import com.example.florys.data.responses.DataItem
import com.example.florys.databinding.ActivityProductHistoryBinding
import com.example.florys.helper.ViewModelFactory

class ProductHistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductHistoryBinding
    private lateinit var productHistoryViewModel: ProductHistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProductHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)

        setupViewModel()
    }

    private fun setupViewModel() {
        productHistoryViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(this)
        )[ProductHistoryViewModel::class.java]




        productHistoryViewModel.isLoading.observe(this) {
            showLoading(it)
        }


    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }


}