package com.ecomm.app.view.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.ecomm.app.databinding.FragmentFavouritesBinding
import com.ecomm.app.db.ProductsDB
import com.ecomm.app.models.Product
import com.ecomm.app.utils.Constants
import com.ecomm.app.view.ui.activities.HomeActivity
import com.ecomm.app.view.ui.adapters.FavoritesAdapter
import com.ecomm.app.view.ui.interfaces.ItemClickListener
import com.ecomm.app.viewmodel.HomeViewModel
import javax.inject.Inject

class FavoritesFragment : Fragment(), ItemClickListener<Product> {

    private lateinit var binding: FragmentFavouritesBinding
    private val adapter = FavoritesAdapter(this)

    private val homeViewModel: HomeViewModel by  activityViewModels()
    private lateinit var productsList: List<Product>

    @Inject
    lateinit var roomDb: ProductsDB

     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
     }

     override fun onCreateView(
         inflater: LayoutInflater, container: ViewGroup?,
         savedInstanceState: Bundle?
     ): View? {
         binding = FragmentFavouritesBinding.inflate(inflater, container, false)
         return binding.root
     }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerview.adapter = adapter

        roomDb =
            Room.databaseBuilder((activity as HomeActivity), ProductsDB::class.java, Constants.DB_NAME)
                .build()
    }

    override fun onResume() {
        super.onResume()
        fetchFavViewModelData()
    }

    private fun fetchFavViewModelData() {
        //check if there is any fav prods in db
        homeViewModel.hasProducts()
        homeViewModel.hasProductsLiveData.observe(this, Observer {
            if (it>0) {
                binding.favText.visibility = View.GONE
                getAllFavProducts()
            }else{
                binding.favText.visibility = View.VISIBLE
            }
        })
    }

    private fun getAllFavProducts() {

        homeViewModel.getFavProducts()
        homeViewModel.favProductsListLiveData.observe(this, Observer { product ->

            productsList = product
            adapter.setProductList(productsList)

        })
    }

    override fun onItemClick(item: Product) {
        val direction = FavoritesFragmentDirections.actionFavouritesFragmentToProductDetailsActivity(item)
        findNavController().navigate(direction)
    }

    override fun onFavClick(item: Product) {
        if(adapter.itemCount==1){
            binding.favText.visibility = View.VISIBLE
        }else{
            binding.favText.visibility = View.GONE
        }

        homeViewModel.deleteFavProduct(item)
        getAllFavProducts()
    }
}