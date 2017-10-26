package com.example.networking.model;

import android.support.annotation.NonNull;

public class Dependency {

    @NonNull
    private String group;
    @NonNull
    private String name;
    @NonNull
    private String version;

    @NonNull
    public String getGroup() {
        return group;
    }

    public void setGroup(@NonNull String group) {
        this.group = group;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getVersion() {
        return version;
    }

    public void setVersion(@NonNull String version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dependency that = (Dependency) o;

        if (!group.equals(that.group)) return false;
        if (!name.equals(that.name)) return false;
        return version.equals(that.version);
    }

    @Override
    public int hashCode() {
        int result = group.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + version.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Dependency{" +
                "group='" + group + '\'' +
                ", name='" + name + '\'' +
                ", version='" + version + '\'' +
                '}';
    }

}
