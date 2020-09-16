package src;

import java.util.Scanner;

import src.program_manager.ProcessInterface;
import src.program_manager.ProcessResponder;
import src.user_interface.UserInterface;

public class Main {
    
    public static void main(String... args) {
      
       
                    
        UserInterface ui = new UserInterface(); 
        DataManager dataManager = ui.setUp(new Scanner(System.in)); 

        Thread uiThread = new Thread() {
            @Override
            public void run() {
                ui.run(); 
            }
        };

        Thread processThread = new Thread() {
            @Override
            public void run() {
                ProcessInterface processInterface = new ProcessInterface();
                processInterface.addListener(new ProcessResponder(dataManager));
                int i = 0; 
                while (true) {
                    i++; 
                    if (i == 25) {
                        processInterface.checkProcess();
                        i = 0; 
                    }
                }
            }
        };
    
        uiThread.start(); 
        processThread.start(); 

    }
    
}