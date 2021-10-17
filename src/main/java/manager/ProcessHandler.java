package manager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class ProcessHandler {

    @FunctionalInterface
    public interface OptionWrapper<T, R, E extends Throwable> {
        R apply(T t) throws E;

        static <T, R, E extends Throwable> Function<T, R> pass(OptionWrapper<T, R, E> func) {
            return t -> {
                try {
                    return func.apply(t);
                } catch (Throwable E) {
                    return null;
                }
            };
        }
    }


    /**
     * Pull the running processes
     * @return the processes running on the computer
     */
    private Stream<ProcessHandle> getProcesses() {
        return ProcessHandle.allProcesses();
    }

    /**
     * Get a count of processes running
     * @return the number of processes runing
     */
    public long getProcessCount() {
        return getProcesses().count();
    }

    /**
     * Get the process names from a stream of ProcessHandles
     * @param processHandle the stream of process handlers to extract names from
     * @return the names of the processes
     */
    private String[] getProcessNames(Stream<ProcessHandle> processHandle) {
        List<Optional<String>> opReturns = new ArrayList<>();

        // sort through the process handles and add them to the opReturns list
        processHandle.forEach(process -> opReturns.add(process.info().command()));

        List<String> returns = new ArrayList<>();

        // stream the names from an Optional<String> list into an array
        opReturns.stream().map(OptionWrapper.pass(Optional<String>::get)).forEach(returns::add);

        return returns.toArray(new String[returns.size()]);
    }

    /**
     * Get the names of all processes running
     * @return extracts the process names from the raw processes
     */
    public String[] getAllProcessNames() {
        return getProcessNames(this.getProcesses());
    }

    /**
     * Checks to verify whether or not a process is running
     * @param process the process to check
     * @param processList the list to check the process in
     * @return whether or not the process is running and appears in the list
     */
    public boolean processRunning(String process, String[] processList) {
        process = process.toLowerCase();

        for (String processItem : processList) {
            try {
                // should not kill system32 processes, as it might kill the system
                if (processItem.contains("System32"))
                    break;

                processItem = processItem
                        .substring(processItem.lastIndexOf("\\") + 1, processItem.length())
                        .toLowerCase();

                if (processItem.equals(process))
                    return true;

            } catch (NullPointerException e) {
                // do not care about nulls
            }
        }

        return false;
    }

    /**
     *
     * @param name the file process to kill
     * @throws IOException if an I/O error occurs while executing
     */
    public void kill(String name) throws IOException {
        Runtime.getRuntime().exec("taskkill /F /IM " + name);
    }


    /**
     * Kills the specified process from a file.
     * @param file the file process to kill. Most often of extension .exe
     * @throws IOException  if an I/O error occurs while executing
     */
    public void kill(File file) throws IOException {
        this.kill(file.getName());
    }
}
