package ru.geraskindenis.dto;

import ru.geraskindenis.domain.User;

import java.math.BigDecimal;

public record PersonalAccountDto(Long id, User owner, BigDecimal balance) {
}
