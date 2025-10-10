//package com.mygdx.game.old_code_base.events;
//
//import lombok.Getter;
//
//import java.util.*;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * Central event bus for decoupling game systems.
// * Allows components to communicate without direct references.
// */
//public class EventBus {
//    private static EventBus instance;
//
//    // Map of event types to their listeners
//    private final Map<Class<? extends Event>, List<EventListener<? extends Event>>> listeners;
//
//    // Queue for events to be processed
//    private final Queue<Event> eventQueue;
//
//    /**
//     * -- GETTER --
//     * Get the event logger instance
//     */
//    // Event logger for tracking events during session
//    @Getter
//    private final EventLogger eventLogger;
//
//    private EventBus() {
//        this.listeners = new ConcurrentHashMap<>();
//        this.eventQueue = new LinkedList<>();
//        this.eventLogger = EventLogger.getInstance();
//    }
//
//    /**
//     * Get the singleton instance of the EventBus
//     */
//    public static EventBus getInstance() {
//        if (instance == null) {
//            instance = new EventBus();
//        }
//        return instance;
//    }
//
//    /**
//     * Subscribe to a specific event type
//     */
//    public <T extends Event> void subscribe(Class<T> eventType, EventListener<T> listener) {
//        listeners.computeIfAbsent(eventType, k -> new ArrayList<>()).add(listener);
//    }
//
//    /**
//     * Unsubscribe from a specific event type
//     */
//    public <T extends Event> void unsubscribe(Class<T> eventType, EventListener<T> listener) {
//        List<EventListener<? extends Event>> eventListeners = listeners.get(eventType);
//        if (eventListeners != null) {
//            eventListeners.remove(listener);
//        }
//    }
//
//    /**
//     * Publish an event immediately (synchronous)
//     */
//    @SuppressWarnings("unchecked")
//    public <T extends Event> void publish(T event) {
//        // Log the event
//        eventLogger.logEvent(event);
//
//        List<EventListener<? extends Event>> eventListeners = listeners.get(event.getClass());
//        if (eventListeners != null) {
//            for (EventListener<? extends Event> listener : new ArrayList<>(eventListeners)) {
//                // Skip cancelled events
//                if (event.isCancelled()) {
//                    break;
//                }
//                ((EventListener<T>) listener).onEvent(event);
//            }
//        }
//    }
//
//    /**
//     * Queue an event for later processing (asynchronous)
//     */
//    public void queue(Event event) {
//        eventQueue.offer(event);
//    }
//
//    /**
//     * Process all queued events
//     */
//    public void processQueue() {
//        while (!eventQueue.isEmpty()) {
//            Event event = eventQueue.poll();
//            if (event != null) {
//                publish(event);
//            }
//        }
//    }
//
//    /**
//     * Clear all listeners (useful for cleanup)
//     */
//    public void clear() {
//        listeners.clear();
//        eventQueue.clear();
//    }
//
//}
