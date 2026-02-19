package by.vduzh.event.dispatcher.config;

import by.vduzh.event.dispatcher.ActionEventDispatcher;
import by.vduzh.event.dispatcher.ActionEventHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class TestConfig {
    @Bean
    public ActionEventDispatcher dispatcher(List<ActionEventHandler<?>> handlers) {
        return new ActionEventDispatcher(handlers);
    }
}
