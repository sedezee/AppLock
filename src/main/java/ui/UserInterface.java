package ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import save.DataManager;
import time.Pair;
import time.Program;
import time.Schedule;

public class UserInterface {
    private DataManager dataManager;
    UIRegister register;

    public UserInterface() {
        register = new UIRegister();
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

    @SuppressWarnings("unchecked")
    private Schedule configSchedule(Scanner scanner, int mode) {
        System.out.println(mode);
        String[] days = { "monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday" };
        Schedule schedule = new Schedule();
        if (mode == 0) {
            for (String day : days) {
                System.out.println("What are your times for " + day + "? Enter NEXT when you're ready to move on.");
                List<Pair<LocalTime, LocalTime>> pairs = new ArrayList<>();
                while (true) {
                    try {
                        String resp = scanner.nextLine();
                        if (resp.equalsIgnoreCase("NEXT")) {
                            schedule.setDay(day, pairs.toArray(new Pair[pairs.size()]));
                            break;
                        }
                        LocalTime localTime = Schedule.parseTime(resp);
                        LocalTime localTimeTwo = Schedule.parseTime(scanner.nextLine());
                        pairs.add(new Pair<>(localTime, localTimeTwo));
                    } catch (DateTimeParseException e) {
                        System.out.println("There was an error with your time entry. Please re-enter your pair in format HH:MM.");
                    }
                }
            }
        } else {
            System.out.println("What are your times? Enter NEXT when you're ready to move on.");
            while (true) {
                List<Pair<LocalTime, LocalTime>> pairs = new ArrayList<>();
                String resp = scanner.nextLine();
                if (resp.equalsIgnoreCase("NEXT")) {
                    if (mode == 2) {
                        schedule.setWeekdays(pairs.toArray(new Pair[pairs.size()]));
                    } else if (mode == 3) {
                        schedule.setAllDays(pairs.toArray(new Pair[pairs.size()]));
                    }
                    break;
                }
                try {
                    LocalTime localTime = Schedule.parseTime(resp);
                    LocalTime localTimeTwo = Schedule.parseTime(scanner.nextLine());
                    pairs.add(new Pair<>(localTime, localTimeTwo));
                } catch (DateTimeParseException e) {
                    System.out.println("There was an error with your time entry. Please re-enter your pair in format HH:MM.");
                }
            }
        }
        return schedule;
    }

    public DataManager setUp(Scanner scanner) {
        while (true) {
            System.out.println("The default directory is" + System.getProperty("user.home")
                    + "\\Documents\\AppLock. Would you like to change this? YES or NO.");
            String resp = scanner.nextLine();
            if (resp.equalsIgnoreCase("YES")) {
                System.out.println("Please enter the directory you would like to place your files in.");
                resp = scanner.nextLine();
                File f = new File(resp + "SaveFile.txt");
                if (!f.mkdirs()) {
                    System.out.println("Directory does not exist. Using default directory.");
                } else {
                    System.out.println("Directory set.");
                    return dataManager = new DataManager(f);
                }
            }

            if (resp.equalsIgnoreCase("YES") || resp.equalsIgnoreCase("NO")) {
                return dataManager = new DataManager();
            }
        }
    }

    private void addProgram(Scanner scanner) {
        //TODO add safety check
        while (true) {
            System.out.println("INPUT file location or SELECT file location?");
            String resp = scanner.nextLine();
            if (resp.equalsIgnoreCase("INPUT")) {
                System.out.println("Input file location.");

                resp = scanner.nextLine();
                File file = new File(resp);
                resp = resp.length() >= 3 ? resp.substring(resp.length() - 3, resp.length()) : resp;

                if (file.exists() && resp.equalsIgnoreCase("exe")) {
                    try {
                        Program program = new Program(file, configSchedule(scanner, 0));
                        dataManager.addProgram(program);
                        System.out.println("Program added!");
                        break;
                    } catch (FileNotFoundException e) {
                        System.out.println("Sorry, file not found while running AddProgram()");
                    }
                }
            } else if (resp.equalsIgnoreCase("SELECT")) {
                Optional<String> programStr = this.selectProgram();
                try {
                    File file = new File(programStr.get());
                    Program program = new Program(file, configSchedule(scanner, 0));
                    dataManager.addProgram(program);
                    System.out.println("Program added!");
                    break;
                } catch (NoSuchElementException | FileNotFoundException e) {
                    System.out.println("Program not found.");
                }
            }
        }
    }

    private void viewPrograms(Scanner scanner) {
        List<Program> programs = dataManager.getPrograms();
        if (programs.isEmpty()) {
            System.out.println("No programs.");
        }

        for (Program program : programs) {
            System.out.println(program);
        }
    }

    private void deleteProgram(Scanner scanner) {
        System.out.println("Which program would you like to delete?");
        dataManager.deleteProgram(scanner.nextLine());
    }

    private void editSchedule(Scanner scanner) {
        while (true) {
            System.out.println("Would you like to EDIT your schedule or KEEP it the same?");
            String resp = scanner.nextLine();
            if (resp.equalsIgnoreCase("EDIT")) {
                while (true) {
                    System.out.println("Would you like to change your ENTIRE schedule, your WEEKLY schedule, or your schedule on a PROGRAM to program basis?");
                    resp = scanner.nextLine();
                    if (resp.equalsIgnoreCase("PROGRAM") || resp.equalsIgnoreCase("PROGRAM TO PROGRAM")) {
                        System.out.println("Which program would you like to change the schedule of?");
                        resp = scanner.nextLine();
                        Program program = dataManager.getProgram(resp);
                        Schedule schedule = configSchedule(scanner, 1);
                        if (program != null) {
                            program.setSchedule(schedule);
                            break;
                        } else {
                            System.out.println("Sorry, that program doesn't exist.");
                        }
                    } else {
                        Schedule schedule;
                        if (resp.equalsIgnoreCase("ENTIRE")) {
                            schedule = configSchedule(scanner, 3);
                        } else if (resp.equalsIgnoreCase("WEEKLY")) {
                            schedule = configSchedule(scanner, 2);
                        } else {
                            System.out.println("Sorry, that isn't an option.");
                            continue;
                        }

                        for (Program program : dataManager.getPrograms()) {
                            program.setSchedule(schedule);
                        }
                        break;
                    }
                }
            }
        }
    }

    private void save(Scanner scanner) {
        if (dataManager.save()) {
            System.out.println("Sucessfully saved.");
            return;
        }
        System.out.println("Save unsuccessful.");
    }

    private void editSettings(Scanner scanner) {
        //TODO
    }

    private void viewSettings(Scanner scanner) {
        //TODO
    }

    public void run() {
        register.register(this::addProgram, "add program", "Adds a program to the restricted list.");
        register.register(this::deleteProgram, "delete program", "Deletes a program from the resitricted list.");
        register.register(this::viewPrograms, "view programs", "View the programs you have added.");
        register.register(this::editSchedule, "edit schedule", "Allows you to edit components of your schedule.");
        register.register(this::save, "save", "save your schedule and your settings.");
        register.register(this::editSettings, "edit settings", "Edit your settings.");
        register.register(this::viewSettings, "view settings", "View your settings.");
        register.run();
    }
}