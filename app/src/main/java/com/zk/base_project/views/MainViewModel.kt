package com.zk.base_project.views

import androidx.lifecycle.viewModelScope
import com.zk.base_project.database.AppDatabase
import com.zk.base_project.utils.PreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val prefs: PreferencesHelper,
    private val database: AppDatabase
) : BaseViewModel<MainViewModel.Event>() {

    fun logOutUser() {
        prefs.clearAllPrefs()
        viewModelScope.launch(Dispatchers.IO) {
            database.clearAllTables()
        }
    }

    sealed class Event {
        data class NavigateTo(val destinationId: Int) : Event()
    }
}