package com.example;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    private Model model;
    private Gui gui;
    private List<IMemento> history; // Memento history
    private List<IMemento> future;

    public Controller(Gui gui) {
        this.model = new Model();
        this.gui = gui;
        this.history = new ArrayList<>();
        this.future = new ArrayList<>();
    }

    public void setOption(int optionNumber, int choice) {
        saveToHistory();
        model.setOption(optionNumber, choice);
    }

    public int getOption(int optionNumber) {
        return model.getOption(optionNumber);
    }

    public void setIsSelected(boolean isSelected) {
        saveToHistory();
        model.setIsSelected(isSelected);
    }

    public boolean getIsSelected() {
        return model.getIsSelected();
    }

    public void undo() {
        if (!history.isEmpty()) {
            System.out.println("Memento found in history");
            saveToFuture();
            System.out.println("Move Memento to future");
            IMemento previousState = history.remove(history.size() - 1);
            model.restoreState(previousState);
            gui.updateGui();
        }
    }

    private void saveToHistory() {
        IMemento currentState = model.createMemento();
        history.add(currentState);
        future = new ArrayList<>(); //reset future
    }

    private void moveToHistory() {
        IMemento currentState = model.createMemento();
        history.add(currentState);
    }

    public void redo() {
        if (!future.isEmpty()) {
            System.out.println("Memento found in future");
            moveToHistory();
            System.out.println("Move Memento to history");
            IMemento previousState = future.remove(future.size() - 1);
            model.restoreState(previousState);
            gui.updateGui();
        }
    }

    private void saveToFuture() {
        IMemento currentState = model.createMemento();
        future.add(currentState);
    }
    
}
