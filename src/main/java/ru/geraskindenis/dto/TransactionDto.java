package ru.geraskindenis.dto;

import ru.geraskindenis.domain.PersonalAccount;
import ru.geraskindenis.domain.TransactionTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionDto(Long id, UUID uuid, PersonalAccount personalAccount, LocalDateTime createdAt,
                             Boolean accept, TransactionTypes transactionTypes, BigDecimal amount) {
}
