package de.marcobohl.portybungee.api;

public interface TaskHandler {

    int requestNewID();

    void addRunningTask(Callback callback);

    boolean removeRunningTask(Callback callback);

    Callback getRunningTask(int uid);
}
