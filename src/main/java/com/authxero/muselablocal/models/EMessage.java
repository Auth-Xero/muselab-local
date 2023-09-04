package com.authxero.muselablocal.models;

public enum EMessage {
    NO_CHANGES(0),CLOSE(1),LOGIN(2), JOIN(3),LEAVE(4),SYNC(5),CHANGE_SCORE(6), META(7);

    private final Integer value;
    EMessage(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
