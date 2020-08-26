package src.program_manager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class ProcessHandler {

    @FunctionalInterface
    public interface ThrowableFunc<T, R, E extends Throwable> {
        R apply(T t) throws E;

        static <T, R, E extends Throwable> Function<T, R> pass(ThrowableFunc<T, R, E> func) {
            return t -> {
                try {
                    return func.apply(t);
                } catch (Throwable e) {
                    return null;
                }
            };
        }
    }

    private Stream<ProcessHandle> getProcesses() {
        return ProcessHandle.allProcesses();
    }

    public long getProcessCount() {
        return getProcesses().count();
    }

    public String[] getProcessNames(Stream<ProcessHandle> processHandle) {
        List<Optional<String>> opReturns = new ArrayList<>();

        processHandle.forEach(process -> opReturns.add(process.info().command()));

        List<String> returns = new ArrayList<>();

        opReturns.stream().map(ThrowableFunc.pass(Optional<String>::get)).forEach(returns::add);

        return returns.toArray(new String[returns.size()]);
    }

    public String[] getAllProcessNames() {
        return getProcessNames(this.getProcesses());
    }

    public boolean processRunning(String process, String[] processList) {
        process = process.toLowerCase();

        for (String processItem : processList) {
            try {
                if (processItem.contains("System32"))
                    break;

                processItem = processItem.substring(processItem.lastIndexOf("\\") + 1, processItem.length())
                        .toLowerCase();
                if (processItem.equals(process))
                    return true;
            } catch (NullPointerException e) {
            }
        }

        return false;
    }

    public void kill(String name) throws IOException {
        Runtime.getRuntime().exec("taskkill /F /IM " + name); 
    }


    public void kill(File file) throws IOException {
        this.kill(file.getName()); 
    }

}