package br.com.personalgymapi.domain.enums;

public enum TrainingType {
    A(0),
    B(1),
    C(2),
    D(3),
    E(4);

    private final int value;

    TrainingType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static TrainingType fromValue(int value) {
        for (TrainingType role : TrainingType.values()) {
            if (role.getValue() == value) {
                return role;
            }
        }
        throw new IllegalArgumentException("Valor de TrainingType inválido: " + value);
    }
}
