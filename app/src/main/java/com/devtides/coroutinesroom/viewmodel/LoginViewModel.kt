package com.devtides.coroutinesroom.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devtides.coroutinesroom.model.LoginState
import com.devtides.coroutinesroom.model.UserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val db by lazy { UserDatabase(getApplication()).userDao() }
    val loginComplete = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    fun login(username: String, password: String) {
        coroutineScope.launch {
            val userFromDb = db.getUser(username)
            withContext(Dispatchers.Main) {
                if (userFromDb != null) {
                    if (userFromDb.passwordHash == password.hashCode()) {
                        LoginState.logIn(userFromDb)
                        loginComplete.value = true
                    } else {
                        error.value = "Wrong password "
                    }
                } else {
                    error.value = "User not found :("
                }
            }
        }
    }
}