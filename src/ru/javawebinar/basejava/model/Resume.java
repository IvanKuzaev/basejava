package ru.javawebinar.basejava.model;

import java.util.*;

public class Resume implements Comparable<Resume>, Cloneable {

    // Unique identifier
    private final String uuid;

    private String fullName;

    private ResumeDataString mobilePhone = new ResumeDataString("Phone", null);
    private String EMail;
    private String homePage;
    private String skype;
    private ResumeData<ResumeDataString> socialMedia = new ResumeData<>("Social media", true);

    public ResumeData[] sections = new ResumeData[Sections.values().length];

    @Override
    public int compareTo(Resume o) {
        int cName = fullName.compareTo(o.fullName);
        return cName != 0 ? cName : uuid.compareTo(o.uuid);
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return this.uuid;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        boolean a = true;
        a &= uuid.equals(resume.uuid);
        a &= fullName.equals(resume.getFullName());
        a &= mobilePhone.equals(resume.mobilePhone);
        a &= EMail.equals(resume.EMail);
        a &= homePage.equals(resume.homePage);
        a &= skype.equals(resume.skype);
        a &= socialMedia.equals(resume.socialMedia);
        for (int i = 0; i < sections.length; i++) {
            a &= sections[i].equals(resume.sections[i]);
        }
        return a;
    }

    @Override
    public int hashCode() {
        int a;
        a = uuid.hashCode();
        a = 31 * a + fullName.hashCode();
        a = 31 * a + mobilePhone.hashCode();
        a = 31 * a + EMail.hashCode();
        a = 31 * a + homePage.hashCode();
        a = 31 * a + skype.hashCode();
        a = 31 * a + socialMedia.hashCode();
        for (int i = 0; i < sections.length; i++) {
            a = 31 * a + sections[i].hashCode();
        }
        return a;
    }

    @Override
    public String toString() {
        String a = "";
        a += getFullName() + "\n";
        a += "phone number: " + getMobilePhone() + "\n";
        a += "eMail: " + getEMail() + "\n";
        a += "home page: " + getHomePage() + "\n";
        a += "skype: " + getSkype() + "\n";
        for (int i = 0; i < socialMedia.size(); i++) {
            ResumeDataString element = socialMedia.getElement(i);
            a += element.getTitle() + " : " + element.getElement(i) + "\n";
        }
        a += "\n";
        for (int i = 0; i < sections.length; i++) {
            a += sections[i].toString() + "\n\n";
        }
        return a;
    }

    public String getMobilePhone() {
        return mobilePhone.getElement();
    }

    public String getMobilePhone(int i) {
        return mobilePhone.getElement(i);
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone.setElement(mobilePhone);
    }

    public void setMobilePhone(int i, String mobilePhone) {
        this.mobilePhone.setElement(i, mobilePhone);
    }

    public String getEMail() {
        return EMail;
    }

    public void setEMail(String EMail) {
        this.EMail = EMail;
    }

    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public String getSocialMedia(String media) {
        for (int i = 0; i < socialMedia.size(); i++) {
            ResumeDataString element = socialMedia.getElement(i);
            if (element.getTitle().compareTo(media) == 0) {
                return element.getElement();
            }
        }
        return null;
    }

    public void setSocialMedia(String media, String profile) {
        for (int i = 0; i < socialMedia.size(); i++) {
            ResumeDataString element = socialMedia.getElement(i);
            if (element.getTitle().compareTo(media) == 0) {
                element.setElement(profile);
                socialMedia.setElement(i, element);
                return;
            }
        }
        socialMedia.setElement(new ResumeDataString(media, profile));
    }

}
