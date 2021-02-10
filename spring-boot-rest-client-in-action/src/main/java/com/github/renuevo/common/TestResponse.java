package com.github.renuevo.common;

import lombok.Data;

@Data
public class TestResponse {

    private String testName;

    public void setTestName(String testName){
        this.testName = testName;
    }
}
