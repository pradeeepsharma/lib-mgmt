package com.learning.LibraryManagementSystem;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty"},
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        features = "classpath:features"
)
public class CucumberIT {

}
