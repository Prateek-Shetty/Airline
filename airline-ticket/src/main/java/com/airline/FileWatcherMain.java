package com.airline;

import java.io.IOException;

import com.airline.util.FileWatcher;

public class FileWatcherMain {
    public static void main(String[] args) {
        FileWatcher watcher = new FileWatcher("\"booking_confirmation.json\"\r\n" + //
                        "");

        try {
            watcher.watch();
        } catch (IOException | InterruptedException e) {
            ((Throwable) e).printStackTrace();
        }
    }
}
