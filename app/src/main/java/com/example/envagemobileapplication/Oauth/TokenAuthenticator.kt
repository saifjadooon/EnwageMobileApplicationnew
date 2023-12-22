package com.example.envagemobileapplication.Oauth

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route


/*

class TokenAuthenticator : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        // Check if the response indicates that the token has expired or another auth issue
        if (isTokenExpired(response)) {
            // Implement your logic to refresh the token (e.g., make a Retrofit request)
            val newToken = refreshToken()

            // If the token was successfully refreshed, create a new request with the updated token
            if (newToken != null) {
                return response.request.newBuilder()
                    .header("Authorization", "Bearer $newToken")
                    .build()
            }
        }

        // If token refresh failed or there's another authentication issue, return null
        return null
    }

    private fun isTokenExpired(response: Response): Boolean {
        // Implement your logic to check if the token in the response is expired
        // Return true if the token has expired, false otherwise
        var tokenManager = TokenManager(Constants.context!!)

        var viewmodel: SharedLoginViewModel = SharedLoginViewModel()
        val username = "upshiftuser"
        val password = "123qweASD*"
        viewmodel.loginUser(username, password, tokenManager)
        return false
    }

    private fun refreshToken(): String? {


        var tokenManager = TokenManager(Constants.context!!)

        var viewmodel: SharedLoginViewModel = SharedLoginViewModel()
        val username = "upshiftuser"
        val password = "123qweASD*"
        viewmodel.loginUser(username, password, tokenManager)
        // Implement your logic to refresh the token, e.g., by making a Retrofit request
        // Return the new token or null if the refresh failed
        return null
    }
}*/


class TokenAuthenticator(private val authToken: String) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        return if (response.request.header("Authorization") != null) {
            // If the request already had an Authorization header, don't add it again
            null
        } else response.request.newBuilder()
            .header("Authorization", "Bearer $authToken")
            .build()

        // Add the Bearer token to the request header
    }
}
