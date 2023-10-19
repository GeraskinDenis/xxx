package ru.geraskindenis.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class EventRecord {
    private Long id;
    private LocalDateTime time;
    private Long userId;
    private String message;

    public static EventRecord of(User user, String message) {
        return new EventRecord(user.getId(), message);
    }

    public EventRecord() {
    }

    private EventRecord(Long userId, String message) {
        this.time = LocalDateTime.now();
        this.userId = userId;
        this.message = message;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventRecord that)) return false;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(time, that.time)) return false;
        if (!Objects.equals(userId, that.userId)) return false;
        return Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("%s User ID: %d [%s]", time.toString(), userId, message);
    }
}
