package manager;

import java.util.ArrayList;
import java.util.List;

public class ProcessRunner {
    private List<ProcessListener> listeners = new ArrayList<>();
    private long numProcesses = 0;
    ProcessHandler processHandler;

    public ProcessRunner() {
        processHandler = new ProcessHandler();
    }

    /**
     * Adds a listener to the Process Runner, to be notified
     * when a program is added
     * @param listener the listener to add
     */
    public void addListener(ProcessListener listener) {
        listeners.add(listener);
    }

    /**
     * Polls the processes to see if they are running, and, if they are
     * notifies the listeners.
     */
    public void checkProcess() {
        long count = processHandler.getProcessCount();
        if (count > numProcesses) {
            for (ProcessListener listener : listeners) {
                listener.programAdded();
            }
        }
        numProcesses = count;
    }
}
