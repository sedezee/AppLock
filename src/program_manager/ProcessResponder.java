package src.program_manager;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import src.DataManager;
import src.time.Pair;
import src.time.Program;

public class ProcessResponder implements ProcessListener {
    ProcessHandler handler;
    DataManager dataManager;

    public ProcessResponder(DataManager dataManager) {
        handler = new ProcessHandler();
        this.dataManager = dataManager;
    }

    private Pair<String, String>[] getCloseablePrograms() {
        List<Pair<String, String>> closeable = new ArrayList<>();
        String day = DayOfWeek.from(LocalDate.now()).name();
        for (Program program : dataManager.getProgramsByViableTime(day, LocalTime.now())) {
            closeable.add(new Pair<String, String>(program.getName(), program.getDirectory()));
        }

        return closeable.toArray(new Pair[closeable.size()]);
    }

    public void programAdded() {
        String[] processes = handler.getAllProcessNames();
        Pair<String, String>[] closeables = this.getCloseablePrograms();
        for (int i = 0; i < processes.length; i++) {
            for (int j = 0; j < closeables.length; j++) {
                if (processes[i] != null && processes[i].equals(closeables[j].right())) {
                    try {
                        System.out.println("Killed " + closeables[j].left()); 
                        handler.kill(closeables[j].left());
                    } catch (IOException e) {
                        Logger.getGlobal().log(Level.WARNING, e.toString()); 
                    }
                }
            }
        }
    }
}