package com.mygdx.game.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Array;

/**
 * GameStateManager - Manages a stack of game states
 * Supports push/pop operations for state transitions and overlays
 * <p>
 * Examples:
 * - Push(PauseState) over PlayingState -> Pause menu overlay
 * - Push(InventoryState) over PlayingState -> Inventory screen
 * - Change(MenuState) -> Replace current state completely
 */
public class GameStateManager {
    private final Array<GameState> stateStack;
    private final Array<StateAction> pendingActions;
    private final InputMultiplexer inputMultiplexer;

    public GameStateManager() {
        this.stateStack = new Array<>();
        this.pendingActions = new Array<>();
        this.inputMultiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    /**
     * Push a new state onto the stack (current state is paused)
     */
    public void pushState(GameState state) {
        pendingActions.add(new StateAction(StateActionType.PUSH, state));
    }

    /**
     * Pop the current state (previous state is resumed)
     */
    public void popState() {
        pendingActions.add(new StateAction(StateActionType.POP, null));
    }

    /**
     * Replace the current state with a new one
     */
    public void changeState(GameState newState) {
        pendingActions.add(new StateAction(StateActionType.CHANGE, newState));
    }

    /**
     * Clear all states and set a new initial state
     */
    public void setInitialState(GameState state) {
        clearAllStates();
        pushState(state);
        processPendingActions();
    }

    /**
     * Update all active states
     */
    public void update(float delta) {
        processPendingActions();

        // Update states from bottom to top, stopping at first blocking state
        for (int i = stateStack.size - 1; i >= 0; i--) {
            GameState state = stateStack.get(i);
            state.update(delta);

            if (state.blocksUpdate()) {
                break;
            }
        }
    }

    /**
     * Render all visible states
     */
    public void render(float delta) {
        // Find the lowest state that blocks rendering
        int startIndex = stateStack.size - 1;
        for (int i = stateStack.size - 1; i >= 0; i--) {
            if (stateStack.get(i).blocksRender()) {
                startIndex = i;
                break;
            }
        }

        // Render from bottom to top
        for (int i = startIndex; i < stateStack.size; i++) {
            stateStack.get(i).render(delta);
        }
    }

    /**
     * Process pending state changes
     */
    private void processPendingActions() {
        if (pendingActions.size == 0) return;

        for (StateAction action : pendingActions) {
            switch (action.type) {
                case PUSH:
                    executePush(action.state);
                    break;
                case POP:
                    executePop();
                    break;
                case CHANGE:
                    executeChange(action.state);
                    break;
            }
        }

        pendingActions.clear();
    }

    /**
     * Execute push operation
     */
    private void executePush(GameState state) {
        // Pause current state if exists
        if (stateStack.size > 0) {
            stateStack.peek().onPause();
        }

        // Initialize and enter new state
        state.initialize();
        stateStack.add(state);
        state.onEnter();

        // Update input processor
        updateInputProcessor();

        Gdx.app.log("StateManager", "Pushed state: " + state.getClass().getSimpleName() + " (Stack size: " + stateStack.size + ")");
    }

    /**
     * Execute pop operation
     */
    private void executePop() {
        if (stateStack.size == 0) {
            Gdx.app.error("StateManager", "Cannot pop: state stack is empty");
            return;
        }

        // Exit and remove current state
        GameState currentState = stateStack.pop();
        currentState.onExit();

        // Resume previous state if exists
        if (stateStack.size > 0) {
            stateStack.peek().onResume();
        }

        // Update input processor
        updateInputProcessor();

        Gdx.app.log("StateManager", "Popped state: " + currentState.getClass().getSimpleName() + " (Stack size: " + stateStack.size + ")");
    }

    /**
     * Execute change operation
     */
    private void executeChange(GameState newState) {
        // Exit current state if exists
        if (stateStack.size > 0) {
            GameState currentState = stateStack.pop();
            currentState.onExit();
            Gdx.app.log("StateManager", "Exited state: " + currentState.getClass().getSimpleName());
        }

        // Push new state
        executePush(newState);
    }

    /**
     * Update input processor with current state's processor
     */
    private void updateInputProcessor() {
        inputMultiplexer.clear();

        if (stateStack.size > 0) {
            InputProcessor processor = stateStack.peek().getInputProcessor();
            if (processor != null) {
                inputMultiplexer.addProcessor(processor);
            }
        }
    }

    /**
     * Clear all states
     */
    private void clearAllStates() {
        while (stateStack.size > 0) {
            GameState state = stateStack.pop();
            state.onExit();
        }
        updateInputProcessor();
        Gdx.app.log("StateManager", "Cleared all states");
    }

    /**
     * Handle window resize for all states
     */
    public void resize(int width, int height) {
        for (GameState state : stateStack) {
            state.resize(width, height);
        }
    }

    /**
     * Get the current active state
     */
    public GameState getCurrentState() {
        return stateStack.size > 0 ? stateStack.peek() : null;
    }

    /**
     * Get the number of states in the stack
     */
    public int getStateCount() {
        return stateStack.size;
    }

    /**
     * Check if a specific state type is in the stack
     */
    public boolean hasState(Class<? extends GameState> stateClass) {
        for (GameState state : stateStack) {
            if (stateClass.isInstance(state)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Dispose all states and cleanup
     */
    public void dispose() {
        Gdx.app.log("StateManager", "Disposing state manager...");
        for (GameState state : stateStack) {
            state.dispose();
        }
        stateStack.clear();
        pendingActions.clear();
    }

    /**
     * State action types
     */
    private enum StateActionType {
        PUSH, POP, CHANGE
    }

    /**
         * State action data
         */
        private record StateAction(StateActionType type, GameState state) {
    }
}

