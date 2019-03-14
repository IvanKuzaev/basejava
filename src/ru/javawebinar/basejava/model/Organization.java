package ru.javawebinar.basejava.model;

import java.io.Serializable;
import java.util.Objects;

public class Organization implements Serializable {
    static final long serialVersionUID = 127L;
    private String title;
    private String description;
    private String webLink;

    public Organization(String title, String description, String webLink) {
        this.title = title;
        this.description = description;
        this.webLink = webLink;
    }

    public Organization(String title, String webLink) {
        this(title, null, webLink);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebLink() {
        return webLink;
    }

    public void setWebLink(String webLink) {
        this.webLink = webLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(description, that.description) &&
                Objects.equals(webLink, that.webLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, webLink);
    }
}
