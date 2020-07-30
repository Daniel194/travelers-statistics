package org.travelers.statistics.cucumber.stepdefs;

import io.cucumber.core.api.TypeRegistry;
import io.cucumber.core.api.TypeRegistryConfigurer;
import io.cucumber.datatable.DataTableType;
import io.cucumber.datatable.TableEntryTransformer;
import org.travelers.statistics.domain.Country;
import org.travelers.statistics.domain.User;
import org.travelers.statistics.service.dto.CountryDTO;
import org.travelers.statistics.service.dto.UserDTO;

import java.time.LocalDate;
import java.util.Locale;

public class Configurer implements TypeRegistryConfigurer {

    @Override
    public Locale locale() {
        return Locale.ENGLISH;
    }

    @Override
    public void configureTypeRegistry(TypeRegistry registry) {
        registry.defineDataTableType(new DataTableType(User.class, (TableEntryTransformer<User>) entry -> {
            User user = new User();
            user.setId(entry.get("id"));
            user.setLogin(entry.get("login"));
            user.setDate(LocalDate.parse(entry.get("date")));

            return user;
        }));

        registry.defineDataTableType(new DataTableType(UserDTO.class, (TableEntryTransformer<UserDTO>) entry -> {
            UserDTO user = new UserDTO();
            user.setCount(Integer.parseInt(entry.get("count")));
            user.setDate(LocalDate.parse(entry.get("date")));

            return user;
        }));

        registry.defineDataTableType(new DataTableType(Country.class, (TableEntryTransformer<Country>) entry -> {
            Country country = new Country();
            country.setId(entry.get("id"));
            country.setLogin(entry.get("login"));
            country.setCountry(entry.get("country"));
            country.setDate(LocalDate.parse(entry.get("date")));

            return country;
        }));

        registry.defineDataTableType(new DataTableType(CountryDTO.class, (TableEntryTransformer<CountryDTO>) entry -> {
            CountryDTO country = new CountryDTO();
            country.setCountry(entry.get("country"));
            country.setDate(LocalDate.parse(entry.get("date")));
            country.setCount(Integer.parseInt(entry.get("count")));

            return country;
        }));
    }
}
