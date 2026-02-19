package by.vduzh.event.dispatcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActionEventDispatcher {
    private static final Logger log = LoggerFactory.getLogger(ActionEventDispatcher.class);
    private final Map<String, ActionEventHandler<?>> handlerMap;

    public ActionEventDispatcher(List<? extends ActionEventHandler<?>> handlers) {
        Map<String, ActionEventHandler<?>> handlerMap = new HashMap<>();

        for (ActionEventHandler<?> handler : handlers) {
            handler.supportedActions().forEach(action -> {
                ActionEventHandler<?> existingHandler = handlerMap.get(action);
                if (existingHandler != null) {
                    throw new IllegalStateException(
                            "Duplicate handler for action '%s': %s and %s".formatted(
                                    action,
                                    existingHandler.getClass().getSimpleName(),
                                    handler.getClass().getSimpleName()
                            ));
                }
                handlerMap.put(action, handler);
            });
        }

        this.handlerMap = handlerMap;
    }

    @SuppressWarnings("unchecked")
    public void dispatch(ActionEvent<?> event) {
        ActionEventHandler<?> handler = handlerMap.get(event.action());

        if (handler == null) {
            log.warn("No handler found for action: {}", event.action());
            return;
        }

        ((ActionEventHandler<Object>) handler).handle((ActionEvent<Object>) event);
    }
}
