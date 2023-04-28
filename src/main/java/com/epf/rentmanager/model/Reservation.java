package com.epf.rentmanager.model;

import java.time.LocalDate;



public class Reservation {
    private long id;
    private long clientId;
    private long vehicleId;
    private LocalDate start;
    private LocalDate end;

    public Reservation(long pid, long clientId, long vehicleId, LocalDate start, LocalDate end) {
        this.id = pid;
        this.clientId = clientId;
        this.vehicleId = vehicleId;
        this.start = start;
        this.end= end;

    }

    public Reservation(long clientId, long vehicleId, LocalDate start, LocalDate end) {
        this.clientId = clientId;
        this.vehicleId = vehicleId;
        this.start = start;
        this.end= end;
    }

    public long getId() {
        return this.id;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", vehicleId=" + vehicleId +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}