package com.ecomm.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecomm.app.models.Product
import com.ecomm.app.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val productRepository: ProductRepository) : ViewModel() {
    val productsLiveData: LiveData<List<Product>>
        get() = productRepository.products

    val progressbarStatus: LiveData<Boolean>
        get() = productRepository.progressbarStatus

    val favProductsListLiveData: LiveData<List<Product>>
        get() = productRepository.favProductsList

    val hasFavProductLiveData: LiveData<Boolean>
        get() = productRepository.hasFavProduct

    val hasProductsLiveData: LiveData<Int>
        get() = productRepository.hasProducts

    val isExistsLiveData: LiveData<Boolean>
        get() = productRepository.isExists

    fun getAllProducts(){
        viewModelScope.launch {
            productRepository.getProducts()
        }
    }

    fun getFavProducts() {
        viewModelScope.launch {
            productRepository.getFavProducts()
        }
    }

    fun checkFavProdExists(product: Product) {
        viewModelScope.launch {
            productRepository.hasFavProduct(product)
        }
    }

    fun addFavProd(product: Product) {
        viewModelScope.launch {
            productRepository.addFavProduct(product)
        }
    }

    fun deleteFavProduct(product: Product) = runBlocking {
        val job = launch { productRepository.deleteFavProduct(product) }
        job.join()
    }

    fun hasProducts() {
        viewModelScope.launch {
            productRepository.hasProducts()
        }
    }

    fun isDataExists() {
        viewModelScope.launch {
            productRepository.isExists()
        }
    }
}