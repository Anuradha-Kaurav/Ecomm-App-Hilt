package com.ecomm.app.view.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.ecomm.app.databinding.FragmentProductsBinding
import com.ecomm.app.db.ProductsDB
import com.ecomm.app.models.Product
import com.ecomm.app.utils.Constants
import com.ecomm.app.view.ui.activities.HomeActivity
import com.ecomm.app.view.ui.adapters.MainAdapter
import com.ecomm.app.view.ui.interfaces.ItemClickListener
import com.ecomm.app.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProductsFragment : Fragment(), ItemClickListener<Product> {

    private lateinit var binding: FragmentProductsBinding
    private val adapter = MainAdapter(this)

    private val homeViewModel: HomeViewModel by activityViewModels()
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
        binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
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
        fetchViewModelData()
        handleProgressbarStatus()
    }

    private fun fetchViewModelData() {
        homeViewModel.getAllProducts()
        homeViewModel.productsLiveData.observe(this, Observer {
            productsList = it.toMutableList()
            checkAndUpdateProducts()
        })
    }

    private fun checkAndUpdateProducts() {
        homeViewModel.isDataExists()
        homeViewModel.isExistsLiveData.observe(this, Observer {
            if (it) {
                getAllFavProductsUpdate()
            } else {
                adapter.setProductList(productsList)
            }
        })
    }

    private fun getAllFavProductsUpdate() {

        homeViewModel.getFavProducts()
        homeViewModel.favProductsListLiveData.observe(this, Observer { product ->
            product.forEach() firstLoop@{
                productsList.forEach() { item ->
                    run {
                        if(item.id == it.id){
                            item.isInWishlist = true
                            return@firstLoop
                        }
                    }
                }
            }
            adapter.setProductList(productsList)
        })
    }

    private fun handleProgressbarStatus() {
        homeViewModel.progressbarStatus.observe(this, Observer<Boolean>() {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })
    }

    override fun onItemClick(item: Product) {
        val direction = ProductsFragmentDirections.actionProductsFragmentToProductDetailsActivity(item)
        findNavController().navigate(direction)
    }

    override fun onFavClick(item: Product) {
        if (item.isInWishlist!!) {
            homeViewModel.addFavProd(item)
        } else {
            homeViewModel.deleteFavProduct(item)
        }
    }
}