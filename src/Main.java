package src;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalTime;
import java.util.List;

import src.time.Pair;
import src.time.Program;
import src.time.Schedule;

public class Main {
    public static void main(String... args) throws IOException, ClassNotFoundException {
        // ProcessHandler processHandler = new ProcessHandler(); 
        // if(processHandler.processRunning("Celeste.exe", processHandler.getAllProcessNames())) {
        //     Runtime.getRuntime().exec("taskkill /IM " + "Celeste.exe"); 
        // } 
        // UserInterface ui = new UserInterface(); 
        Pair<LocalTime, LocalTime> x = new Pair<>(LocalTime.of(2, 30), LocalTime.of(3, 30)); 
        Pair<LocalTime, LocalTime> y = new Pair<>(LocalTime.of(3, 30), LocalTime.of(4,30)); 
        
        Pair[] yx = {y, x}; 
        Schedule schedule = new Schedule(); 
        schedule.setAllDays(yx); 
        File file = new File("C:\\Users\\sedez\\Downloads\\IT Letter - Google Docs.pdf");
        Program program = new Program(file, schedule); 
        OldDataManager dataManager = new OldDataManager(); 
        dataManager.addProgram(program); 
        List<Program> programs = dataManager.getPrograms(); 
        dataManager.writeSettings();
        dataManager.wipeProgramList(); 
        System.out.println(dataManager.getPrograms().size()); 
        dataManager.readInPrograms(); 
        System.out.println(dataManager.getPrograms().size()); 
        List<Program> programs2 = dataManager.getPrograms(); 
        System.out.println(programs == programs2); 
    }
    
}