package src.time;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.Arrays;


public class Schedule implements Serializable {
    private static final long serialVersionUID = 0; 
    private Pair<LocalTime, LocalTime>[] monday; 
    private Pair<LocalTime, LocalTime>[] tuesday;
    private Pair<LocalTime, LocalTime>[] wednesday;
    private Pair<LocalTime, LocalTime>[] thursday; 
    private Pair<LocalTime, LocalTime>[] friday; 
    private Pair<LocalTime, LocalTime>[] saturday; 
    private Pair<LocalTime, LocalTime>[] sunday; 

    public Schedule() {
        monday = new Pair[] {null}; 
        tuesday = new Pair[] {null}; 
        wednesday = new Pair[] {null}; 
        thursday = new Pair[] {null}; 
        friday = new Pair[] {null}; 
        saturday = new Pair[] {null}; 
        sunday = new Pair[] {null}; 
    }

    public void setWeekdays(Pair<LocalTime, LocalTime>[] schedule) {
        monday = schedule; 
        tuesday = schedule; 
        wednesday = schedule; 
        thursday = schedule; 
        friday = schedule; 
    }

    public void setAllDays(Pair<LocalTime, LocalTime>[] schedule) {
        this.setWeekdays(schedule); 
        saturday = schedule; 
        sunday = schedule; 
    }

    public void setDay(String day, Pair<LocalTime, LocalTime>[] schedule) {
        switch (day.toUpperCase()) {
            case "MONDAY": 
                monday = schedule; 
                break; 
            case "TUESDAY": 
                tuesday = schedule; 
                break; 
            case "WEDNESDAY": 
                wednesday = schedule; 
                break; 
            case "THURSDAY": 
                thursday = schedule; 
                break; 
            case "FRIDAY": 
                friday = schedule; 
                break; 
            case "SATURDAY": 
                saturday = schedule; 
                break; 
            case "SUNDAY": 
                sunday = schedule; 
                break; 
            default: 
                break; 
        }
    }

    public int hashCode() {
        final int prime = 31; 
        return prime + Arrays.hashCode(monday) * Arrays.hashCode(tuesday) * Arrays.hashCode(wednesday) * Arrays.hashCode(thursday) * Arrays.hashCode(friday) * Arrays.hashCode(saturday) * Arrays.hashCode(sunday);  
    }

    public boolean equals(Object other) {
        if (!(other instanceof Schedule)) 
            return false; 

        Schedule oSchedule = (Schedule) other; 
        return Arrays.equals(monday, oSchedule.monday) && 
            Arrays.equals(tuesday, oSchedule.tuesday) &&
            Arrays.equals(wednesday, oSchedule.wednesday) &&
            Arrays.equals(thursday, oSchedule.thursday) &&
            Arrays.equals(friday, oSchedule.friday) &&
            Arrays.equals(saturday, oSchedule.saturday) &&
            Arrays.equals(sunday, oSchedule.sunday); 
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }   

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject(); 
    }

    private void readObjectNoData() throws ObjectStreamException {
        throw new InvalidObjectException("Stream Data Required"); 
    }
}