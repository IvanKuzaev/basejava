package ru.javawebinar.basejava.model;

public class ResumeDataDate extends ResumeData<String> {
    private DateInterval dateInterval;

    public DateInterval getDateInterval() {
        return dateInterval;
    }

    public void setDateInterval(DateInterval dateInterval) {
        this.dateInterval = dateInterval;
    }

    public ResumeDataDate(String title, String element, DateInterval dateInterval) throws Throwable {
        super(title, false, element);
        this.dateInterval = dateInterval;
    }

    @Override
    public String toString() {
        String a = "";
        a += dateInterval;
        a += " " + title;
        if (size() > 0) {
            a += "\n\t" + getElement();
        }
        return a;
    }

    @Override
    public int hashCode() {
        return dateInterval.hashCode() ^ super.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        ResumeDataDate resumeDataDate = (ResumeDataDate)object;
        return dateInterval.equals(resumeDataDate.dateInterval) & super.equals(object);
    }
}
