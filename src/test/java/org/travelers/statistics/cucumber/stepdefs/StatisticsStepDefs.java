package org.travelers.statistics.cucumber.stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StatisticsStepDefs extends StepDefs {

    @When("admin wants to see country statistics between {string} and {string}")
    public void admin_wants_country_statistics(String begin, String end) {

    }

    @When("admin wants to see country statistics for {string} between {string} and {string}")
    public void admin_wants_country_statistics_for(String begin, String end) {

    }

    @When("admin wants to see user statistics between {string} and {string}")
    public void admin_wants_user_statistics(String begin, String end) {

    }

    @Then("the response is {string}")
    public void response_is(String message) {

    }

    @And("following statistics are returned")
    public void statistics_are_returned() {

    }

}
