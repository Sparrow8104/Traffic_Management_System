package com.smarttraffic.traffic_management_system.entity;

public class SignalEvent {

    private int signalId;
    private int laneId;
    private int queueLength;
    private int vehicleCount;
    private int possibleTime;
    private boolean emergencyVehicle;

    public SignalEvent(int signalId, int laneId, int queueLength, int vehicleCount, int possibleTime, boolean emergencyVehicle) {
        this.signalId = signalId;
        this.laneId = laneId;
        this.queueLength = queueLength;
        this.vehicleCount = vehicleCount;
        this.possibleTime = possibleTime;
        this.emergencyVehicle = emergencyVehicle;
    }

    public int getSignalId() {
        return signalId;
    }

    public void setSignalId(int signalId) {
        this.signalId = signalId;
    }

    public int getLaneId() {
        return laneId;
    }

    public void setLaneId(int laneId) {
        this.laneId = laneId;
    }

    public int getQueueLength() {
        return queueLength;
    }

    public void setQueueLength(int queueLength) {
        this.queueLength = queueLength;
    }

    public int getVehicleCount() {
        return vehicleCount;
    }

    public void setVehicleCount(int vehicleCount) {
        this.vehicleCount = vehicleCount;
    }

    public int getPossibleTime() {
        return possibleTime;
    }

    public void setPossibleTime(int possibleTime) {
        this.possibleTime = possibleTime;
    }

    public boolean isEmergencyVehicle() {
        return emergencyVehicle;
    }

    public void setEmergencyVehicle(boolean emergencyVehicle) {
        this.emergencyVehicle = emergencyVehicle;
    }

    @Override
    public String toString() {
        return "SignalEvent{" +
                "signalId=" + signalId +
                ", laneId=" + laneId +
                ", queueLength=" + queueLength +
                ", vehicleCount=" + vehicleCount +
                ", possibleTime=" + possibleTime +
                ", emergencyVehicle=" + emergencyVehicle +
                '}';
    }
}
