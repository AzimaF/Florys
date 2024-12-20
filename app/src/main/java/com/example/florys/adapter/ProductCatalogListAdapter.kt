package com.example.florys.adapter

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.florys.data.responses.ProductcatalogItem
import com.example.florys.databinding.ProductCatalogItemBinding
import java.text.NumberFormat
import java.util.Locale

class ProductCatalogListAdapter(private val listStory: List<ProductcatalogItem>) : RecyclerView.Adapter<ProductCatalogListAdapter.ListViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductCatalogListAdapter.ListViewHolder {
        return ListViewHolder(ProductCatalogItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: ProductCatalogListAdapter.ListViewHolder, position: Int) {
        holder.bind(listStory[position])
    }

    override fun getItemCount(): Int = listStory.size

    class ListViewHolder(binding: ProductCatalogItemBinding) : RecyclerView.ViewHolder(binding.root) {

        private var imgPhoto: ImageView = binding.productCatalogImg
        private var tvName: TextView = binding.productCatalogName
        private var tvCategory: TextView = binding.productCatalogCategory
        private var tvNationalPrice: TextView = binding.nationalPrice
        private var tvPredictionPrice: TextView = binding.predictionPrice

        fun bind(productCatalogItem: ProductcatalogItem) {

            Glide.with(itemView.context)
                .load(productCatalogItem.photoUrl)
                .into(imgPhoto)
            tvName.text = productCatalogItem.name
            tvCategory.text = productCatalogItem.category

            val indonesianLocale = Locale("in", "ID")
            val numberFormat = NumberFormat.getInstance(indonesianLocale)
            val formattedNationalPrice = numberFormat.format(productCatalogItem.nationalPrice)
            val formattedPredictionPrice = numberFormat.format(productCatalogItem.predictionPrice)

            tvNationalPrice.text = "IDR " + formattedNationalPrice.toString()
            tvPredictionPrice.text = "IDR " + formattedPredictionPrice.toString()

            Log.d("Florys", productCatalogItem.photoUrl)

            itemView.setOnClickListener {

                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(imgPhoto, "photo"),
                        Pair(tvName, "name"),
                        Pair(tvCategory, "category"),
                        Pair(tvNationalPrice, "nationalPrice"),
                        Pair(tvPredictionPrice, "predictionPrice")
                    )

//                Toast.makeText(context, "TestC", Toast.LENGTH_LONG).show()
//                val intent = Intent(itemView.context, ProductCatalogDetailActivity::class.java)
//                intent.putExtra("ProductCatalog", productCatalogItem)
//                itemView.context.startActivity(intent, optionsCompat.toBundle())
            }
        }
    }
}