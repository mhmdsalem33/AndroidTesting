package com.salem.testing.flows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModelV2(private val getUserProfileUseCase: GetUserProfileUseCaseV2) : ViewModel() {

    private val _profileUiState = MutableStateFlow<ProfileUIState>(ProfileUIState.Idle)
    val profileUiState = _profileUiState.asStateFlow()

    //
    init {
//       getUserProfile()

//        getUserProfileWayTwo()
    }


    fun getUserProfileWayOne() = viewModelScope.launch {
        _profileUiState.update { ProfileUIState.Loading }
        getUserProfileUseCase.getProfileData()
            .catch { exception ->
                _profileUiState.update { ProfileUIState.Error(exception.message ?: "") }
            }
            .collect { result ->
                result.onSuccess { profile ->
                    _profileUiState.update { ProfileUIState.Success(profile) }
                }
            }
    }


    fun getUserProfileWayTwo() = viewModelScope.launch {
        getUserProfileUseCase.getProfileData()
            .onStart {
                _profileUiState.update { ProfileUIState.Loading }
            }
            .onEach {
                val profile = it.getOrNull() ?: return@onEach
                when {
                    it.isSuccess -> { _profileUiState.update { ProfileUIState.Success(profile) } }
                    it.isFailure -> { _profileUiState.update { ProfileUIState.Error(it.toString()) } }
                }
            }
            .catch { exception ->
                _profileUiState.update { ProfileUIState.Error(exception.message ?: "") }
            }
            .launchIn(viewModelScope)

    }
}


sealed class ProfileUIState {
    data object Idle : ProfileUIState()
    data object Loading : ProfileUIState()
    data class Success(val data: Profile) : ProfileUIState()
    data class Error(val message: String) : ProfileUIState()
}