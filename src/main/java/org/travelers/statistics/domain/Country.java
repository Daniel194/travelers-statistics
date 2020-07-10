package org.travelers.statistics.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.mongodb.core.index.Indexed;
import org.travelers.statistics.config.Constants;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@org.springframework.data.mongodb.core.mapping.Document(collection = "country")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "country_statistics_details")
public class Country extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private String id;

    @NotNull
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    @Indexed
    private String login;

    @Size(min = 3, max = 3)
    @NotNull
    private String country;

    @NotNull
    private LocalDate date;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country1 = (Country) o;
        return Objects.equals(id, country1.id) &&
            Objects.equals(login, country1.login) &&
            Objects.equals(country, country1.country) &&
            Objects.equals(date, country1.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, country, date);
    }

    @Override
    public String toString() {
        return "Country{" +
            "id='" + id + '\'' +
            ", login='" + login + '\'' +
            ", country='" + country + '\'' +
            ", when=" + date +
            '}';
    }
}
