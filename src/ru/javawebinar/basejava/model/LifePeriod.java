package ru.javawebinar.basejava.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class LifePeriod  implements Serializable {
    static final long serialVersionUID = 125L;
    private LocalDate startDate;
    private LocalDate endDate;

    private String title;
    private String description;

    public LifePeriod(LocalDate startDate, LocalDate endDate, String title, String description) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return dateCompact(startDate) + " - " + dateCompact(endDate) + " " + title + (description != null ? "\n" + description : "");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LifePeriod that = (LifePeriod) o;
        return Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(title, that.title) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate, title, description);
    }

    private String dateCompact(LocalDate date) {
        if (date.getYear() < 9999) {
            int monthNum = date.getMonthValue();
            return (monthNum < 10 ? "0" : "") + monthNum + "/" + date.getYear();
        } else {
            return "сейчас";
        }
    }

}
