package com.example.florys.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.florys.R
import com.example.florys.adapter.ProductCatalogListAdapter
import com.example.florys.data.responses.ProductcatalogItem
import com.example.florys.databinding.FragmentHomeBinding
import com.example.florys.helper.ViewModelFactory
import com.example.florys.ui.detailproductcatalog.ProductCatalogDetailActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var adapter: ProductCatalogListAdapter
    private lateinit var homeViewModel: HomeViewModel

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val layoutManager = LinearLayoutManager(root.context)

        setupViewModel()
        setupAction(root)

        return root
    }

    private fun setupViewModel() {
        homeViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(requireActivity().applicationContext)
        )[HomeViewModel::class.java]

        homeViewModel.getUser().observe(viewLifecycleOwner) { user ->
            if (user.token.isNotEmpty()) {
                homeViewModel.getProductCatalog(user.token)
            }
        }
        homeViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        homeViewModel.listProductCatalog.observe(viewLifecycleOwner) { productsCatalog ->
            setProductCatalogData(productsCatalog.productcatalog)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setProductCatalogData(productsCatalog: List<ProductcatalogItem>) {

    }

    private fun setupAction(view: View) {
        // Mengatur listener untuk flower_1
        view.findViewById<View>(R.id.flower_1).setOnClickListener {
            startActivity(Intent(requireContext(), /*flowerActovity*/ProductCatalogDetailActivity::class.java))
        }

        // Mengatur listener untuk flower_2
        view.findViewById<View>(R.id.flower_2).setOnClickListener {
            startActivity(Intent(requireContext(), /*flowerActovity*/ProductCatalogDetailActivity::class.java))
        }

        // Mengatur listener untuk flower_3
        view.findViewById<View>(R.id.flower_3).setOnClickListener {
            startActivity(Intent(requireContext(), /*flowerActovity*/ProductCatalogDetailActivity::class.java))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
