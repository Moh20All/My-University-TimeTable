package com.example.timetable;
public class TimetableEntry {
    private String dataType;
    private String location;
    private String courseType;
    private String studyLevel;
    private String field;
    private String professorLastName;
    private String professorFirstName;
    private String unknownField;
    private String moduleName;
    private String dayOfWeek;
    private String timeSlot;
    private String subGroup;
    private boolean online;
    private boolean biweekly;
    private String onlineLink;
    private String locationGPS;

    public TimetableEntry(String dataType, String location, String courseType, String studyLevel, String field, String professorLastName, String professorFirstName, String unknownField, String moduleName, String dayOfWeek, String timeSlot, String subGroup, boolean online, boolean biweekly, String onlineLink, String locationGPS) {
        this.dataType = dataType;
        this.location = location;
        this.courseType = courseType;
        this.studyLevel = studyLevel;
        this.field = field;
        this.professorLastName = professorLastName;
        this.professorFirstName = professorFirstName;
        this.unknownField = unknownField;
        this.moduleName = moduleName;
        this.dayOfWeek = dayOfWeek;
        this.timeSlot = timeSlot;
        this.subGroup = subGroup;
        this.online = online;
        this.biweekly = biweekly;
        this.onlineLink = onlineLink;
        this.locationGPS = locationGPS;
    }

    // Getters and Setters
    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getStudyLevel() {
        return studyLevel;
    }

    public void setStudyLevel(String studyLevel) {
        this.studyLevel = studyLevel;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getProfessorLastName() {
        return professorLastName;
    }

    public void setProfessorLastName(String professorLastName) {
        this.professorLastName = professorLastName;
    }

    public String getProfessorFirstName() {
        return professorFirstName;
    }

    public void setProfessorFirstName(String professorFirstName) {
        this.professorFirstName = professorFirstName;
    }

    public String getUnknownField() {
        return unknownField;
    }

    public void setUnknownField(String unknownField) {
        this.unknownField = unknownField;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getSubGroup() {
        return subGroup;
    }

    public void setSubGroup(String subGroup) {
        this.subGroup = subGroup;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public boolean isBiweekly() {
        return biweekly;
    }

    public void setBiweekly(boolean biweekly) {
        this.biweekly = biweekly;
    }

    public String getOnlineLink() {
        return onlineLink;
    }

    public void setOnlineLink(String onlineLink) {
        this.onlineLink = onlineLink;
    }

    public String getLocationGPS() {
        return locationGPS;
    }

    public void setLocationGPS(String locationGPS) {
        this.locationGPS = locationGPS;
    }

    @Override
    public String toString() {
        return "TimetableEntry{" +
                "dataType='" + dataType + '\'' +
                ", location='" + location + '\'' +
                ", courseType='" + courseType + '\'' +
                ", studyLevel='" + studyLevel + '\'' +
                ", field='" + field + '\'' +
                ", professorLastName='" + professorLastName + '\'' +
                ", professorFirstName='" + professorFirstName + '\'' +
                ", unknownField='" + unknownField + '\'' +
                ", moduleName='" + moduleName + '\'' +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                ", timeSlot='" + timeSlot + '\'' +
                ", subGroup='" + subGroup + '\'' +
                ", online=" + online +
                ", biweekly=" + biweekly +
                ", onlineLink='" + onlineLink + '\'' +
                ", locationGPS='" + locationGPS + '\'' +
                '}';
    }
}
