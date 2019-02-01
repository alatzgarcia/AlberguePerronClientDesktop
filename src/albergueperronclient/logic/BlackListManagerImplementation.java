/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.logic;

import albergueperronclient.exceptions.CreateException;
import albergueperronclient.exceptions.DeleteException;
import albergueperronclient.exceptions.ReadException;
import albergueperronclient.exceptions.UpdateException;
import albergueperronclient.modelObjects.UserBeanMongo;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.bson.Document;
import org.glassfish.jersey.internal.guava.Lists;

/**
 *
 * @author Alatz
 */
public class BlackListManagerImplementation implements BlackListManager {
    private static final Logger LOGGER= Logger.
            getLogger("albergueperronclient.logic.BlackListManagerImplementation");
    
    @Override
    public List<UserBeanMongo> findAllUsersFromBlackList() throws ReadException {
        ArrayList<UserBeanMongo> users = null;
        MongoClient mongoClient = null;
        try{
            mongoClient = MongoClients.create("mongodb://localhost:27017/AlberguePerronMongoDB");
            //MongoClient mongoClient = MongoClients.create("mongodb://admin:abcd*1234@localhost:27017/AlberguePerronMongoDB");
            MongoDatabase mongoDB = mongoClient.getDatabase("AlberguePerronMongoDB");
            MongoCollection<Document> collection = mongoDB.getCollection("BlackList");
        
            //FindIterable<Document> iterableList = collection.find();
            List<Document> documents = (List<Document>) collection.find().into(
				new ArrayList<Document>());
            users = new ArrayList<UserBeanMongo>();
            for(Document doc: documents){
                String id= (doc.get("id").toString());
                String name = (doc.get("name").toString());
                String surname1 = (doc.get("surname1").toString());
                String surname2 = (doc.get("surname2").toString());
                String reason = (doc.get("reason").toString());
            
                UserBeanMongo user = new UserBeanMongo(id,name,surname1,surname2,reason);
                users.add(user);
            }
        
            
        } catch(Exception ex){
            LOGGER.severe("Error finding all users on the blacklist: " + ex.getMessage());
        } finally{
            mongoClient.close();
            return users;
        }
        
    }

    @Override
    public void addUserToBlackList(UserBeanMongo user) throws CreateException { 
        MongoClient mongoClient = null;
        try{
            mongoClient = MongoClients.create("mongodb://localhost:27017/AlberguePerronMongoDB");
            //mongoClient = MongoClients.create("mongodb://adtUser:abcd*1234@localhost:27017/AlberguePerronMongoDB");
            MongoDatabase mongoDB = mongoClient.getDatabase("AlberguePerronMongoDB");
            MongoCollection<Document> collection = mongoDB.getCollection("BlackList");
        
        
            Document document = new Document();
            document.put("id", user.getId());
            document.put("name", user.getName());
            document.put("surname1", user.getSurname1());
            document.put("surname2", user.getSurname2());
            document.put("reason", user.getReason());
        
            collection.insertOne(document);
        } catch(Exception ex){
            LOGGER.severe("Error adding new user to the blacklist: " + ex.getMessage());
        } finally {
            mongoClient.close();
        }
    }

    @Override
    public void updateUserOnBlackList(UserBeanMongo user) throws UpdateException {
        MongoClient mongoClient = null;
        try{
            mongoClient = MongoClients.create("mongodb://localhost:27017/AlberguePerronMongoDB");
            //mongoClient = MongoClients.create("mongodb://adtUser:abcd*1234@localhost:27017/AlberguePerronMongoDB");
            MongoDatabase mongoDB = mongoClient.getDatabase("AlberguePerronMongoDB");
            MongoCollection<Document> collection = mongoDB.getCollection("BlackList");
        
            Document docToSet = new Document("$set", new Document("reason",
                    user.getReason()));
            collection.updateOne(new Document("id", user.getId()), docToSet);
        } catch(Exception ex){
            LOGGER.severe("Error updating the user on the blacklist: " + ex.getMessage());
        } finally {
            mongoClient.close();
        }
    }

    @Override
    public void deleteUserFromBlackList(String id) throws DeleteException {
        MongoClient mongoClient = null;
        try{
            mongoClient = MongoClients.create("mongodb://localhost:27017/AlberguePerronMongoDB");
            //mongoClient = MongoClients.create("mongodb://adtUser:abcd*1234@localhost:27017/AlberguePerronMongoDB");
            MongoDatabase mongoDB = mongoClient.getDatabase("AlberguePerronMongoDB");
            MongoCollection<Document> collection = mongoDB.getCollection("BlackList");
        
            collection.deleteOne(Filters.eq("id", id));
            mongoClient.close();
        } catch(Exception ex){
            LOGGER.severe("Error deleting the user from the blacklist: " + ex.getMessage());
        } finally {
            mongoClient.close();
        }
    }
}
