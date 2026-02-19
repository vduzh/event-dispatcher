# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Publishing

Hardcoded Nexus credentials in `build.gradle` are intentional — used only for local development (`localhost:8081`). Production publishing is handled separately via CI/CD.

## Build Commands

```bash
./gradlew build      # Build the library
./gradlew test       # Run all tests
./gradlew test --tests "ActionEventDispatcherTest.testDispatch"  # Run single test
./gradlew publish    # Publish to Nexus repository
```

## Architecture

This is a Spring Boot library providing an event dispatching framework based on the Event Dispatcher pattern (similar to DispatcherServlet routing).

### Core Components

- **ActionEvent\<T>** - Immutable event record with UUID id, action string for routing, and payload (T)
- **ActionEventHandler\<T>** - Interface for event handlers; declare `supportedActions()` and implement `handle(event)`
- **ActionEventDispatcher** - Spring component that routes events to the matching handler (one handler per action, validated at startup)

### Event Flow

1. Create `ActionEvent` with action string and payload
2. Call `ActionEventDispatcher.dispatch(event)`
3. Dispatcher looks up handler by action (O(1) map lookup)
4. Handler's `handle(event)` processes the event

### Tech Stack

- Java 25
- Spring Boot 3.5.3
- Lombok
- Gradle 9.1.0
