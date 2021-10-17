package save;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Helped class to make writing and reading from a file simpler
 *
 * @author Holland Louis Delrin
 */

public class FileHandler {

    File file;

    String fileName;
    FileWriter fileWriter;
    FileReader fileReader;

    /**
     * Create a fileHandler attached to a file name
     * @param fileName the name of the file to read/write
     */
    public FileHandler(String fileName) {
        this.fileName = fileName;
        try {
            file = new File(fileName);
            file.createNewFile();
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Create a save.FileHandler attached to a specific file
     * @param file the file to read/write
     */
    public FileHandler(File file) {
        this.fileName = file.getName();
        this.file = file;
    }

    /**
     * Write to the attached file
     * @param text the string to write
     * @return whether writing was successful
     */
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

    /**
     * Read from the attached file
     * @return the char[] read from the file. Will always be of length 1024.
     */
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

    /**
     * Get the FileReader used in the save.FileHandler
     * @return the FileReader being used to read the file
     */
    public FileReader getFileReader() {
        return this.fileReader;
    }

    /**
     * Get the file being read/written to
     * @return the file the save.FileHandler is attached to
     */
    public File getFile() {
        return this.file;
    }

    /**
     * Open the save.FileHandler for reading and writing.
     * Must be performed before the handler is used.
     * @return whether opening was successful
     */
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

    /**
     * Close the save.FileHandler and attached resources.
     * @return whether closing was successful
     */
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