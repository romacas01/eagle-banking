package com.banking.eagle.model;

public enum TransactionType {
    DEPOSIT("deposit"),
    WITHDRAW("withdraw");

    private final String value;

    private TransactionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static TransactionType findByKey(String key) {
        TransactionType[] types = TransactionType.values();
        for (TransactionType typeEnum : types) {
            if (typeEnum.value.equals(key)) {
                return typeEnum;
            }
        }
        return null;
    }
}
