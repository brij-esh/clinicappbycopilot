package com.brijesh.copilot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Task {
    private String description;
    private boolean done;

    public Task(String description) {
        this.description = description;
        this.done = false;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return done;
    }

    public void markAsDone() {
        this.done = true;
    }
}

public class TaskManager {
    private final List<Task> tasks;

    public TaskManager() {
        this.tasks = Collections.synchronizedList(new ArrayList<>());
    }

    public synchronized void addTask(String description) {
        Task task = new Task(description);
        tasks.add(task);
    }

    public synchronized List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    public synchronized void listTasks() {
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            System.out.println((i + 1) + ". " + task.getDescription() + " - " + (task.isDone() ? "Done" : "Not Done"));
        }
    }

    public synchronized void markTaskAsDone(int index) {
        if (index < 1 || index > tasks.size()) {
            throw new IllegalArgumentException("Invalid task index");
        }
        Task task = tasks.get(index - 1);
        task.markAsDone();
        System.out.println("Task marked as done: " + task.getDescription());
    }

    public synchronized void removeLastTask() {
        if (tasks.isEmpty()) {
            throw new IllegalStateException("No tasks to remove");
        }
        tasks.remove(tasks.size() - 1);
        System.out.println("Last task removed");
    }
}