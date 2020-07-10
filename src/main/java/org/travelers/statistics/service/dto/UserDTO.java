package org.travelers.statistics.service.dto;

import java.time.LocalDate;

public class UserDTO {

    private LocalDate date;
    private Integer count;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
