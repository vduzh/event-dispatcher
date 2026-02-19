package by.vduzh.event.dispatcher;

import by.vduzh.event.dispatcher.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Set;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class ActionEventDispatcherTest {

    @MockitoBean
    private ActionEventHandler<String> handler;

    @Autowired
    private ActionEventDispatcher dispatcher;

    @Test
    void shouldHandleActionEvent() {
        // Given - mock event
        var event = mock(ActionEvent.class);
        doReturn("foo.action").when(event).action();

        doNothing().when(handler).handle(any());

        // When
        dispatcher.dispatch(event);

        // Then
        verify(handler, times(1)).handle(any());
    }
}
