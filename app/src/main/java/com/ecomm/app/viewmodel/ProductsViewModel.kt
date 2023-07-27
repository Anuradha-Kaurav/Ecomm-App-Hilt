package com.ecomm.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecomm.app.models.Product
import com.ecomm.app.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(private val productRepository: ProductRepository): ViewModel() {

    val productsLiveData : LiveData<List<Product>>
        get() = productRepository.products

    init {
        viewModelScope.launch {
            productRepository.getProducts()
        }
    }
}