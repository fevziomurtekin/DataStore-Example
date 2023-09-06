package com.fevziomurtekin.datastore_example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fevziomurtekin.datastore_example.data.UserNameUiModel
import com.fevziomurtekin.datastore_example.data.UserNameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userNameRepository: UserNameRepository
): ViewModel(){

    private val _userNameState = MutableStateFlow(UserNameUiModel())
    val userNameState = _userNameState.asStateFlow()

    init {
        observeUserNameState()
    }

    private fun observeUserNameState() {
        viewModelScope.launch {
            userNameRepository.userPreferencesFlow.collectLatest {
                _userNameState.value = it
            }
        }
    }

    fun updateUserName(name: String) {
        viewModelScope.launch { userNameRepository.updateUserName(name) }
    }
}