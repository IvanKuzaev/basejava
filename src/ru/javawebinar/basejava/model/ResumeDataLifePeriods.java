package ru.javawebinar.basejava.model;

public class ResumeDataLifePeriods extends ResumeData<ResumeDataDate> {
    private String titelLink;

    public ResumeDataLifePeriods(String title, String titelLink, ResumeDataDate ... es) throws Throwable {
        super(title, true, es);
        this.titelLink = titelLink;
    }

    public ResumeDataLifePeriods(String title, ResumeDataDate ... es) throws Throwable {
        this(title, null, es);
    }

    @Override
    public String toString() {
        String a = super.toString();
        a += "\n";
        return a;
    }
}
