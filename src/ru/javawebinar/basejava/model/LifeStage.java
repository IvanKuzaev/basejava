package ru.javawebinar.basejava.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class LifeStage  implements Serializable {
    static final long serialVersionUID = 126L;
    private Organization organization;
    private List<LifePeriod> periods;

    public LifeStage() {
    }

    public LifeStage(Organization organization, List<LifePeriod> periods) {
        this.organization = organization;
        this.periods = periods;
        this.periods.sort((o1, o2) -> { return o2.getStartDate().compareTo(o1.getStartDate()); });
    }

    public LifeStage(Organization organization, LifePeriod... periods) {
        this(organization, Arrays.asList(periods));
    }

    public List<LifePeriod> getData() {
        return periods;
    }

    public int size() {
        return periods.size();
    }

    public Organization getOrganization() {
        return organization;
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
