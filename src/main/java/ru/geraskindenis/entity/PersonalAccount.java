package ru.geraskindenis.entity;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * The personal account of user.
 */
public class PersonalAccount {
    /**
     * ID {@link Long} of object.
     */
    private Long id;

    /**
     * ID {@link Long} of {@link User}
     */
    private Long ownerId;

    /**
     * This filed store a balance.
     */
    private BigDecimal balance = BigDecimal.ZERO;

    public PersonalAccount() {
    }

    public PersonalAccount(User user) {
        this.ownerId = user.getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonalAccount that)) return false;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(ownerId, that.ownerId)) return false;
        return Objects.equals(balance, that.balance);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (ownerId != null ? ownerId.hashCode() : 0);
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Personal account ID: " + id;
    }
}
