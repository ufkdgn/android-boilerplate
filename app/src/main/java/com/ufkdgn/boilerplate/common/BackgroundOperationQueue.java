package com.ufkdgn.boilerplate.common;

import java.util.ArrayDeque;

public class BackgroundOperationQueue {
    private ArrayDeque<Runnable> queue = new ArrayDeque<>();

    public final synchronized void queueOperation(Runnable operation) {
        this.queue.add(operation);
    }

    public final synchronized Runnable dequeueOperation() {
        if (this.queue.size() > 0) {
            return this.queue.pop();
        } else {
            return null;
        }
    }
}
