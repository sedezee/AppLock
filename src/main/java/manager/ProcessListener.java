package manager;

import save.DataManager;
import time.Pair;
import time.Program;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProcessListener {
    ProcessHandler handler;
    DataManager dataManager;


    /**
     * Creates a Process Manager to handle the upper level
     * process management.
     * @param dataManager the dataManager (or 'save class') to load
     * user settings from
     */
    public ProcessListener(DataManager dataManager) {
        handler = new ProcessHandler();
        this.dataManager = dataManager;
    }

    /**
     * Retrieves the programs within a specific timeframe
     * @return the programs that need to be closed
     */
    @SuppressWarnings("unchecked")
    private Pair<String, String>[] getCloseablePrograms() {
        List<Pair<String, String>> closeable = new ArrayList<>();

        String day = DayOfWeek.from(LocalDate.now()).name();

        // checks to see whether or not a program falls within the specific
        // time frame that requires it be closed
        for (var program : dataManager.getProgramsByViableTime(day, LocalTime.now())) {
            closeable.add(new Pair<>(program.getName(), program.getDirectory()));
        }

        return closeable.toArray(new Pair[closeable.size()]);
    }

    /**
     * Respond to a program being added, and close the programs
     * which are currently in the time window to close.
     */
    public void programAdded() {
        // pulls all of the running processes
        String[] processes = handler.getAllProcessNames();

        // all programs that need to be closed
        Pair<String, String>[] closeables = this.getCloseablePrograms();

        for (int i = 0; i < processes.length; i++) {
            for (int j = 0; j < closeables.length; j++) {

                // see if any of the processes currently running match processes that need to be closed
                if (processes[i] != null && processes[i].equals(closeables[j].right())) {
                    try {
                        System.out.println("Killed " + closeables[j].left());
                        // kill the located process
                        handler.kill(closeables[j].left());
                    } catch (IOException e) {
                        Logger.getGlobal().log(Level.WARNING, e.toString());
                    }
                }
            }
        }
    }
}
