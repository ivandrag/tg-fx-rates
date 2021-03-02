package ro.dragosivanov.tgo.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {

    private val _onEvent = MutableLiveData<OnEvent>()

    val onEvent : LiveData<OnEvent>
        get() = _onEvent

    fun onItemClick(code: String, flag: Int) {
        _onEvent.value = OnEvent.ItemClick(code, flag)
    }

    sealed class OnEvent {
        data class ItemClick(val code: String, val flag: Int) : OnEvent()
    }
}
