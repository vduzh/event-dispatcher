package by.vduzh.event.action.config;

import by.vduzh.event.action.ActionEvent;
import by.vduzh.event.action.ActionEventDispatcher;
import by.vduzh.event.action.ActionEventHandler;
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
