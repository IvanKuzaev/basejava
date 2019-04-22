package ru.javawebinar.basejava.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class BioSection extends AbstractResumeSection {
    static final long serialVersionUID = 125L;
    private List<LifeStage> stages;

    public BioSection() {
    }

    public BioSection(LifeStage ... stages) {
        this(Arrays.asList(stages));
    }

    public BioSection(List<LifeStage> stages) {
        this.stages = stages;
        this.stages.sort((o1, o2) -> { return o1.getOrganization().getTitle().compareTo(o2.getOrganization().getTitle()); });
    }

    @Override
    public List<LifeStage> getData() {
        return stages;
    }

    @Override
    public int size() {
        return stages.size();
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
