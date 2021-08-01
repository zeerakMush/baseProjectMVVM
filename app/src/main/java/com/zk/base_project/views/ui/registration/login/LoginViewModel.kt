package com.zk.base_project.views.ui.registration.login

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zk.base_project.utils.EditField
import com.zk.base_project.utils.FormValidator
import com.zk.base_project.utils.getError
import com.zk.base_project.views.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
        private val appContext: Application,
        private val validator: FormValidator,
) : BaseViewModel<LoginViewModel.Event>() {
    val email = EditField(viewModelScope, validator::emailValidate, "")
    val password = EditField(viewModelScope, validator::passwordValidate, "")
    val error = MutableLiveData<String?>(null)

    fun onLoginClicked() {
        hideKeyboard()
        listOf(email, password).getError().let { errorMessage ->
            error.value = errorMessage
            if (error.value == null) {
                toggleLoading(true)
                viewModelScope.launch {
                }
            }
        }
    }

    sealed class Event {

    }
}