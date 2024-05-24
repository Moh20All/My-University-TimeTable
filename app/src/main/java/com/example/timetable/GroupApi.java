package com.example.timetable;

public class GroupApi {
    private int groupId;
    private String groupName;
    private int sectionId;
    private String abbreviationFr;
    private String abbreviationAr;
    private String capacity;
    private String active;

    public GroupApi(int groupId, String groupName, int sectionId, String abbreviationFr, String abbreviationAr, String capacity, String active) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.sectionId = sectionId;
        this.abbreviationFr = abbreviationFr;
        this.abbreviationAr = abbreviationAr;
        this.capacity = capacity;
        this.active = active;
    }

    public int getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public int getSectionId() {
        return sectionId;
    }

    public String getAbbreviationFr() {
        return abbreviationFr;
    }

    public String getAbbreviationAr() {
        return abbreviationAr;
    }

    public String getCapacity() {
        return capacity;
    }

    public String getActive() {
        return active;
    }

    @Override
    public String toString() {
        return "GroupApi{" +
                "groupId='" + groupId + '\'' +
                ", groupName='" + groupName + '\'' +
                ", sectionId='" + sectionId + '\'' +
                ", abbreviationFr='" + abbreviationFr + '\'' +
                ", abbreviationAr='" + abbreviationAr + '\'' +
                ", capacity='" + capacity + '\'' +
                ", active='" + active + '\'' +
                '}';
    }
}
