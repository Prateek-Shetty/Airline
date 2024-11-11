package com.airline.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.ReplaceOptions;

import org.bson.Document;

import java.io.IOException;
import java.nio.file.*;
import java.util.Map;

public class FileWatcher {

    private final Path folderPath;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public FileWatcher(String folderPath) {
        this.folderPath = Paths.get(folderPath);
    }

    public void watch() throws IOException, InterruptedException {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        folderPath.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

        System.out.println("Watching folder: " + folderPath);

        while (true) {
            WatchKey key = watchService.take();

            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();
                if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
                    Path changedFilePath = folderPath.resolve((Path) event.context());
                    updateMongoDB(changedFilePath);
                }
            }
            key.reset();
        }
    }

    private void updateMongoDB(Path filePath) {
        String fileName = filePath.getFileName().toString();
        String collectionName = fileName.replace(".json", "");

        MongoCollection<Document> collection = MongoDBUtil.getCollection(collectionName);

        try {
            Map<String, Object> jsonMap = objectMapper.readValue(filePath.toFile(), Map.class);
            Document document = new Document(jsonMap);

            // Upsert the data (insert if doesn't exist, update if it does)
            collection.replaceOne(new Document("_id", fileName), document, new ReplaceOptions().upsert(true));
            System.out.println("Updated MongoDB with data from: " + fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

