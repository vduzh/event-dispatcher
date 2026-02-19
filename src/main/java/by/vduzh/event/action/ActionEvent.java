package by.vduzh.event.action;

import java.util.UUID;

public record ActionEvent<T>(UUID id, String action, T payload) {
}

