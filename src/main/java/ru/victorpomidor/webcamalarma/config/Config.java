package ru.victorpomidor.webcamalarma.config;

public class Config {
    private int shotFrequency;

    private boolean allowTelegramNotifications;

    private String telegramUserId;

    public Config(int shotFrequency, boolean allowTelegramNotifications, String telegramUserId) {
        this.shotFrequency = shotFrequency;
        this.allowTelegramNotifications = allowTelegramNotifications;
        this.telegramUserId = telegramUserId;
    }

    public Config(int shotFrequency, boolean allowTelegramNotifications) {
        this.shotFrequency = shotFrequency;
        this.allowTelegramNotifications = allowTelegramNotifications;
    }

    public int getShotFrequency() {
        return shotFrequency;
    }

    public Config setShotFrequency(int shotFrequency) {
        this.shotFrequency = shotFrequency;
        return this;
    }

    public boolean isAllowTelegramNotifications() {
        return allowTelegramNotifications;
    }

    public Config setAllowTelegramNotifications(boolean allowTelegramNotifications) {
        this.allowTelegramNotifications = allowTelegramNotifications;
        return this;
    }

    public String getTelegramUserId() {
        return telegramUserId;
    }

    public Config setTelegramUserId(String telegramUserId) {
        this.telegramUserId = telegramUserId;
        return this;
    }
}
