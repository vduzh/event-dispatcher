package by.vduzh.event.dispatcher;

import java.util.Set;

public interface ActionEventHandler<T> {
    Set<String> supportedActions();

    void handle(ActionEvent<T> event);
}
