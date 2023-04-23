package edu.duke.ece651.team3.shared;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.slf4j.LoggerFactory;

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
        ((LoggerContext) LoggerFactory.getILoggerFactory()).getLogger("org.mongodb.driver").setLevel(Level.ERROR);
        return mongoClient;
    }

    public static void connectToDb(String dbName) {
        try {
            System.out.print("dbnames: ");
            for (String name: mongoClient.listDatabaseNames()) {
                System.out.print(name + ", ");
            }

            MongoDatabase database = mongoClient.getDatabase(dbName);
            System.out.print("\nIn riscDB: collection names: ");
            for (String cname: database.listCollectionNames()) {
                System.out.print(cname + " ");
            }
            System.out.println("\nSuccessfully connected to MongoDB riscDB!");
        } catch (MongoException e) {
            e.printStackTrace();
        }
    }

}
