package ru.geraskindenis.domain;

public enum TransactionTypes {
    DEBIT("DEBET"), CREDIT("CREDIT");
    private final String name;

    TransactionTypes(String name) {
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
