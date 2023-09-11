package com.example.domain.repository

import com.example.domain.utils.Resourse

interface TokenManagerRepo {


    suspend fun setAdminTokenInSherdPreferences(admin_token:String): Resourse<String>

    suspend fun getAdminTokenInSherdPreferences(): Resourse<String>

    suspend fun setVoterTokenInSherdPreferences(voter_token:String): Resourse<String>

    suspend fun getVoterTokenInSherdPreferences(): Resourse<String>
}