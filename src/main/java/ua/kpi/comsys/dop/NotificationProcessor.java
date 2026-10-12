package ua.kpi.comsys.dop;

public class NotificationProcessor {
    public String process(Notification notification) {
        if (notification == null) {
            throw new IllegalArgumentException("Notification cannot be null");
        }

        return switch (notification) {
            case TextMsg text -> """
                    Notification: TEXT
                    From: %s
                    Message: %s
                    """.formatted(text.sender(), text.text());
            case ImageMsg image -> """
                    Notification: IMAGE
                    From: %s
                    URL: %s (%d KB)
                    """.formatted(image.sender(), image.url(), image.sizeKb());
            case AudioMsg audio -> """
                    Notification: AUDIO
                    From: %s
                    Duration: %d seconds
                    """.formatted(audio.sender(), audio.durationSeconds());
        };
    }
}
