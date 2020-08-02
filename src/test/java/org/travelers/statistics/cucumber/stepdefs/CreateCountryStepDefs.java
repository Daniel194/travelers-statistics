package org.travelers.statistics.cucumber.stepdefs;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.travelers.statistics.config.KafkaProperties;
import org.travelers.statistics.domain.Country;
import org.travelers.statistics.repository.CountryRepository;
import org.travelers.statistics.service.mapper.CountryMapper;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateCountryStepDefs extends StepDefs {

    private final KafkaProperties kafkaProperties;
    private final CountryMapper countryMapper;
    private final CountryRepository countryRepository;

    private Country country;

    public CreateCountryStepDefs(KafkaProperties kafkaProperties,
                                 CountryMapper countryMapper,
                                 CountryRepository countryRepository) {
        this.kafkaProperties = kafkaProperties;
        this.countryMapper = countryMapper;
        this.countryRepository = countryRepository;
    }

    @Given("user wants to add a country with the following attributes")
    public void user_add_country_with_attributes(DataTable dataTable) {
        this.country = (Country) dataTable.asList(Country.class).get(0);
    }

    @When("^user save the new country .*?")
    public void user_save_new_country() throws JsonProcessingException {
        String message = countryMapper.mapToJson(country);

        KafkaProducer<String, String> producer = new KafkaProducer<>(kafkaProperties.getProducerProps());
        producer.send(new ProducerRecord<>("add-country", message));
    }

    @Then("the country save is {string}")
    public void save_is(String expectedResult) throws InterruptedException {
        Thread.sleep(5000);

        List<Country> result = countryRepository.findByLoginAndCountry(country.getLogin(), country.getCountry());

        if ("SUCCESSFUL".equals(expectedResult)) {
            assertThat(result).isNotNull();
            assertThat(result.size()).isGreaterThan(0);
        } else if ("FAIL".equals(expectedResult)) {
            assertThat(result).isEmpty();
        }
    }

}
