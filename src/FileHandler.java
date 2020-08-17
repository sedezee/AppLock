package src; 

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandler {

    File file; 

    String fileName; 
    FileWriter fileWriter; 
    FileReader fileReader; 

    public FileHandler(String fileName) { 
        this.fileName = fileName;    
        try { 
           file = new File(fileName); 
           file.createNewFile(); 
        } catch(Exception e) {
            System.out.println(e); 
        }
    }

    public FileHandler(File file) {
        this.fileName = file.getName(); 
        this.file = file; 
    }

    public boolean write(String text) {
        try {
            fileWriter.write(text); 
            fileWriter.flush(); 
            return true; 
         } catch(IOException e) {
             e.printStackTrace();
             return false; 
         }
    }

    public char[] read() {
        try {
            char[] charArr = new char[1024];
            fileReader.read(charArr); 
            return charArr; 
        } catch(IOException e) {
            e.printStackTrace(); 
        }
        return new char[] {};
    }

    public FileReader getFileReader() {
        return this.fileReader; 
    }

    public File getFile() {
        return this.file; 
    }

    public boolean open() {
        try {
            this.fileWriter = new FileWriter(file);    
            this.fileReader = new FileReader(file); 
            return true; 
        } catch (IOException e) {
            System.out.println(e); 
            return false; 
        }
    }

    public boolean close() {
        try {  
            fileReader.close(); 
            fileWriter.close(); 
            return true; 
        } catch (IOException e) {
            System.out.println(e); 
            return false; 
        }
    }
}