package com.example.florys.ui.productmanagement

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.florys.adapter.ProductManagementListAdapter
import com.example.florys.data.responses.DataItem
import com.example.florys.databinding.ActivityProductManagementBinding
import com.example.florys.helper.ViewModelFactory
import com.example.florys.ui.addproductitem.AddProductItemActivity

class ProductManagementActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductManagementBinding
    private lateinit var productManagementViewModel: ProductManagementViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductManagementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvProductManagement.layoutManager = layoutManager.apply { setReverseLayout(true) }

        setupViewModel()
        setupAction()
    }

    private fun setupViewModel() {
        productManagementViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(this)
        )[ProductManagementViewModel::class.java]

        productManagementViewModel.getUser().observe(this) {user ->
            if (user.token.isNotEmpty()) {
                if (user.type.equals("Producer")) {
                    binding.greeting.text = "Producer : " + user.name
                    productManagementViewModel.getProductActiveProducer(user.id)
                } else if (user.type.equals("Distributor")) {
                    binding.greeting.text = "Distributor : " + user.name
                    productManagementViewModel.getProductActiveDistributor(user.id)
                } else if (user.type.equals("Seller")) {
                    binding.greeting.text = "Seller : " + user.name
                    productManagementViewModel.getProductActiveSeller(user.id)
                } else {
                    binding.greeting.text = "Hello " + user.name
                }
            }
        }

        productManagementViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        productManagementViewModel.listProductitem.observe(this) { productItems ->
            setProductActiveData(productItems.data)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setProductActiveData(productItems: List<DataItem>) {
        binding.rvProductManagement.adapter = ProductManagementListAdapter(productItems)
    }

    private fun setupAction() {
        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this@ProductManagementActivity, AddProductItemActivity::class.java))
        }
    }
}