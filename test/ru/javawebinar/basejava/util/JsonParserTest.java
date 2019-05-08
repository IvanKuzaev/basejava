package ru.javawebinar.basejava.util;

import org.junit.Assert;
import org.junit.Test;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.StringListSection;
import ru.javawebinar.basejava.model.StringSection;

import static ru.javawebinar.basejava.ResumeTestData.fillDummyResume;

public class JsonParserTest /* extends AbstractStorageTest */ {

    @Test
    public void testResume() {
        Resume resumeBefore = new Resume("John Smith", "uuid00");
        fillDummyResume(resumeBefore, 0);
        String resumeBeforeJSON = JsonParser.write(resumeBefore);
        Resume resumeAfter = JsonParser.read(resumeBeforeJSON, Resume.class);
        Assert.assertEquals(resumeBefore, resumeAfter);
    }

    @Test
    public void testStringSection() {
        StringSection stringSectionBefore = new StringSection("This is a StringSection of a resume.");
        String stringSectionBeforeJSON = JsonParser.write(stringSectionBefore);
        StringSection stringSectionAfter = JsonParser.read(stringSectionBeforeJSON, StringSection.class);
        Assert.assertEquals(stringSectionBefore, stringSectionAfter);
    }

    @Test
    public void testStringListSection() {
        StringListSection stringListSectionBefore = new StringListSection("string1", "string2", "string3");
        String stringListSectionBeforeJSON = JsonParser.write(stringListSectionBefore);
        StringListSection stringListSectionAfter = JsonParser.read(stringListSectionBeforeJSON, StringListSection.class);
        Assert.assertEquals(stringListSectionBefore, stringListSectionAfter);
    }

}