package com.serveterdogan.shopapp.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serveterdogan.shopapp.domain.model.Product
import com.serveterdogan.shopapp.domain.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository
): ViewModel() {


    private val _state = MutableStateFlow(FavoriteState())
    val state : StateFlow<FavoriteState> = _state.asStateFlow()


    init {
        loadFavoriteProduct()
        refreshFavorites()
    }

    private fun refreshFavorites() {
        viewModelScope.launch {
            favoriteRepository.refreshFavoriteProducts()
        }
    }


    fun loadFavoriteProduct(){
        viewModelScope.launch {
            favoriteRepository.getFavoriteProducts().collect{
                _state.value = _state.value.copy(
                    favoriteList = it,
                    isLoading = false
                )
            }
        }
        }



    fun addProductFavorite(product : Product){
        viewModelScope.launch {
           val result =  favoriteRepository.addFavoriteProduct(product)

            result.onSuccess {
                _state.value = _state.value.copy(
                    favoriteList = _state.value.favoriteList + product,
                    isLoading = false
                )
            }.onFailure {
                _state.value = _state.value.copy(
                    isError = it.localizedMessage,
                    isLoading = false
                )
            }
        }
    }



    fun deleteProductFavorite(id : Int){
        viewModelScope.launch {
          val result =   favoriteRepository.deleteFavoriteProduct(id)

            result.onSuccess {
                _state.value = _state.value.copy(
                    isLoading = false,
                    favoriteList = _state.value.favoriteList.filter { it.id != id }
                )
            }

            result.onFailure {
                _state.value = _state.value.copy(
                    isError = it.localizedMessage,
                    isLoading = false
                )
            }
        }
    }


    }


