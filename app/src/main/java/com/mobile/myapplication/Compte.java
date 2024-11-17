package com.mobile.myapplication;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.Date;
public class Compte {

    private Long id;
    private double solde;
    private String dateCreation;
    private String type;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
