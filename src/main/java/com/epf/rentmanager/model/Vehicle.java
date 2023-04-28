package com.epf.rentmanager.model;

public class Vehicle {

    private long id;

    private String modele;
    private String constructor;
    private int seats;

    public Vehicle(long id, String modele, String constructor, int seats) {
        this.id = id;
        this.modele = modele;
        this.constructor = constructor;
        this.seats = seats;
    }

    public Vehicle(String modele, String constructor, int seats) {
        this.modele = modele;
        this.constructor = constructor;
        this.seats = seats;
    }

    public String getModele() {
        return modele;
    }

    public String getConstructor() {
        return constructor;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public void setConstructor(String constructor) {
        this.constructor = constructor;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public long getId(long id) {
        return this.id;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", modele='" + modele + '\'' +
                ", constructor='" + constructor + '\'' +
                ", seats=" + seats +
                '}';
    }
}
