package ru.javawebinar.basejava.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class LifeStage {
    private Organization organization;
    private List<LifePeriod> periods;

    public LifeStage(Organization organization, List<LifePeriod> periods) {
        this.organization = organization;
        this.periods = periods;
    }

    public LifeStage(Organization organization, LifePeriod... periods) {
        this.organization = organization;
        this.periods = Arrays.asList(periods);
    }

    public List<LifePeriod> getData() {
        return periods;
    }

    @Override
    public String toString() {
        String string = "";
        string += organization.getTitle() + "\n";
        for (LifePeriod period : periods) {
            string += period + "\n";
        }
        return string;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LifeStage lifeStage = (LifeStage) o;
        return Objects.equals(organization, lifeStage.organization) &&
                Objects.equals(periods, lifeStage.periods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organization, periods);
    }

}
