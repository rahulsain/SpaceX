package com.rahuls.spacex;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "task")
public class Member implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name , agency , image , wikipedia , status;

    public int getId() { return id; }

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
