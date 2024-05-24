package com.example.timetable;

public class Faculty {
    private int id;
    private String nameFac;
    private String nameFacAr;


    // Constructor
    public Faculty(int id, String nameFac, String nameFacAr) {
        this.id = id;
        this.nameFac = nameFac;
        this.nameFacAr = nameFacAr;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameFac() {
        return nameFac;
    }

    public void setNameFac(String nameFac) {
        this.nameFac = nameFac;
    }

    public String getNameFacAr() {
        return nameFacAr;
    }

    public void setNameFacAr(String nameFacAr) {
        this.nameFacAr = nameFacAr;
    }



    // toString() method for logging or debugging
    @Override
    public String toString() {
        return "Faculty{" +
                "id=" + id +
                ", nameFac='" + nameFac + '\'' +
                ", nameFacAr='" + nameFacAr + '\'' ;
    }
}
