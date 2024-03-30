package utils;

import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoDatabase;

//Include the following static imports before your class definition
import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class DBConnection {
	private MongoDatabase mongoDatabase;
    public	DBConnection() {
    	MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017/");
    	
    	CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
		CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
		
	    mongoDatabase = mongoClient.getDatabase("sample_training").withCodecRegistry(pojoCodecRegistry);
    }
    
    public MongoDatabase getDatabasse() {
    	return mongoDatabase;
    }

}
