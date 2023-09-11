package com.example.data.repository

import android.app.Application
import android.content.Context
import com.example.domain.repository.TokenManagerRepo
import com.example.domain.utils.Constants
import com.example.domain.utils.Resourse

class TokenManagerRepoImp(private val context:Application):TokenManagerRepo {
        override suspend fun setAdminTokenInSherdPreferences(admin_token: String):Resourse<String> {
            var sharedPreferences = context
                .getSharedPreferences(Constants.shared_preferences_name, Context.MODE_PRIVATE)
            var editor=sharedPreferences.edit()
            try {
                editor.putString(Constants.admin_token,admin_token)
                editor.apply()
                return Resourse.Success("sucess")

            }catch (e:Exception){

                return Resourse.Error(e.message.toString())
            }

    }

    override suspend fun getAdminTokenInSherdPreferences(): Resourse<String> {

        var sharedPreferences = context
            .getSharedPreferences(Constants.shared_preferences_name, Context.MODE_PRIVATE)

        try {

            var token=  sharedPreferences.getString(Constants.admin_token,Constants.empty_admin)
            return Resourse.Success("${token}")

        }catch (e:Exception){

            return Resourse.Error(e.message.toString())
        }
    }

    override suspend fun setVoterTokenInSherdPreferences(voter_token: String): Resourse<String> {
        var sharedPreferences = context
            .getSharedPreferences(Constants.shared_preferences_name, Context.MODE_PRIVATE)
        var editor=sharedPreferences.edit()
        try {
            editor.putString(Constants.voter_token,voter_token)
            editor.apply()
            return Resourse.Success("sucess")

        }catch (e:Exception){

            return Resourse.Error(e.message.toString())
        }


    }

    override suspend fun getVoterTokenInSherdPreferences(): Resourse<String> {
        var sharedPreferences = context
            .getSharedPreferences(Constants.shared_preferences_name, Context.MODE_PRIVATE)

        try {

            var token=  sharedPreferences.getString(Constants.voter_token,Constants.empty_voter)
            return Resourse.Success("${token}")

        }catch (e:Exception){

            return Resourse.Error(e.message.toString())
        }
    }

}