# event-dispatcher

A lightweight Java library for dispatching action-based events to handlers. No Spring dependency at runtime.

## Usage

Define a handler:

```java
public class OrderHandler implements ActionEventHandler<OrderDto> {
    @Override
    public Set<String> supportedActions() {
        return Set.of("order.created", "order.updated");
    }

    @Override
    public void handle(ActionEvent<OrderDto> event) {
        // process event
    }
}
```

Create and use the dispatcher:

```java
var dispatcher = new ActionEventDispatcher(List.of(new OrderHandler()));
dispatcher.dispatch(new ActionEvent<>(UUID.randomUUID(), "order.created", orderDto));
```

## Build

```bash
./gradlew build      # Build the library
./gradlew test       # Run all tests
./gradlew publish    # Publish to Nexus repository
```
