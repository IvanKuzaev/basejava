package ru.javawebinar.basejava.util;

import ru.javawebinar.basejava.model.Contacts;
import java.time.LocalDate;

public class htmlUtil {

    public static String contactToHTML(Contacts contact, String value) {
        if (value != null) {
            switch (contact) {
                case SKYPE:
                    return "<a href='skype:" + value + "'>" + value + "</a>";
                case EMAIL:
                    return "<a href='mailto:" + value + "'>" + value + "</a>";
            }
            return value;
        } else {
            return "";
        }
    }

    public static String briefDate(LocalDate ld) {
        int m = ld.getMonthValue();
        int y = ld.getYear();
        if (y < 9999) {
            return (m < 10 ? "0" : "") + m + "/" + y;
        } else {
            return "сейчас";
        }
    }

}
