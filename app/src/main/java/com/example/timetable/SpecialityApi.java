package com.example.timetable;

public class SpecialityApi {
    private int idSpeciality;
    private int departmentId;
    private String nameSpec;
    private String nameSpecAr;

    public SpecialityApi(int idSpeciality, int departmentId, String nameSpec, String nameSpecAr) {
        this.idSpeciality = idSpeciality;
        this.departmentId = departmentId;
        this.nameSpec = nameSpec;
        this.nameSpecAr = nameSpecAr;
    }

    public int getIdSpeciality() {
        return idSpeciality;
    }

    public void setIdSpeciality(int idSpeciality) {
        this.idSpeciality = idSpeciality;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getNameSpec() {
        return nameSpec;
    }

    public void setNameSpec(String nameSpec) {
        this.nameSpec = nameSpec;
    }

    public String getNameSpecAr() {
        return nameSpecAr;
    }

    public void setNameSpecAr(String nameSpecAr) {
        this.nameSpecAr = nameSpecAr;
    }

    @Override
    public String toString() {
        return "SpecialityApi{" +
                "idSpeciality=" + idSpeciality +
                ", departmentId=" + departmentId +
                ", nameSpec='" + nameSpec + '\'' +
                ", nameSpecAr='" + nameSpecAr + '\'' +
                '}';
    }
}
