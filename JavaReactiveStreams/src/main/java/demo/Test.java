package demo;

import java.util.List;

import org.bson.types.ObjectId;

import com.mongodb.reactivestreams.client.MongoDatabase;

import dao.ZipDao;
import entities.Location;
import entities.Zip;
import utils.DBConnection;

public class Test {
	public static void main(String[] args) {
		DBConnection conn = new DBConnection();
		
		MongoDatabase db = conn.getDatabasse();
		
		ZipDao zipDao = new ZipDao(db);

		//InsertOne
/*		Zip zip = new Zip();
		zip.setId(ObjectId.get());
		zip.setCity("Việt Nam");
		zip.setState("VN");
		zip.setZip("123456");
		zip.setPop(123456);		
		Location loc = new Location(12.3,45.6);
		zip.setLoc(loc);		
		boolean result = zipDao.insertZip(zip);
		System.out.println(result);*/
	
		
		// tìm vị trí thứ 2
/*		List<Zip> result = zipDao.find(2);
		System.out.println(result);*/
		
		//Hiển thị n document từ document thứ k
/*		int k = 2;
		int n = 5;
		zipDao.findDocuments(k, n);*/
		
		//Tìm kiếm theo thành phố
//		 zipDao.findZipByCity("PALMER");
		
		
	}

}
