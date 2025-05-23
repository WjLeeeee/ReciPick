package com.woojin.recipick

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val _addRecipeClick = MutableSharedFlow<Unit>()
    val addRecipeClick: SharedFlow<Unit> = _addRecipeClick.asSharedFlow()

    fun onAddRecipeClick() {
        viewModelScope.launch {
            _addRecipeClick.emit(Unit)
        }
    }
}