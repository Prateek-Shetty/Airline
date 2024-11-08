package com.airline;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class MongoDBConnector {

    private static MongoClient mongoClient;
    private static MongoDatabase database;

    // Connect to MongoDB
    public static void connect() {
        // Replace with your MongoDB URI if using MongoDB Atlas or a remote database
        mongoClient = MongoClients.create("mongodb://localhost:27017");// Localhost default
        database = mongoClient.getDatabase("airlineDB"); // Replace with your database name
        System.out.println("Connected to MongoDB!");
    }

    // Example method to insert a document into a collection
    public static void insertFlightData() {
        MongoCollection<Document> collection = database.getCollection("flights"); // Replace with your collection name

        // Create a new document
        Document doc = new Document("flightNumber", "AI202")
                            .append("origin", "Delhi")
                            .append("destination", "New York")
                            .append("departureTime", "2024-12-01T08:00:00");

        // Insert the document into the collection
        collection.insertOne(doc);
        System.out.println("Flight data inserted!");
    }

    // Example method to retrieve documents from the collection
    public static void fetchFlightData() {
        MongoCollection<Document> collection = database.getCollection("flights"); // Replace with your collection name

        // Retrieve all documents from the collection
        for (Document doc : collection.find()) {
            System.out.println(doc.toJson());
        }
    }

    // Disconnect from MongoDB
    public static void disconnect() {
        if (mongoClient != null) {
            mongoClient.close();
            System.out.println("Disconnected from MongoDB.");
        }
    }
}
