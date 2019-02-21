package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.Objects;

public class LifePeriod extends DataItem<String> {
    protected LocalDate startDate;
    protected LocalDate endDate;

    public LifePeriod(LocalDate startDate, LocalDate endDate, String title, String description) {
        super(title, description);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        LifePeriod that = (LifePeriod) o;
        return Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), startDate, endDate);
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return dateCompact(startDate) + " - " + dateCompact(endDate) + " " + title + (data != null ? "\n" + data: "");
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
