package src.program_manager;

import java.util.ArrayList;
import java.util.List;

public class ProcessInterface {
    private List<ProcessListener> listeners = new ArrayList<>(); 
    private long numProcesses = 0;
    ProcessHandler processHandler; 

    public ProcessInterface() {
        processHandler = new ProcessHandler(); 
    }

    public void addListener(ProcessListener listener) {
        listeners.add(listener); 
    }

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