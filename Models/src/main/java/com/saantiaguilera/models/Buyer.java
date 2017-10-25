package com.saantiaguilera.models;

import java.util.List;

/**
 * Buyer class with a list of products that he has available for selling
 * - Getters / Setters / Hashcode / Equals were generated with intellij
 */
public class Buyer extends User {

    private List<Product> catalog;

    public List<Product> getCatalog() {
        return catalog;
    }

    public void setCatalog(List<Product> catalog) {
        this.catalog = catalog;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Buyer buyer = (Buyer) o;

        return catalog != null ? catalog.equals(buyer.catalog) : buyer.catalog == null;
    }

    @Override
    public int hashCode() {
        return catalog != null ? catalog.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Buyer{" +
                "catalog=" + catalog +
                "}\n" + super.toString();
    }
}
