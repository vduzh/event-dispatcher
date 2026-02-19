package by.vduzh.event.dispatcher;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActionEventDispatcherTest {

    @Test
    void shouldDispatchToMatchingHandler() {
        // Given
        ActionEventHandler<String> handler = mock(ActionEventHandler.class);
        when(handler.supportedActions()).thenReturn(Set.of("order.created"));

        var dispatcher = new ActionEventDispatcher(List.of(handler));
        var event = new ActionEvent<>(UUID.randomUUID(), "order.created", "payload");

        // When
        dispatcher.dispatch(event);

        // Then
        verify(handler).handle(event);
    }

    @Test
    void shouldNotFailWhenNoHandlerFound() {
        // Given
        ActionEventHandler<String> handler = mock(ActionEventHandler.class);
        when(handler.supportedActions()).thenReturn(Set.of("order.created"));

        var dispatcher = new ActionEventDispatcher(List.of(handler));
        var event = new ActionEvent<>(UUID.randomUUID(), "unknown.action", "payload");

        // When / Then — no exception
        dispatcher.dispatch(event);
        verify(handler, never()).handle(any());
    }

    @Test
    void shouldFailOnDuplicateAction() {
        // Given
        ActionEventHandler<String> handler1 = mock(ActionEventHandler.class);
        ActionEventHandler<String> handler2 = mock(ActionEventHandler.class);
        when(handler1.supportedActions()).thenReturn(Set.of("order.created"));
        when(handler2.supportedActions()).thenReturn(Set.of("order.created"));

        // When / Then
        assertThrows(IllegalStateException.class,
                () -> new ActionEventDispatcher(List.of(handler1, handler2)));
    }
}
