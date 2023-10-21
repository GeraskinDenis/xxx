package ru.geraskindenis.model;

public enum TransactionType {
    DEBIT("DEBIT"), CREDIT("CREDIT");
    private final String name;

    TransactionType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
