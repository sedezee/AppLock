package src.time;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.time.LocalTime;

public class Program implements Serializable {
    private static final long serialVersionUID = 0; 
    private final String name; 
    private String directory;
    private Schedule schedule; 

    public Program(String name, String directory, Schedule schedule) throws FileNotFoundException {
        this.name = name; 
        this.directory = directory; 
        File f = new File(directory); 
        if (!f.exists()) {
            throw new FileNotFoundException();
        }
        this.schedule = schedule; 
    }  

    public Program(File file, Schedule schedule) throws FileNotFoundException {        
        if (!file.exists()) {
            throw new FileNotFoundException(); 
        }
        this.schedule = schedule; 
        this.name = file.getName(); 
        this.directory = file.getAbsolutePath();  
    }

    public String getName() {
        return name; 
    }

    public String getDirectory() {
        return directory; 
    }

    public Schedule getSchedule() {
        return schedule; 
    }
    
    public void setDirectory(String directory) {
        this.directory = directory; 
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule; 
    }

    public int hashCode() {
        final int prime = 31; 
        return prime + name.hashCode() * directory.hashCode() * schedule.hashCode(); 
    }

    public boolean equals(Object other) { 
        if (!(other instanceof Program))
            return false; 

        Program oProgram = (Program) other; 
        
        return name.equals(oProgram.name) && 
        directory.equals(oProgram.directory) &&
        schedule.equals(oProgram.schedule); 
    }

    public String toString() {
        return name + " " + directory + " " + schedule.toString(); 
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