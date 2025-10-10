//package com.mygdx.game.old_code_base.events;
//
//import com.badlogic.gdx.Gdx;
//import lombok.Getter;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * Logs all events during a game session for debugging and analytics.
// */
//@Getter
//public class EventLogger {
//    private static EventLogger instance;
//
//    private final List<EventLogEntry> eventLog;
//    private final Map<Class<? extends Event>, Integer> eventCounts;
//    private final long sessionStartTime;
//    private boolean enabled;
//
//    private EventLogger() {
//        this.eventLog = new ArrayList<>();
//        this.eventCounts = new HashMap<>();
//        this.sessionStartTime = System.currentTimeMillis();
//        this.enabled = true;
//    }
//
//    /**
//     * Initialize the singleton instance
//     */
//    public static void initialize() {
//        if (instance == null) {
//            instance = new EventLogger();
//            Gdx.app.log("EventLogger", "Event logging system initialized");
//        }
//    }
//
//    /**
//     * Get the singleton instance
//     */
//    public static EventLogger getInstance() {
//        if (instance == null) {
//            initialize();
//        }
//        return instance;
//    }
//
//    /**
//     * Log an event
//     */
//    public void logEvent(Event event) {
//        if (!enabled) {
//            return;
//        }
//
//        Class<? extends Event> eventClass = event.getClass();
//
//        // Create log entry
//        EventLogEntry entry = new EventLogEntry(event, eventClass.getSimpleName(), System.currentTimeMillis() - sessionStartTime, event.getTimestamp());
//
//        eventLog.add(entry);
//
//        // Update event count
//        eventCounts.put(eventClass, eventCounts.getOrDefault(eventClass, 0) + 1);
//
//        // Log to console
//        Gdx.app.log("Event", String.format("[%dms] %s", entry.sessionTime(), entry.eventName()));
//    }
//
//    /**
//     * Get the count of a specific event type
//     */
//    public int getEventCount(Class<? extends Event> eventClass) {
//        return eventCounts.getOrDefault(eventClass, 0);
//    }
//
//    /**
//     * Get total event count
//     */
//    public int getTotalEventCount() {
//        return eventLog.size();
//    }
//
//    /**
//     * Get events by type
//     */
//    public List<EventLogEntry> getEventsByType(Class<? extends Event> eventClass) {
//        List<EventLogEntry> result = new ArrayList<>();
//        for (EventLogEntry entry : eventLog) {
//            if (entry.event().getClass().equals(eventClass)) {
//                result.add(entry);
//            }
//        }
//        return result;
//    }
//
//    /**
//     * Print event statistics to console
//     */
//    public void printStatistics() {
//        Gdx.app.log("EventLogger", "=== Event Statistics ===");
//        Gdx.app.log("EventLogger", "Total Events: " + getTotalEventCount());
//        Gdx.app.log("EventLogger", "Event Types: " + eventCounts.size());
//        Gdx.app.log("EventLogger", "Session Duration: " + (System.currentTimeMillis() - sessionStartTime) + "ms");
//        Gdx.app.log("EventLogger", "");
//        Gdx.app.log("EventLogger", "Event Breakdown:");
//
//        eventCounts.entrySet().stream().sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue())).forEach(entry -> {
//            Gdx.app.log("EventLogger", String.format("  %s: %d", entry.getKey().getSimpleName(), entry.getValue()));
//        });
//    }
//
//    /**
//     * Clear all logged events
//     */
//    public void clear() {
//        eventLog.clear();
//        eventCounts.clear();
//        Gdx.app.log("EventLogger", "Event log cleared");
//    }
//
//    /**
//     * Enable or disable logging
//     */
//    public void setEnabled(boolean enabled) {
//        this.enabled = enabled;
//        Gdx.app.log("EventLogger", "Event logging " + (enabled ? "enabled" : "disabled"));
//    }
//
//    /**
//     * Get the last N events
//     */
//    public List<EventLogEntry> getLastEvents(int count) {
//        int size = eventLog.size();
//        int start = Math.max(0, size - count);
//        return new ArrayList<>(eventLog.subList(start, size));
//    }
//
//    /**
//     * Entry in the event log
//     */
//    public record EventLogEntry(Event event, String eventName, long sessionTime, long timestamp) {
//
//        @Override
//        public String toString() {
//            return String.format("[%dms] %s", sessionTime, eventName);
//        }
//    }
//}
//
