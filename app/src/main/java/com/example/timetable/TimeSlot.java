package com.example.timetable;

public class TimeSlot {
    private int slotNumber;
    private String startTime;
    private String endTime;

    public TimeSlot(int slotNumber, String startTime, String endTime) {
        this.slotNumber = slotNumber;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public TimeSlot() {
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return getStartTime()+"-"+getEndTime();
    }
}
