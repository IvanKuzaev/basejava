package ru.javawebinar.basejava.model;

import java.util.Arrays;
import java.util.List;

public class LifeStage extends DataList<LifePeriod> {
    protected Organization organization;

    public LifeStage(Organization organization, List<LifePeriod> list) {
        super(organization.getTitle(), list);
        this.organization = organization;
    }

    public LifeStage(Organization organization, LifePeriod... array) {
        super(organization.getTitle(), Arrays.asList(array));
        this.organization = organization;
    }

//    @Override
//    public String toString() {
//        String string = "";
//        string +=
//        return string;
//    }
}
