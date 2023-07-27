package com.ecomm.app.view.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ecomm.app.R
import com.ecomm.app.databinding.AdapterProductBinding
import com.ecomm.app.models.Product
import com.ecomm.app.utils.Constants
import com.ecomm.app.view.ui.interfaces.ItemClickListener

class FavoritesAdapter(onProductClickListener: ItemClickListener<Product>): RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder>() {

    var products = mutableListOf<Product>()
    private val onProductClickListener: ItemClickListener<Product>?

    init {
        this.onProductClickListener = onProductClickListener
    }

    fun setProductList(products: List<Product>) {
        this.products = products.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterProductBinding.inflate(inflater, parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindItems(products[position])
    }

    override fun getItemCount(): Int {
        return products.size
    }

    inner class FavoriteViewHolder(val binding: AdapterProductBinding): RecyclerView.ViewHolder(binding.root){
        fun bindItems(product: Product) = with(binding) {

            binding.name.text = product.title

            val sb = StringBuilder()
            sb.append(root.context.getString(R.string.AUD))
                .append(product.price.toString())
            binding.price.text = sb.toString()

            Glide.with(binding.root.context)
                .load(product.image)
                .placeholder(R.drawable.ic_placeholder)
                .into(binding.productImage)

            binding.root.setOnClickListener {
                onProductClickListener?.onItemClick(products[adapterPosition])
            }

            if(products[adapterPosition].isInWishlist!!){
                Constants.selectFav(binding.fav, root.context)
            }else {
                Constants.unSelectFav(binding.fav, root.context)
            }

            binding.fav.setOnClickListener {
                    products[adapterPosition].isInWishlist = false
                    Constants.unSelectFav(binding.fav, root.context)
                notifyItemChanged(adapterPosition)
                onProductClickListener?.onFavClick(product)
            }
        }
    }
}