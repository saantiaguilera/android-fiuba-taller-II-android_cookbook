package com.saantiaguilera.models;

import java.util.List;

/**
 * Model of a seller with a list of favorites
 * - Getters / Setters / Hashcode / Equals were generated with intellij
 */
public class Seller extends User {

    private List<Product> favorites;

    public List<Product> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Product> favorites) {
        this.favorites = favorites;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Seller seller = (Seller) o;

        return favorites != null ? favorites.equals(seller.favorites) : seller.favorites == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (favorites != null ? favorites.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Seller{" +
                "favorites=" + favorites +
                "}\n" + super.toString();
    }
}
