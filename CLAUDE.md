# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Publishing

Hardcoded Nexus credentials in `build.gradle` are intentional — used only for local development (`localhost:8081`). Production publishing is handled separately via CI/CD.

## Build Commands

```bash
./gradlew build      # Build the library
./gradlew test       # Run all tests
./gradlew test --tests "ActionEventDispatcherTest.shouldDispatchToMatchingHandler"  # Run single test
./gradlew publish    # Publish to Nexus repository
```

## Architecture

A lightweight Java library providing an event dispatching framework based on the Dispatcher pattern (similar to DispatcherServlet routing). No Spring dependency at runtime — only SLF4J.

### Core Components

- **ActionEvent\<T>** - Immutable event record with UUID id, action string for routing, and payload (T)
- **ActionEventHandler\<T>** - Interface for event handlers; declare `supportedActions()` and implement `handle(event)`
- **ActionEventDispatcher** - Routes events to the matching handler (one handler per action, duplicates rejected at construction time with `IllegalStateException`)

### Event Flow

1. Create `ActionEvent` with action string and payload
2. Call `ActionEventDispatcher.dispatch(event)`
3. Dispatcher looks up handler by action (O(1) map lookup)
4. Handler's `handle(event)` processes the event

### Design Decisions

- **No Spring dependency at runtime** — the library depends only on `slf4j-api`. Consumers wire `ActionEventDispatcher` via their own `@Bean` method or any DI framework.
- **One handler per action** — duplicate actions across handlers are detected at construction time (fail-fast). This is intentional for the Kafka consumer use case where each event type maps to exactly one handler.
- **ActionEvent is a Java record** — immutable by design, no Lombok needed.

### Tech Stack

- Java 25
- SLF4J 2.0.17
- Gradle 9.1.0
- JUnit 5 + Mockito (tests only)
