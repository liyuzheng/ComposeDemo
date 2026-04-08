package yz.l.compose.impl

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * desc:
 * created by liyuzheng on 2026/3/16 20:07
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    override fun onCleared() {
        Log.v("LoginViewModel", "onCleared")
        super.onCleared()
    }

    fun test() {

    }
}