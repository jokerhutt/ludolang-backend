package com.testingpractice.duoclonebackend.auth.api.dto

import com.fasterxml.jackson.annotation.JsonProperty

class GoogleUserInfo {

    var email: String? = null
    var name: String? = null

    @JsonProperty("given_name")
    var givenName: String? = null

    @JsonProperty("family_name")
    var familyName: String? = null
}