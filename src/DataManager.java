package src;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
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

    public DataManager() {
        String directory = System.getProperty("user.home") + "\\Documents\\AppLock"; 
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
        } 
        this.file = file; 
        this.setDefaults(); 
    }

    private void setDefaults() {
        programs = new ArrayList<>(); 
        settings = new Settings("12hr", true); 
    }

    public void addProgram(Program program) {
        programs.add(program); 
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