package ua.kpi.comsys.dop;

public record AudioMsg(String sender, int durationSeconds) implements Notification {
    public AudioMsg {
        if (sender == null || sender.isBlank()) {
            throw new IllegalArgumentException("Sender cannot be blank");
        }
        if (durationSeconds <= 0) {
            throw new IllegalArgumentException("Duration must be positive");
        }
    }
}
