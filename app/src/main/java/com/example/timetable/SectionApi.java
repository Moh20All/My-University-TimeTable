package com.example.timetable;

public class SectionApi {
    private int sectionId;
    private String sectionName;
    private int levelId;
    private int capacity;
    private String abbreviationFr;
    private String abbreviationAr;
    private int active;
    private int idYear;

    public SectionApi(int sectionId, String sectionName, int levelId, int capacity, String abbreviationFr, String abbreviationAr, int active,int idYear) {
        this.sectionId = sectionId;
        this.sectionName = sectionName;
        this.levelId = levelId;
        this.capacity = capacity;
        this.abbreviationFr = abbreviationFr;
        this.abbreviationAr = abbreviationAr;
        this.active = active;
        this.idYear=idYear;
    }

    public int getIdYear() {
        return idYear;
    }

    public int getSectionId() {
        return sectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public int getLevelId() {
        return levelId;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getAbbreviationFr() {
        return abbreviationFr;
    }

    public String getAbbreviationAr() {
        return abbreviationAr;
    }

    public int getActive() {
        return active;
    }

    @Override
    public String toString() {
        return "SectionApi{" +
                "sectionId=" + sectionId +
                ", sectionName='" + sectionName + '\'' +
                ", levelId=" + levelId +
                ", capacity=" + capacity +
                ", abbreviationFr='" + abbreviationFr + '\'' +
                ", abbreviationAr='" + abbreviationAr + '\'' +
                ", active=" + active +
                '}';
    }
}
