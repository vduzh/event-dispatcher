package by.vduzh.event.dispatcher;

import java.util.UUID;

public record ActionEvent<T>(UUID id, String action, T payload) {
}
