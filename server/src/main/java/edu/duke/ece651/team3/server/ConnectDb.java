package edu.duke.ece651.team3.server;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class ConnectDb {
    private static MongoClient mongoClient;


    public static synchronized MongoClient getMongoClient() {
        if (mongoClient == null) {
            String connectionString = "mongodb+srv://mongo:1234@cluster0.gpoxcke.mongodb.net/?retryWrites=true&w=majority";
            ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();
            MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();
            mongoClient = MongoClients.create(settings);
        }
        return mongoClient;
    }
}
