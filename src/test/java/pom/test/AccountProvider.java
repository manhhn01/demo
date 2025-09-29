package pom.test;

import org.testng.annotations.DataProvider;

public class AccountProvider {

    @DataProvider(name = "loginCases")
    public static Object[][] loginCases() {
        return new Object[][]{
                // username,         password,        expectSuccess, expectedErrorContains (nullable),       variant
                {"standard_user", "secret_sauce", true, null, "NONE"},
                {"locked_out_user", "secret_sauce", false, "Epic sadface: Sorry, this user has been locked out.", "NONE"},
                {"standard_user", "wrong_pw", false, "Epic sadface: Username and password do not match any user in this service", "NONE"},
                {"", "", false, "Epic sadface: Username is required",  "NONE"},
                {"standard_user", "", false, "Epic sadface: Password is required", "NONE"},
                {"unknown_user", "secret_sauce", false, "Epic sadface: Username and password do not match any user in this service", "NONE"},
                {"  standard_user  ", "secret_sauce", false, "Epic sadface: Username and password do not match any user in this service", "NONE"},

                {"performance_glitch_user", "secret_sauce", true, null, "PERF"},
                {"visual_user", "secret_sauce", true, null, "VISUAL"},
                {"problem_user", "secret_sauce", true, null, "PROBLEM"},
                {"error_user", "secret_sauce", true, null, "ERROR"},

        };
    }
}

