package com.example.timetable;

public class DepartmentApi {
    private int id;
    private String nameFr;
    private String nameAr;

    public DepartmentApi(int id, String nameFr, String nameAr) {
        this.id = id;
        this.nameFr = nameFr;
        this.nameAr = nameAr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameFr() {
        return nameFr;
    }

    public void setNameFr(String nameFr) {
        this.nameFr = nameFr;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", nameFr='" + nameFr + '\'' +
                ", nameAr='" + nameAr + '\'' +
                '}';
    }
}

