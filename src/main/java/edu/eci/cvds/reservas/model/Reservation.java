package edu.eci.cvds.reservas.model;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reservations")
public class Reservation {
    @Id
    private String idReservation;
    private String laboratory;
    private LocalDateTime dateHour;
    private String user;
    private String className;
    private LocalDateTime creationDate;
    private int priority; // Lab 5

    public Reservation() {
    }

    public Reservation(String laboratory, String idReservation, LocalDateTime dateHour, String user, String className, LocalDateTime creationDate, int priority) {
        this.laboratory = laboratory;
        this.idReservation = idReservation;
        this.dateHour = dateHour;
        this.user = user;
        this.className = className;
        this.creationDate = creationDate;
        this.priority = priority;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public String getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(String idReservation) {
        this.idReservation = idReservation;
    }

    public String getLaboratory() {
        return laboratory;
    }

    public void setLaboratory(String laboratory) {
        this.laboratory = laboratory;
    }

    public LocalDateTime getDateHour() {
        return dateHour;
    }

    public void setDateHour(LocalDateTime dateHour) {
        this.dateHour = dateHour;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getId(){
        return idReservation;
    }

    public void setId(String id){
        this.idReservation = id;
    }

    public int getPriority() { return priority; }

    public void setPriority(int priority) { this.priority = priority; }

    @Override
    public String toString() {
        return "Reservation [id=" + idReservation + ", laboratory=" + laboratory + ", userName=" + user + ", date="
                + dateHour + ", Class=" + className + "]";
    }
}
