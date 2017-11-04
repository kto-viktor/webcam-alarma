package ru.victorpomidor.webcamalarma;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import ru.victorpomidor.webcamalarma.config.Config;
import ru.victorpomidor.webcamalarma.config.ConfigReader;

public class Application {
    private static final String EXIT_COMMAND = "exit";

    public static void main(String[] args) throws IOException {
        Config config = ConfigReader.readConfig("config.properties");

        MotionCapture motionCapture = new MotionCapture(new TelegramNotifier(config), config);
        motionCapture.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("type exit to exit");

        do {

        } while (!reader.readLine().equals(EXIT_COMMAND));
    }
}
