package org.travelers.statistics.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.travelers.statistics.service.CountryConsumerService;
import org.travelers.statistics.service.UserConsumerService;

@Component
public class ApplicationReadyListener {

    private final Logger log = LoggerFactory.getLogger(ApplicationReadyListener.class);

    private final UserConsumerService userConsumerService;
    private final CountryConsumerService countryConsumerService;

    @Autowired
    public ApplicationReadyListener(UserConsumerService userConsumerService,
                                    CountryConsumerService countryConsumerService) {
        this.userConsumerService = userConsumerService;
        this.countryConsumerService = countryConsumerService;
    }

    @Async("taskExecutor")
    @EventListener(ApplicationReadyEvent.class)
    public void startCreateNewUser() {
        log.info("START create-new-user");

        while (true) {
            userConsumerService.consumeCreateNewUser();
        }
    }

    @Async("taskExecutor")
    @EventListener(ApplicationReadyEvent.class)
    public void startAddCountry() {
        log.info("START add-country");

        while (true) {
            countryConsumerService.consumeAddCountry();
        }
    }

}
