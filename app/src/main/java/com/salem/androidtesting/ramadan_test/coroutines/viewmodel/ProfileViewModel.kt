package com.salem.androidtesting.ramadan_test.coroutines.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salem.androidtesting.ramadan_test.coroutines.UseCase.GetUserProfileUseCase
import com.salem.androidtesting.ramadan_test.coroutines.UseCase.Profile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(private val getUserProfileUseCase: GetUserProfileUseCase) : ViewModel() {

    private val _profileUiState = MutableStateFlow<ProfileUIState>(ProfileUIState.Idle)
    val profileUiState = _profileUiState.asStateFlow()

    init {
        getUserProfile()
    }


    fun getUserProfile() = viewModelScope.launch {
        runCatching {
            getUserProfileUseCase.getProfileDataAsync()
        }.onSuccess { profile ->
            _profileUiState.emit(ProfileUIState.Success(profile))
        }.onFailure { error ->
            _profileUiState.emit(ProfileUIState.Error(error.message ?:"" ))
        }

    }

}


sealed class ProfileUIState{
    data object Idle : ProfileUIState()
    data object Loading : ProfileUIState()
    data class Success(val data : Profile ) : ProfileUIState()
    data class Error(val message : String ) : ProfileUIState()
}