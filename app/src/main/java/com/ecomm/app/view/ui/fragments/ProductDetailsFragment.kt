package com.ecomm.app.view.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.room.Room
import com.bumptech.glide.Glide
import com.ecomm.app.R
import com.ecomm.app.databinding.FragmentProductDetailsBinding
import com.ecomm.app.db.ProductsDB
import com.ecomm.app.models.Product
import com.ecomm.app.utils.Constants
import com.ecomm.app.view.ui.activities.ProductDetailsActivity
import com.ecomm.app.view.ui.activities.ProductDetailsActivityArgs
import com.ecomm.app.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {

    private lateinit var binding: FragmentProductDetailsBinding

    private lateinit var receivedObject: Product
    private val homeViewModel: HomeViewModel by viewModels()
    private val args: ProductDetailsActivityArgs by navArgs()

    @Inject
    lateinit var roomDb: ProductsDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        roomDb = Room.databaseBuilder(
            (activity as ProductDetailsActivity),
            ProductsDB::class.java,
            Constants.DB_NAME
        )
            .build()

        getArgs()
        initUi()
    }

    private fun getArgs() {
        receivedObject = args.product
    }

    private fun initUi() {

        binding.productName.text = receivedObject.title.toString()
        binding.productPrice.text = Constants.appendString(
            this.getString(R.string.AUD),
            receivedObject.price.toString()
        )
        binding.ratings.rating = receivedObject.rating.rate.toFloat()
        binding.ratingCount.text = receivedObject.rating.count.toString()

        Glide.with(this)
            .load(receivedObject.image)
            .placeholder(R.drawable.ic_placeholder)
            .into(binding.productImageDetails)

        if (receivedObject.isInWishlist!!) {
            Constants.selectFav(binding.favBtn, (activity as ProductDetailsActivity))
        }

        binding.backBtn.setOnClickListener { (activity as ProductDetailsActivity).finish() }

        binding.favBtn.setOnClickListener {
            if (receivedObject.isInWishlist!!) {
                receivedObject.isInWishlist = false
                homeViewModel.deleteFavProduct(receivedObject)
                Constants.unSelectFav(binding.favBtn, (activity as ProductDetailsActivity))
            } else {
                receivedObject.isInWishlist = true
                homeViewModel.addFavProd(receivedObject)
                Constants.selectFav(binding.favBtn, (activity as ProductDetailsActivity))
            }
        }
    }
}