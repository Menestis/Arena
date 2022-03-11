package com.pyralia.arena.utils.mongo;

import com.mongodb.*;
import com.pyralia.arena.ArenaAPI;
import org.bukkit.Bukkit;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author Rhodless
 */
public class DatabaseManager {

    private final MongoClient mongoClient;
    private final DB database;
    private final DBCollection arenaCollection;

    public DatabaseManager() {
        mongoClient = new MongoClient(new MongoClientURI("mongodb://root:Wz3AavxEQereYPKZXpc6pD8ttuYSEiopwMSAK2cfjdfxfoZYmF5zRN6eXz5guCyC@37.59.33.176:27017/Pyralia"));
        database = mongoClient.getDB("Pyralia");
        arenaCollection = database.getCollection("arenaStats");
    }

    public DB getDatabase() {
        return database;
    }
    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public DBCollection getArenaCollection() {
        return arenaCollection;
    }

    public Object getFromArenaCollection(UUID uuid, String string) {
        try {
            CompletableFuture<Object> task = new CompletableFuture<>();
            CompletableFuture.runAsync(() -> {
                DBObject query = new BasicDBObject("uuid", uuid.toString());
                DBObject cplayer = arenaCollection.find(query).one();
                task.complete(cplayer.get(string));
            });
            return task.get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public void createProfile(UUID uuid) {
        if(arenaCollection.find(new BasicDBObject("uuid", uuid.toString())).one() != null)
            return;
        try {
            Bukkit.getScheduler().runTaskAsynchronously(ArenaAPI.getApi(), () -> {
                DBObject profile = new BasicDBObject("_id", uuid.toString())
                        .append("uuid", uuid.toString())
                        .append("kills", 0)
                        .append("deaths", 0);
                arenaCollection.insert(profile);
            });
        } catch (Exception e) {
            ArenaAPI.getApi().getLogger().severe("Failed to create a profile for " + uuid + "...");
            e.printStackTrace();
        }
    }

    public void setArenaCollection(UUID uuid, String string, Object toSet) {
        CompletableFuture.runAsync(() -> {
            arenaCollection.update(new BasicDBObject("_id", uuid.toString()),
                    new BasicDBObject("$set", new BasicDBObject(string, toSet)));
        });
    }
}
