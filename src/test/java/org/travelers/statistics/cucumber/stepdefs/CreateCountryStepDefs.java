package org.travelers.statistics.cucumber.stepdefs;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CreateCountryStepDefs extends StepDefs {

    @Given("user wants to add a country with the following attributes")
    public void user_add_country_with_attributes(DataTable dataTable) {

    }

    @When("user save the new country {string}")
    public void user_save_new_country(String testCase) {

    }

    @Then("the country save is {string}")
    public void save_is(String expectedResult) {

    }

}
