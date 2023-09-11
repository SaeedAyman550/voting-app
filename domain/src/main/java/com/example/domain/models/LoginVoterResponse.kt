package com.example.domain.models

import com.example.domain.utils.Constants

data class LoginVoterResponse (val accessToken: String="${Constants.empty_voter}", val result: String="")
