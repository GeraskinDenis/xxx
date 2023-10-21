package ru.geraskindenis.entity;

import ru.geraskindenis.model.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Transaction {
    private Long id;
    private UUID uuid;
    private Long personalAccountId;
    private LocalDateTime createdAt;
    private Boolean accept;
    private TransactionType transactionType;
    private BigDecimal amount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Long getPersonalAccountId() {
        return personalAccountId;
    }

    public void setPersonalAccountId(Long personalAccountId) {
        this.personalAccountId = personalAccountId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getAccept() {
        return accept;
    }

    public void setAccept(Boolean accept) {
        this.accept = accept;
    }

    public boolean isAccept() {
        return accept;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction that)) return false;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(uuid, that.uuid)) return false;
        if (!Objects.equals(personalAccountId, that.personalAccountId))
            return false;
        if (!Objects.equals(createdAt, that.createdAt)) return false;
        if (!Objects.equals(accept, that.accept)) return false;
        if (transactionType != that.transactionType) return false;
        return Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
        result = 31 * result + (personalAccountId != null ? personalAccountId.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (accept != null ? accept.hashCode() : 0);
        result = 31 * result + (transactionType != null ? transactionType.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s (uuid:%s) (id:%d)"
                , createdAt.toString()
                , transactionType.toString()
                , amount.toString()
                , accept ? "Accept" : "Not accept"
                , uuid, id);
    }
}
