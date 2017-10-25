package com.saantiaguilera.mvp.model;

import java.util.List;

/**
 * We are creating a normal model with its values.
 * - The class is final because we dont allow inheritance of a product
 * - The transient field makes our JSON parser skip it for parsing (apart from being a transient field)
 * - The getters and setters / equals and hashcode were generated with intellij
 */
public final class Product {

    private transient int innerSchemaId;

    private long id;
    private double price;
    private String name;
    private List<String> tags;

    public int getInnerSchemaId() {
        return innerSchemaId;
    }

    public void setInnerSchemaId(int innerSchemaId) {
        this.innerSchemaId = innerSchemaId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (innerSchemaId != product.innerSchemaId) return false;
        if (id != product.id) return false;
        if (Double.compare(product.price, price) != 0) return false;
        if (name != null ? !name.equals(product.name) : product.name != null) return false;
        return tags != null ? tags.equals(product.tags) : product.tags == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = innerSchemaId;
        result = 31 * result + (int) (id ^ (id >>> 32));
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        return result;
    }

}
