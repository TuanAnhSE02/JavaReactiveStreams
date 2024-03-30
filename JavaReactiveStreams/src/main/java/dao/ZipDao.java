package dao;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.reactivestreams.client.FindPublisher;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;

import entities.Zip;

public class ZipDao {
	private MongoCollection<Zip> zipCollection;
	
	public ZipDao(MongoDatabase db) {
		zipCollection = db.getCollection("zips", Zip.class);
	}
	
	public boolean insertZip(Zip zip) {
		AtomicBoolean result = new AtomicBoolean(false);
		
		CountDownLatch latch = new CountDownLatch(1);
		
		Publisher<InsertOneResult> publisher = zipCollection.insertOne(zip);
		
		Subscriber<InsertOneResult> subscriber = new Subscriber<InsertOneResult>() {

			@Override
			public void onSubscribe(Subscription s) {
				s.request(1);
				
			}

			@Override
			public void onNext(InsertOneResult t) {
				System.out.println("onNext: " + t);
				result.set(t.getInsertedId() != null ? true : false);
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
				
			}

			@Override
			public void onComplete() {
				System.out.println("Complete");
				latch.countDown();
			}
		};
		
		publisher.subscribe(subscriber);
		try {
			latch.await();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.get();
		
	}
	
	public List<Zip> find(int limit){
		
		List<Zip> result = new ArrayList<Zip>();
		
		CountDownLatch latch = new CountDownLatch(1);
			
		FindPublisher<Zip> publisher = zipCollection.find().limit(limit);
		
		Subscriber<Zip> subscriber = new Subscriber<Zip>() {
			
			private Subscription s;

			@Override
			public void onSubscribe(Subscription s) {
				this.s = s;
				s.request(1);	
			}
			
			@Override
			public void onNext(Zip t) {
				result.add(t);
				this.s.request(1);
				
			}
			
			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
				
			}
			
			@Override
			public void onComplete() {
				latch.countDown();
				
			}
		};
		
		publisher.subscribe(subscriber);
		try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
		return result;
		
	}
	
	 public void findDocuments(int k, int n) {
	        CountDownLatch latch = new CountDownLatch(1);
	        List<Zip> results = new ArrayList<>();

	        FindPublisher<Zip> findPublisher = zipCollection.find().skip(k).limit(n);
	        Subscriber<Zip> subscriber = new Subscriber<Zip>() {
	            private Subscription subscription;

	            @Override
	            public void onSubscribe(Subscription s) {
	                subscription = s;
	                subscription.request(1); // Request the first document
	            }

	            @Override
	            public void onNext(Zip zip) {
	                results.add(zip);
	                subscription.request(1); // Request the next document
	            }

	            @Override
	            public void onError(Throwable t) {
	                t.printStackTrace();
	                latch.countDown();
	            }

	            @Override
	            public void onComplete() {
	                for (Zip zip : results) {
	                    System.out.println(zip);
	                }
	                latch.countDown();
	            }
	        };
	        findPublisher.subscribe(subscriber);
	        try {
	            latch.await();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }
	 public void findZipByCity(String city) {
		 CountDownLatch latch = new CountDownLatch(1);
	        FindPublisher<Zip> findPublisher = zipCollection.find(Filters.eq("city", city));
	        Subscriber<Zip> subscriber = new Subscriber<Zip>() {
	            private Subscription subscription;

	            @Override
	            public void onSubscribe(Subscription s) {
	                subscription = s;
	                subscription.request(1);
	            }

	            @Override
	            public void onNext(Zip zip) {
	                System.out.println(zip);
	                subscription.request(1);
	            }

	            @Override
	            public void onError(Throwable t) {
	                t.printStackTrace();
	            }

	            @Override
	            public void onComplete() {
	                System.out.println("Complete");
	            }
	        };

	        findPublisher.subscribe(subscriber);
	        try {
	            latch.await();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }
	 
	
	

}
