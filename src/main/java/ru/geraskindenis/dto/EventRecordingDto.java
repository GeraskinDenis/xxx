package ru.geraskindenis.dto;

import ru.geraskindenis.domain.User;

import java.time.LocalDateTime;

public record EventRecordingDto(LocalDateTime time, User user, String message) {
}
