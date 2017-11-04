package ru.victorpomidor.webcamalarma;

import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.SendResponse;
import java.io.File;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.victorpomidor.webcamalarma.config.Config;

public class TelegramNotifier {
    private static final String TOKEN = "470423736:AAFiUjRoxgg3xiHiSB0Cubgf8ez-hp-hGNg";
    private static final Logger logger = LoggerFactory.getLogger(TelegramNotifier.class);

    private final TelegramBot bot;
    private final String userId;

    public TelegramNotifier(Config config) {
        bot = new TelegramBot(TOKEN);
        userId = config.getTelegramUserId();
    }

    public void sendNotification(File shot) {
        bot.execute(new SendPhoto(userId, shot), new Callback<SendPhoto, SendResponse>() {
            @Override
            public void onResponse(SendPhoto request, SendResponse response) {
                logger.debug("send telegram notification");
            }

            @Override
            public void onFailure(SendPhoto request, IOException e) {
                logger.error("error when sending to telegram", e);
            }
        });
    }
}