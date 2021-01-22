package com.devtides.coroutinesroom.model

object LoginState {
    var isLoggedIn = false
    var user: User? = null

    fun logIn(user: User) {
        isLoggedIn = true
        this.user = user
    }

    fun logOut() {
        isLoggedIn = false
        this.user = null
    }
}