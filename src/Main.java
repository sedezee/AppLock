package src;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import src.program_manager.ProcessHandler;
import src.program_manager.ProcessInterface;
import src.program_manager.ProcessListener;
import src.program_manager.ProcessResponder;
import src.time.Pair;
import src.time.Program;
import src.time.Schedule;
import src.user_interface.UserInterface;

public class Main {
    
    public static void main(String... args) throws IOException, ClassNotFoundException {
      
        //TESTING CODE FOR KILLER
        // Pair<LocalTime, LocalTime> pair = new Pair(LocalTime.of(1, 30), LocalTime.of(2, 40)); 
        // Pair<LocalTime, LocalTime> pair2 = new Pair(LocalTime.of(22, 30), LocalTime.of(23, 20)); 
        // Schedule schedule = new Schedule(); 
        // schedule.setAllDays(new Pair[] {pair, pair2}); 
        // File file = new File("C:\\Program Files\\Epic Games\\Celeste\\Celeste.exe"); 
        // Program program = new Program(file, schedule);
        // dataManager.delete(); 
        // dataManager.load(); 
        // dataManager.addProgram(program); 
        // int i = 0; 
        // String[] processNames = processHandler.getAllProcessNames(); 
        // while(true) {
        //     i++; 
        //     if (i == 25) {
        //         processInterface.checkProcess();
        //     }
        // }

        UserInterface ui = new UserInterface(); 
        ui.run(); 

    }
    
}