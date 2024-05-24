package com.example.timetable;

public class savings {
    String name;
    int secID;
    int GroupID;
    int specID;

    public int getLvlID() {
        return lvlID;
    }

    public int getSpecID() {
        return specID;
    }

    public savings(String name, int secID, int groupID, int specID, int lvlID) {
        this.name = name;
        this.secID = secID;
        GroupID = groupID;
        this.specID = specID;
        this.lvlID = lvlID;
    }

    public savings(String name, int secID, int groupID) {
        this.name = name;
        this.secID = secID;
        GroupID = groupID;
    }

    int lvlID;

    public savings(String name, int secID, int groupID, int lvlID) {
        this.name = name;
        this.secID = secID;
        GroupID = groupID;
        this.lvlID = lvlID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSecID() {
        return secID;
    }

    public void setSecID(int secID) {
        this.secID = secID;
    }

    public int getGroupID() {
        return GroupID;
    }

    public void setGroupID(int groupID) {
        GroupID = groupID;
    }
}
