package ru.victorpomidor.webcamalarma;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamMotionDetector;
import com.github.sarxos.webcam.WebcamMotionEvent;
import com.github.sarxos.webcam.WebcamMotionListener;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;
import javax.imageio.ImageIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.victorpomidor.webcamalarma.config.Config;

public class MotionCapture implements WebcamMotionListener {
    private static final String SHOTS_DIR = "shots";
    private static final Logger logger = LoggerFactory.getLogger(MotionCapture.class);

    private final TelegramNotifier telegramNotifier;
    private final Webcam webcam;
    private final WebcamMotionDetector detector;

    private final boolean allowTelegramNotifications;

    public MotionCapture(TelegramNotifier telegramNotifier, Config config) {
        this.telegramNotifier = telegramNotifier;
        allowTelegramNotifications = config.isAllowTelegramNotifications();

        webcam = Webcam.getDefault();
        detector = new WebcamMotionDetector(Webcam.getDefault());
        try {
            makeDirIfNeeds();

            webcam.setViewSize(getBestDimension());
            webcam.open();

            detector.setInterval(config.getShotFrequency());
            detector.addMotionListener(this);
        } catch (Exception e) {
            logger.error("init exception", e);
        }
    }

    public void start() {
        detector.start();
    }

    @Override
    public void motionDetected(WebcamMotionEvent wme) {
        try {
            File shot = makeShot();
            if (allowTelegramNotifications) {
                telegramNotifier.sendNotification(shot);
            }
        } catch (IOException e) {
            logger.error("exception when try make a shot");
        }
    }

    private Dimension getBestDimension() {
        return Stream.concat(
                Arrays.stream(webcam.getCustomViewSizes()),
                Arrays.stream(webcam.getViewSizes()))
                .sorted(Comparator.comparingDouble(Dimension::getWidth).reversed())
                .findFirst()
                .orElseGet(() -> new Dimension(640, 480));
    }

    private void makeDirIfNeeds() throws IOException {
        File file = new File(SHOTS_DIR);
        if (!file.exists() && !file.mkdir()) {
            throw new IOException("cannot make " + SHOTS_DIR + " directory");
        }
    }

    private File makeShot() throws IOException {
        File shot = new File(SHOTS_DIR + "/" + System.currentTimeMillis() + ".png");
        ImageIO.write(webcam.getImage(), "PNG", shot);
        logger.debug("maked a shot");
        return shot;
    }
}