package com.zk.base_project.views

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zk.base_project.network.ApiResponse
import com.zk.base_project.utils.SingleLiveEvent

open class BaseViewModel<E> : ViewModel() {
    protected val _events = SingleLiveEvent<E>()
    val events: LiveData<E> = _events

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _commonEvents = SingleLiveEvent<CommonEvents>()
    val commonEvents: LiveData<CommonEvents> = _commonEvents

    protected fun toggleLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    protected fun setCommonEvent(event: CommonEvents) {
        _commonEvents.value = event
    }

    protected fun hideKeyboard() {
        setCommonEvent(CommonEvents.HideKeyboard)
    }

    protected fun showMessage(message: String) {
        _commonEvents.value = CommonEvents.Message(message)
    }

    protected fun <T> handleResponse(response: ApiResponse<T>, successCall: (ApiResponse.Success<T>) -> Unit) {
        when (response) {
            is ApiResponse.Failed -> {
                toggleLoading(false)
                showMessage(response.message ?: "")
            }
            is ApiResponse.Success -> {

                successCall.invoke(response)
            }
        }
    }

    sealed class CommonEvents {
        data class Message(val message: String) : CommonEvents()
        object HideKeyboard : CommonEvents()
    }
}