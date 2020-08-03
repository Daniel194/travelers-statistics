package org.travelers.statistics.cucumber.stepdefs;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.travelers.statistics.service.dto.CountryDTO;
import org.travelers.statistics.service.dto.UserDTO;
import org.travelers.statistics.web.rest.StatisticsResource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class StatisticsStepDefs extends StepDefs {

    private final StatisticsResource statisticsResource;

    public StatisticsStepDefs(StatisticsResource statisticsResource) {
        this.statisticsResource = statisticsResource;
    }

    @When("admin wants to see country statistics between {string} and {string}")
    public void admin_wants_country_statistics(String begin, String end) throws Exception {
        MockMvc restUserMockMvc = MockMvcBuilders.standaloneSetup(statisticsResource).build();

        actions = restUserMockMvc.perform(get("/api/statistic/country?begin=" + begin + "&end=" + end)
            .contentType(MediaType.APPLICATION_JSON));
    }

    @When("admin wants to see country statistics for {string} between {string} and {string}")
    public void admin_wants_country_statistics_for(String country, String begin, String end) throws Exception {
        MockMvc restUserMockMvc = MockMvcBuilders.standaloneSetup(statisticsResource).build();

        actions = restUserMockMvc.perform(get("/api/statistic/country/" + country + "?begin=" + begin + "&end=" + end)
            .contentType(MediaType.APPLICATION_JSON));
    }

    @When("admin wants to see user statistics between {string} and {string}")
    public void admin_wants_user_statistics(String begin, String end) throws Exception {
        MockMvc restUserMockMvc = MockMvcBuilders.standaloneSetup(statisticsResource).build();

        actions = restUserMockMvc.perform(get("/api/statistic/user?begin=" + begin + "&end=" + end)
            .contentType(MediaType.APPLICATION_JSON));
    }

    @Then("the response is SUCCESSFUL")
    public void response_is() {
        assertThat(actions.andReturn().getResponse().getStatus()).isIn(200, 201);
    }

    @And("following user statistics are returned")
    public void user_statistics_are_returned(DataTable dataTable) throws Exception {
        List<UserDTO> users = dataTable.asList(UserDTO.class);

        actions
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.length()").value(users.size()));
    }

    @And("following country statistics are returned")
    public void country_statistics_are_returned(DataTable dataTable) throws Exception {
        List<CountryDTO> country = dataTable.asList(CountryDTO.class);

        actions
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.length()").value(country.size()));
    }

}
