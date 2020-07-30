package org.travelers.statistics.cucumber.stepdefs;


import io.cucumber.datatable.DataTable;
import io.cucumber.datatable.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.travelers.statistics.domain.Country;
import org.travelers.statistics.domain.User;
import org.travelers.statistics.repository.CountryRepository;
import org.travelers.statistics.repository.UserRepository;

import java.util.List;

public class BackgroundStepDefs extends StepDefs {

    private List<Country> countries;
    private List<User> users;

    private final CountryRepository countryRepository;
    private final UserRepository userRepository;

    public BackgroundStepDefs(CountryRepository countryRepository, UserRepository userRepository) {
        this.countryRepository = countryRepository;
        this.userRepository = userRepository;
    }

    @Given("country/countries with the following attributes")
    public void country_with_following_attributes(DataTable dataTable) {
        DataTableType.entry(Country.class);
        this.countries = dataTable.asList(Country.class);
    }

    @When("country/countries already exists")
    public void country_already_exist() {
        countryRepository.deleteAll();
        countryRepository.saveAll(countries);
    }

    @Given("user/users with the following attributes")
    public void user_with_following_attributes(DataTable dataTable) {
        DataTableType.entry(User.class);
        this.users = dataTable.asList(User.class);
    }

    @When("user/users already exists")
    public void user_already_exist() {
        userRepository.deleteAll();
        userRepository.saveAll(users);
    }

}
