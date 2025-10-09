## Architecture Flow

```
┌─────────────────┐
│  InputManager   │ (Publisher)
│  - Captures     │
│    input        │
└────────┬────────┘
         │ publish()
         ▼
┌─────────────────┐
│    EventBus     │ (Message Broker)
│  - Routes       │
│    events       │
└────────┬────────┘
         │ notifies
         ▼
┌─────────────────┐
│  GameManager    │ (Subscriber)
│  - Updates      │
│    game state   │
└─────────────────┘
```

## Best Practices

1. **Keep Events Immutable** - Use `final` fields and no setters
2. **Use Lombok @Getter** - For cleaner event classes
3. **Descriptive Event Names** - End with "Event" suffix
4. **Document Events** - Add JavaDoc explaining when/why event is fired
5. **Process Queue Regularly** - Call `processQueue()` in main game loop
6. **Clean Up** - Call `eventBus.clear()` when disposing resources

## Migration Guide

To migrate existing direct calls to event-driven:

1. Identify the interaction (e.g., InputManager → GameManager)
2. Create an appropriate event class
3. Replace direct method call with `eventBus.publish(new XxxEvent(...))`
4. Subscribe to event in target component
5. Remove the direct dependency reference

## Performance Considerations

- EventBus uses `ConcurrentHashMap` for thread-safe operations
- Event publication is O(n) where n = number of subscribers
- Queue processing is FIFO (First In, First Out)
- Consider using queuing for non-critical events during heavy processing

## Future Enhancements

Potential improvements:
- Event priority system
- Event filtering/interceptors
- Event history/replay for debugging
- Typed event channels
- Async event processing with thread pools
s no