package ru.javawebinar.basejava.model;

public class DateInterval {
    Months startMonth;
    int startYear;
    Months endMonth;
    int endYear;

    public DateInterval(int startMonth, int startYear, int endMonth, int endYear) {
        if (startMonth == 0) {
            startMonth = 1;
        }
        if (endMonth == 0) {
            endMonth = 1;
        }
        this.startMonth = Months.values()[startMonth - 1];
        this.startYear = startYear;
        this.endMonth = Months.values()[endMonth - 1];
        this.endYear = endYear;
    }

    public DateInterval(Months startMonth, int startYear, Months endMonth, int endYear) {
        this.startMonth = startMonth;
        this.startYear = startYear;
        this.endMonth = endMonth;
        this.endYear = endYear;
    }

    @Override
    public boolean equals(Object object) {
        DateInterval dateInterval = (DateInterval)object;
        boolean a = true;
        a &= startMonth.equals(dateInterval.startMonth);
        a &= startYear == dateInterval.startYear;
        a &= endMonth.equals(dateInterval.endMonth);
        a &= endYear == dateInterval.endYear;
        return a;
    }

    @Override
    public int hashCode() {
        int a;
        a = startMonth.hashCode();
        a = 31 * a + 17 * startYear;
        a = 31 * a + endMonth.hashCode();;
        a = 31 * a + 64 * endYear;
        return a;
    }

    @Override
    public String toString() {
        String end;
        if (endYear == 0) {
            end = "сейчас";
        } else {
            end = endMonth.MM + "/" + endYear;
        }
        return "" + startMonth.MM + "/" + startYear + " - " + end;
    }
}
