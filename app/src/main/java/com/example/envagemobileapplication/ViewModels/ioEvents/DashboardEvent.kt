package com.example.envagemobileapplication.ViewModels.ioEvents


class DashboardEvent {
    fun login(username: String, password: String): Boolean {
        // Perform authentication logic (e.g., check credentials)
        return username == "demo" && password == "password"
    }
}