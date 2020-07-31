package org.travelers.statistics.web.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils;
import org.testcontainers.shaded.org.apache.commons.lang.math.RandomUtils;
import org.travelers.statistics.domain.Country;
import org.travelers.statistics.domain.User;
import org.travelers.statistics.service.dto.CountryDTO;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

import static org.assertj.core.api.Assertions.assertThat;


public final class TestUtil {

    private static final ObjectMapper mapper = createObjectMapper();

    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        return mapper.writeValueAsBytes(object);
    }

    public static byte[] createByteArray(int size, String data) {
        byte[] byteArray = new byte[size];
        for (int i = 0; i < size; i++) {
            byteArray[i] = Byte.parseByte(data, 2);
        }
        return byteArray;
    }

    public static String convertObjectToJson(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }

    public static class ZonedDateTimeMatcher extends TypeSafeDiagnosingMatcher<String> {

        private final ZonedDateTime date;

        public ZonedDateTimeMatcher(ZonedDateTime date) {
            this.date = date;
        }

        @Override
        protected boolean matchesSafely(String item, Description mismatchDescription) {
            try {
                if (!date.isEqual(ZonedDateTime.parse(item))) {
                    mismatchDescription.appendText("was ").appendValue(item);
                    return false;
                }
                return true;
            } catch (DateTimeParseException e) {
                mismatchDescription.appendText("was ").appendValue(item)
                    .appendText(", which could not be parsed as a ZonedDateTime");
                return false;
            }

        }

        @Override
        public void describeTo(Description description) {
            description.appendText("a String representing the same Instant as ").appendValue(date);
        }
    }

    public static ZonedDateTimeMatcher sameInstant(ZonedDateTime date) {
        return new ZonedDateTimeMatcher(date);
    }

    public static <T> void equalsVerifier(Class<T> clazz) throws Exception {
        T domainObject1 = clazz.getConstructor().newInstance();
        assertThat(domainObject1.toString()).isNotNull();
        assertThat(domainObject1).isEqualTo(domainObject1);
        assertThat(domainObject1.hashCode()).isEqualTo(domainObject1.hashCode());

        Object testOtherObject = new Object();
        assertThat(domainObject1).isNotEqualTo(testOtherObject);
        assertThat(domainObject1).isNotEqualTo(null);

        T domainObject2 = clazz.getConstructor().newInstance();
        assertThat(domainObject1).isNotEqualTo(domainObject2);

        assertThat(domainObject1.hashCode()).isEqualTo(domainObject2.hashCode());
    }

    public static FormattingConversionService createFormattingConversionService() {
        DefaultFormattingConversionService dfcs = new DefaultFormattingConversionService();
        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        registrar.setUseIsoFormat(true);
        registrar.registerFormatters(dfcs);
        return dfcs;
    }

    public static CountryDTO getCountryDTO() {
        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setCount(RandomUtils.nextInt());
        countryDTO.setCountry(RandomStringUtils.randomAlphanumeric(3));
        countryDTO.setDate(LocalDate.now());

        return countryDTO;
    }

    public static Country getCountry() {
        Country country = new Country();
        country.setCountry(RandomStringUtils.randomAlphanumeric(3));
        country.setLogin(RandomStringUtils.randomAlphanumeric(10));
        country.setDate(LocalDate.now());

        return country;
    }

    public static User getUser() {
        User user = new User();
        user.setLogin(RandomStringUtils.randomAlphanumeric(10));
        user.setDate(LocalDate.now());

        return user;
    }


    private TestUtil() {
    }
}
