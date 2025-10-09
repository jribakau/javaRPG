## How to Work With This System

### Adding a New Event Handler
1. Create your event class (e.g., `MyNewEvent.java`)
2. Add the handler method to `GameEventHandler` (if game logic) or `GameManager` (if UI)
3. Subscribe to it in the appropriate `subscribeTo*Events()` method
4. That's it!

### Modifying Game State
- The `Level` object is the single source of truth for game state
- Access it via `gameManager.getLevel()` or directly in `GameEventHandler`
- Events flow through the EventBus for loose coupling

### Debugging
- Toggle developer menu: `isDevMenuOpen` flag
- Toggle entity debug rendering: `isEntityDebug` flag
- Check event flow via `EventLogger` accessible through `EventBus`

## Performance Considerations
- Event processing is deferred until `eventBus.processQueue()` in the update loop
- Entity visibility is calculated before input to avoid unnecessary processing
- Camera updates after input to ensure smooth following

## Future Enhancements
Consider these improvements for continued evolution:
1. **Extract Debug State**: Create a `DebugState` class to manage all debug flags
2. **Factory Pattern**: Use factories for level and entity creation
3. **Configuration Object**: Pass a single config object instead of 5 parameters to constructor
4. **State Machine**: If game complexity grows, consider a proper state machine pattern
5. **Dependency Injection**: Consider using a DI framework for cleaner initialization
