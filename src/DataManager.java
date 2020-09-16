package src;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import src.time.Program;

public class DataManager implements Serializable {
    private static final long serialVersionUID = 0;
    private File file;
    private List<Program> programs;
    private Settings settings;
    private transient Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public static final transient String SAVE_NAME = "SaveFile.txt";

    /* Both of the DataManager constructors are designed to 
    automatically load in the specified save file. The DataManager
    class is not filled with static fields because different save files 
    can exist at different locations on the computer. 
    */ 
    public DataManager() {
        String directory = System.getProperty("user.home") + "\\Documents\\AppLock\\";
        file = new File(directory);
        if (!file.mkdirs() && (file = new File(directory + SAVE_NAME)).exists()) {
            try {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                DataManager dataManager = (DataManager) ois.readObject();
                ois.close();
                this.file = dataManager.file;
                this.programs = dataManager.programs;
                this.settings = dataManager.settings;
            } catch (IOException | ClassNotFoundException e) {
                logger.log(Level.WARNING, e.toString());
                this.setDefaults(); 
            }
        } else {
            this.setDefaults();
        }
    }

    public DataManager(File file) {
        file = new File(file.getAbsolutePath() + "\\SaveFile.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                logger.log(Level.WARNING, e.toString());
            }
        } else { 
            try {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                DataManager dataManager = (DataManager) ois.readObject();
                ois.close();
                this.file = dataManager.file;
                this.programs = dataManager.programs;
                this.settings = dataManager.settings;
            } catch (IOException | ClassNotFoundException e) {
                logger.log(Level.WARNING, e.toString()); 
                this.setDefaults(); 
            }
        }
        this.file = file;
        this.setDefaults();
    }

    public DataManager(DataManager dataManager) {
        this.file = dataManager.file; 
        this.settings = dataManager.settings; 
        this.programs = dataManager.programs; 
    }

    public boolean delete() {
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(file);
            fos.write(("").getBytes());
            fos.close();
            return true; 
        } catch (IOException e) {
            logger.log(Level.WARNING, e.toString()); 
            return false; 
        }
    }

    public boolean save() {
        try {
            System.out.println("BEFORE" + programs.size()); 
            //for some reason this is repeatedly adding programs 
            FileOutputStream fos = new FileOutputStream(file);
            System.out.println("AFTER" + programs.size()); 
            fos = new FileOutputStream(file); 
            ObjectOutputStream oos = new ObjectOutputStream(fos); 
            oos.writeObject(this); 
            oos.close(); 
            fos.close(); 
            return true; 
        } catch (IOException e) {
            logger.log(Level.WARNING, e.toString()); 
            return false; 
        }
    }

    public DataManager load() {
        try {
            FileInputStream fis = new FileInputStream(file); 
            ObjectInputStream ois = new ObjectInputStream(fis); 
            DataManager dm = (DataManager) ois.readObject(); 
            System.out.println("DM" + dm.programs.size()); 
            ois.close(); 
            fis.close(); 
            return dm; 
        } catch (IOException | ClassNotFoundException e) {
            logger.log(Level.WARNING, e.toString()); 
            return null;
        } 
    }

    public void setDefaults() {
        programs = new ArrayList<>(); 
        settings = new Settings("12hr", true); 
    }

    public List<Program> getPrograms() {
        return programs; 
    }
    
    public List<Program> getProgramsByViableTime(String day, LocalTime localTime) {
        List<Program> viablePrograms = new ArrayList<>(); 
        synchronized(programs) {
            for (Program program : programs) {
                if (program.getSchedule().inTime(day, localTime))
                    viablePrograms.add(program); 
            }
        }

        return viablePrograms; 
    }

    public Program getProgram(String name) {
        for (Program program : programs) {
            if (program.getName().equals(name)) {
                return program; 
            }
        }
        return null; 
    }

    public void addProgram(Program program) {
        synchronized(programs) {
            try {
                programs.add(program); 
            } catch (NullPointerException e) {
                logger.log(Level.INFO, e.toString()); 
                programs = new ArrayList<>(); 
                programs.add(program); 
            }
        }

    }

    public void deleteProgram(String name) {
        synchronized(programs) {
            for (int i = 0; i < programs.size(); i++) {
                if (programs.get(i).getName().equals(name)) {
                    programs.remove(i); 
                }
            }
        }
    }
    
    public void writeSettings(String clock, boolean safety) {
        settings.clock = clock; 
        settings.safety = safety; 
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
    
    public class Settings implements Serializable {
        private static final long serialVersionUID = 0; 
        public String clock; 
        public Boolean safety; 

        public Settings(String clock, Boolean safety) {
            this.clock = clock; 
            this.safety = safety; 
        }
        
        private void writeObject(ObjectOutputStream out) throws IOException {
            out.defaultWriteObject();
        }   
    
        private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
            in.defaultReadObject(); 
        }
    }
}