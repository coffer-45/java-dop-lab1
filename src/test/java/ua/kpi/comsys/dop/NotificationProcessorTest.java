package ua.kpi.comsys.dop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NotificationProcessorTest {
    private final NotificationProcessor processor = new NotificationProcessor();

    @Test
    void formatsTextNotification() {
        assertEquals("Notification: TEXT\nFrom: Alice\nMessage: Hello!\n",
                processor.process(new TextMsg("Alice", "Hello!")));
    }

    @Test
    void formatsImageNotification() {
        assertEquals("Notification: IMAGE\nFrom: Bob\nURL: https://example.com/a.png (256 KB)\n",
                processor.process(new ImageMsg("Bob", "https://example.com/a.png", 256)));
    }

    @Test
    void formatsAudioNotification() {
        assertEquals("Notification: AUDIO\nFrom: Carol\nDuration: 42 seconds\n",
                processor.process(new AudioMsg("Carol", 42)));
    }

    @Test
    void rejectsNullNotificationWithRequiredMessage() {
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                () -> processor.process(null));
        assertEquals("Notification cannot be null", error.getMessage());
    }

    @Test
    void rejectsInvalidTextFields() {
        assertThrows(IllegalArgumentException.class, () -> new TextMsg(null, "Hello"));
        assertThrows(IllegalArgumentException.class, () -> new TextMsg("  \t", "Hello"));
        assertThrows(IllegalArgumentException.class, () -> new TextMsg("Alice", null));
        assertThrows(IllegalArgumentException.class, () -> new TextMsg("Alice", " \n"));
    }

    @Test
    void rejectsInvalidImageFields() {
        assertThrows(IllegalArgumentException.class, () -> new ImageMsg(null, "url", 1));
        assertThrows(IllegalArgumentException.class, () -> new ImageMsg(" ", "url", 1));
        assertThrows(IllegalArgumentException.class, () -> new ImageMsg("Bob", null, 1));
        assertThrows(IllegalArgumentException.class, () -> new ImageMsg("Bob", "\t", 1));
        assertThrows(IllegalArgumentException.class, () -> new ImageMsg("Bob", "url", 0));
        assertThrows(IllegalArgumentException.class, () -> new ImageMsg("Bob", "url", -1));
    }

    @Test
    void rejectsInvalidAudioFields() {
        assertThrows(IllegalArgumentException.class, () -> new AudioMsg(null, 1));
        assertThrows(IllegalArgumentException.class, () -> new AudioMsg("\n", 1));
        assertThrows(IllegalArgumentException.class, () -> new AudioMsg("Carol", 0));
        assertThrows(IllegalArgumentException.class, () -> new AudioMsg("Carol", -1));
    }
}
