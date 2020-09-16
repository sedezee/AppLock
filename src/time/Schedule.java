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
        for (Pair<LocalTime, LocalTime> pair : schedule) {
            System.out.println(pair); 
        }
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
        System.out.println(day); 
        for (Pair<LocalTime, LocalTime> pair : schedule) {
            System.out.println(pair); 
        }
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

    public Pair<LocalTime, LocalTime>[] getDay(String day) {
        switch (day.toUpperCase()) {
            case "MONDAY": 
                return monday; 
            case "TUESDAY": 
                return tuesday;  
            case "WEDNESDAY": 
                return wednesday; 
            case "THURSDAY": 
                return thursday; 
            case "FRIDAY": 
                return friday; 
            case "SATURDAY": 
                return saturday; 
            case "SUNDAY": 
                return sunday; 
            default: 
                return null; 
        }
    }

    private boolean checkTime(Pair[] arr, LocalTime localTime) {
        for (Pair<LocalTime, LocalTime> pair : arr) {
            LocalTime start = pair.left().isBefore(pair.right()) ? pair.left() : pair.right(); 
            LocalTime end = pair.right().isAfter(pair.left()) ? pair.right() : pair.left(); 

            if ((localTime.isAfter(start) && localTime.isBefore(end)) || localTime.equals(start) || localTime.equals(end)) {
                return true; 
            }
        } 
        return false; 
    }

    public static LocalTime parseTime(String time) {
        String[] timeArr = time.split(":");
        int hour = Integer.parseInt(timeArr[0]);
        hour = hour == 12 ? 0 : hour; 
        int minute = timeArr[1].length() == 4 ? Integer.parseInt(timeArr[1].substring(0, 2)) : Integer.parseInt(timeArr[1]); 
        if (timeArr[1].length() == 4 && timeArr[1].substring(2, 4).equalsIgnoreCase("PM")) {
            hour += 12;
        }

        return LocalTime.of(hour, minute);
    }

    public boolean inTime(String day, LocalTime localTime) {
        switch (day.toUpperCase()) {
            case "MONDAY": 
                return checkTime(monday, localTime); 
            case "TUESDAY": 
                return checkTime(tuesday, localTime);  
            case "WEDNESDAY": 
                return checkTime(wednesday, localTime); 
            case "THURSDAY": 
              return checkTime(thursday, localTime); 
            case "FRIDAY": 
                return checkTime(friday, localTime); 
            case "SATURDAY": 
                return checkTime(saturday, localTime); 
            case "SUNDAY": 
                return checkTime(sunday, localTime); 
            default: 
                return false; 
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