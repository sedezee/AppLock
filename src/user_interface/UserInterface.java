package src.user_interface;

import java.io.File;
import java.util.Optional;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import src.OldDataManager;

public class UserInterface {
    private Scanner scanner; 
    private OldDataManager dataManager; 
    
    public UserInterface() {
        scanner = new Scanner(System.in); 
        setUp(scanner); 
    }

    private Optional<String> selectProgram() {
        JFileChooser fileChooser = new JFileChooser(); 
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Executables", "exe"); 
        fileChooser.setFileFilter(filter);
        int returnVal = fileChooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) { 
            return Optional.of(fileChooser.getSelectedFile().getAbsolutePath());  
        } 

        return Optional.of(null); 
    }

    private void setUp (Scanner scanner) {
        while (true) {
            System.out.println("The default directory is C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\AppLock. Would you like to change this? YES or NO.");
            String resp = scanner.nextLine().toUpperCase(); 
            if (resp.equals("YES")) {
                System.out.println("Please enter the directory you would like to place your files in."); 
                resp = scanner.nextLine(); 
                File f = new File(resp); 
                if (!f.mkdir()) {
                    System.out.println("Directory does not exist. Using default directory."); 
                    break; 
                } else { 
                    System.out.println("Directory set."); 
                    dataManager = new OldDataManager(f); 
                }
            }
        } 
    }

    private void addProgram(Scanner scanner) {
        while (true) {
            System.out.println("INPUT file location or SELECT file location?"); 
            String resp = scanner.nextLine().toUpperCase(); 
            if (resp.equals("INPUT")) {
                System.out.println("Input file location."); 
                
                resp = scanner.nextLine(); 
                File file = new File(resp);
                resp = resp.length() >= 3 ? resp.substring(resp.length() - 3, resp.length()) : resp; 

                if (file.exists() && resp.equals("exe")) {
                    
                } 
            } else if (resp.equals("SELECT")) {
                Optional<String> program = this.selectProgram(); 
            }
        }
    }
}