package ru.javawebinar.basejava.model;

import java.util.Arrays;
import java.util.List;

public class LifeStages extends DataList<LifeStage> {

    public LifeStages(String titel, List<LifeStage> list) {
        super(titel, list);
    }

    public LifeStages(String titel, LifeStage ... array) {
        super(titel, Arrays.asList(array));
    }

    @Override
    public String toString() {
        String string = "";
        for (LifeStage lf : getData()) {
            string += lf + "\n";
        }
        return string;
    }
}
