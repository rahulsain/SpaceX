package com.rahuls.spacex;

public class Crew {
    int id;
    String name , agency , image , wikipedia , status;

    public Crew(int id, String name, String agency, String image, String wikipedia, String status) {
        this.id = id;
        this.name = name;
        this.agency = agency;
        this.image = image;
        this.wikipedia = wikipedia;
        this.status = status;
    }

    public Crew() {
        this.id = 0;
        this.name = "";
        this.agency = "";
        this.image = "";
        this.wikipedia = "";
        this.status = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getWikipedia() {
        return  wikipedia;
    }

    public void setWikipedia(String wikipedia) {
        this. wikipedia = wikipedia;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
