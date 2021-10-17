import java.util.Scanner;

import manager.ProcessRunner;
import manager.ProcessListener;
import save.DataManager;
import ui.UserInterface;

public class Main {

    public static void main(String... args) {
        GUI.go(args);
    }

    public static void mainNo(String... args) {
        UserInterface ui = new UserInterface();
        DataManager dataManager = ui.setUp(new Scanner(System.in));

        Thread uiThread = new Thread() {
            @Override
            public void run() {
                ui.run();
            }
        };

        Thread processThread = new Thread() {
            @Override
            public void run() {
                ProcessRunner processRunner = new ProcessRunner();
                processRunner.addListener(new ProcessListener(dataManager));
                int i = 0;
                while (true) {
                    i++;
                    if (i == 25) {
                        processRunner.checkProcess();
                        i = 0;
                    }
                }
            }
        };

        uiThread.start();
        processThread.start();

    }

}