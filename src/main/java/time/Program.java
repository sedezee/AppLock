package time;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Data class to hold information about running programs.
 *
 * @author Holland Louis Delrin
 */
public class Program implements Serializable {
    private static final long serialVersionUID = 0;
    private final String name;
    private String directory;
    private Schedule schedule;

    /**
     * Instantiates program, filling out necessary data fields
     * @param name name of program
     * @param directory directory program exists in
     * @param schedule the schedule associated with when the program should be closed
     * @throws FileNotFoundException thrown if file does not exist
     */
    public Program(String name, String directory, Schedule schedule) throws FileNotFoundException {
        this.name = name;
        this.directory = directory;
        File f = new File(directory);
        if (!f.exists()) {
            throw new FileNotFoundException();
        }
        this.schedule = schedule;
    }

    /**
     * Instantiates program, filling out necessary data fields
     * @param file file location of program
     * @param schedule the schedule associated with when the program should be closed
     * @throws FileNotFoundException thrown if file does not exist
     */
    public Program(File file, Schedule schedule) throws FileNotFoundException {
        if (!file.exists()) {
            throw new FileNotFoundException();
        }
        this.schedule = schedule;
        this.name = file.getName();
        this.directory = file.getAbsolutePath();
    }

    /**
     * @return name of the file
     */
    public String getName() {
        return name;
    }

    /**
     * @return location of the file
     */
    public String getDirectory() {
        return directory;
    }

    /**
     * @return raw schedule object
     */
    public Schedule getSchedule() {
        return schedule;
    }

    /**
     * Inteded for use if a file location has been changed
     * @param directory file path to update program to
     */
    public void setDirectory(String directory) {
        this.directory = directory;
    }

    /**
     * Intended for use when the time the program should be closed has been changed
     * @param schedule schedule to update program to
     */
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
}