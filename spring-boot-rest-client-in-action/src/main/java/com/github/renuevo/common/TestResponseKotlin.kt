package com.github.renuevo.common

data class TestResponseKotlin(
//        @JsonProperty("test_name")
        var testName: String,
//        @JsonProperty("test_number")
        var testNumber: String,
        var statusCode: STATUS_CODE

)

enum class STATUS_CODE {
    OK,
    FAIL
}