package com.example.networking.model;

import android.support.annotation.NonNull;

import java.util.List;

public class Project {

    @NonNull
    private String name;
    @NonNull
    private String license;
    @NonNull
    private String description;
    @NonNull
    private String imageUrl;
    @NonNull
    private List<Dependency> dependencies;

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getLicense() {
        return license;
    }

    public void setLicense(@NonNull String license) {
        this.license = license;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    @NonNull
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(@NonNull String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @NonNull
    public List<Dependency> getDependencies() {
        return dependencies;
    }

    public void setDependencies(@NonNull List<Dependency> dependencies) {
        this.dependencies = dependencies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Project project = (Project) o;

        if (!name.equals(project.name)) return false;
        if (!license.equals(project.license)) return false;
        if (!description.equals(project.description)) return false;
        if (!imageUrl.equals(project.imageUrl)) return false;
        return dependencies.equals(project.dependencies);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + license.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + imageUrl.hashCode();
        result = 31 * result + dependencies.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Project{" +
                "name='" + name + '\'' +
                ", license='" + license + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", dependencies=" + dependencies +
                '}';
    }
}
