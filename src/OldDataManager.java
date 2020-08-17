package src;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;

import src.time.Program;

public class OldDataManager implements Serializable{
    private static String directory;
    private transient FileHandler saveFile;
    private Settings settings;
    private List<Program> programs;
    private transient Logger logger; 

    public OldDataManager() {
        logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME); 
        directory = System.getProperty("user.home") + "\\Documents\\AppLock";
        logger.log(Level.INFO, directory); 
        File f = new File(directory);
        if (!f.mkdirs() && new File(directory + "\\SaveFile.txt").exists()) {
            saveFile = new FileHandler(new File(directory + "\\SaveFile.txt"));
        } else {
            saveFile = new FileHandler(directory + "\\SaveFile.txt");
            this.setDefaults();
        }
        saveFile.open();
        programs = new ArrayList<>(); 
        this.readInSettings(); 
        settings = new Settings(); 
    }

    public OldDataManager(File f) {
        logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME); 
        directory = f.getAbsolutePath();
        File file = new File(directory + "\\SaveFile.txt");
        if (file.exists()) {
            saveFile = new FileHandler(file);
        } else {
            saveFile = new FileHandler(directory + "\\SaveFile.txt");
            this.setDefaults();
        }
        saveFile.open();
        programs = new ArrayList<>(); 
        this.readInSettings();
        settings = new Settings(); 
    }

    public List<Program> getPrograms() {
        return programs; 
    }

    public void wipeProgramList() {
        programs = new ArrayList<>(); 
    }

    public boolean readInSettings() {
        FileReader fr = saveFile.getFileReader();
        BufferedReader br = new BufferedReader(fr);
        String line;
        try {
            if ((line = br.readLine()) != null && line.substring(0, 8).equals("SETTINGS[")) {
                line = line.substring(8, line.length() - 1);
                String[] settingStr = line.split(",");
                for (String s : settingStr) {
                    String[] indiv = s.split(":");
                    if (indiv[0] == "CLOCK")
                        settings.clock = indiv[1];
                    else if (indiv[0] == "SAFETY")
                        settings.safety = Boolean.parseBoolean(indiv[1]);
                
                }
            return true;
            }
        } catch (IOException e) {
            logger.log(Level.WARNING, e.toString()); 
        }
        return false; 
    }

    public boolean readInPrograms() {
        FileReader fr = saveFile.getFileReader(); 
        BufferedReader br = new BufferedReader(fr); 
        String line; 
        try {
            br.readLine(); 
            if ((line = br.readLine()) != null && line.substring(0, 7).equals("PROGRAMS")) {
                line = line.substring(7, line.length() -1); 
                InputStream is = new ByteArrayInputStream(line.getBytes()); 
                ObjectInputStream ois = new ObjectInputStream(is);
                this.programs = (ArrayList<Program>) ois.readObject();  
            }
            return true; 
        } catch (IOException | ClassNotFoundException e) {
            logger.log(Level.WARNING, e.toString()); 
        }
        return false; 
    }

    public boolean writeSettings() {
        StringBuilder sb = new StringBuilder();
        sb.append("SETTINGS[CLOCK:");
        sb.append(settings.clock);
        sb.append(",SAFETY:");
        sb.append(settings.safety);
        sb.append("]\nPROGRAMS[");
        FileOutputStream fis;
        try {
            fis = new FileOutputStream(saveFile.getFile(), true);
            ObjectOutputStream ois = new ObjectOutputStream(fis);
            ois.writeObject(programs); 
        } catch (IOException e) {
            sb.append("]"); 
            return false; 
        }
        sb.append("]"); 
        return true; 
    }

    private boolean setDefaults() {
        return saveFile.write("SETTINGS[CLOCK:12HR,SAFETY:TRUE]\nPROGRAMS[]"); 
    }
    
    public void addProgram(Program program) {
        programs.add(program); 
    }

    class Settings {
        public String clock; 
        public Boolean safety; 
    }
}