package ru.javawebinar.basejava.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class BioSection extends AbstractResumeSection {
    static final long serialVersionUID = 125L;
    private List<LifeStage> stages;

    public BioSection(LifeStage ... stages) {
        this.stages = Arrays.asList(stages);
    }

    public BioSection(List<LifeStage> stages) {
        this.stages = stages;
    }

    @Override
    public List<LifeStage> getData() {
        return stages;
    }

    @Override
    public String toString() {
        String string = "";
        for (LifeStage stage : stages) {
            string += stage + "\n";
        }
        return string;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BioSection that = (BioSection) o;
        return Objects.equals(stages, that.stages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stages);
    }
}
