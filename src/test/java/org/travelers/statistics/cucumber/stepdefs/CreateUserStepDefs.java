package org.travelers.statistics.cucumber.stepdefs;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.travelers.statistics.config.KafkaProperties;
import org.travelers.statistics.domain.User;
import org.travelers.statistics.repository.UserRepository;
import org.travelers.statistics.service.mapper.UserMapper;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateUserStepDefs {

    private User user;

    private final KafkaProperties kafkaProperties;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public CreateUserStepDefs(KafkaProperties kafkaProperties,
                              UserMapper userMapper,
                              UserRepository userRepository) {
        this.kafkaProperties = kafkaProperties;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    @Given("user wants to create an account with the following attributes")
    public void user_create_account_with_attributes(DataTable dataTable) {
        this.user = (User) dataTable.asList(User.class).get(0);
    }

    @When("^user save the new account .*?")
    public void user_save_new_account() throws JsonProcessingException {
        String message = userMapper.mapToJson(user);

        KafkaProducer<String, String> producer = new KafkaProducer<>(kafkaProperties.getProducerProps());
        producer.send(new ProducerRecord<>("create-new-user", message));
    }

    @Then("the user save is {string}")
    public void save_is(String expectedResult) throws InterruptedException {
        Thread.sleep(5000);

        List<User> result = userRepository.findByLogin(user.getLogin());

        if ("SUCCESSFUL".equals(expectedResult)) {
            assertThat(result).isNotNull();
            assertThat(result.size()).isGreaterThan(0);
        } else if ("FAIL".equals(expectedResult)) {
            assertThat(result).isEmpty();
        }
    }

}
