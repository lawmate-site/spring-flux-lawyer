package site.lawmate.lawyer.config;
//
//import com.example.webflux.lawyer.domain.LawyerModel;
//import com.example.webflux.lawyer.repository.LawyerRepository;
//import org.bson.BsonDocument;
//import org.bson.BsonInt64;
//import org.bson.Document;
//import org.bson.conversions.Bson;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.mongodb.ConnectionString;
//import com.mongodb.MongoClientSettings;
//import com.mongodb.MongoException;
//import com.mongodb.ServerApi;
//import com.mongodb.ServerApiVersion;
//import com.mongodb.client.MongoClient;
//import com.mongodb.client.MongoClients;
//import com.mongodb.client.MongoCollection;
//import com.mongodb.client.MongoDatabase;
//
//import reactor.core.publisher.Mono;
//
//import java.util.ArrayList;
//import java.util.stream.IntStream;
//
//@Configuration
//public class LawyersCollectionConfig {
//    @Bean
//    CommandLineRunner initUsers(LawyerRepository repo) {
//        System.out.println(" >>>> 1 실행 >>>>");
//        String uri = "mongodb://root:root@101.79.10.130:27017/lawmatedb?authSource=admin";
//        // Construct a ServerApi instance using the ServerApi.builder() method
//        ServerApi serverApi = ServerApi.builder().version(ServerApiVersion.V1).build();
//        MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(new ConnectionString(uri)).serverApi(serverApi).build();
//        // Create a new client and connect to the server
//        try (MongoClient mongoClient = MongoClients.create(settings)) {
//            System.out.println(" DB 접속 ...");
//            MongoDatabase database = mongoClient.getDatabase("lawmatedb");
//            System.out.println(" Test 컬렉션 접속 ...");
//            try {
//                // Send a ping to confirm a successful connection
//                Bson command = new BsonDocument("ping", new BsonInt64(1));
//                Document commandResult = database.runCommand(command);
//                System.out.println(" 핑 연 결 ...");
//                System.out.println("Pinged your deployment. You successfully connected to MongoDB!");
//                System.out.println(commandResult);
//
////                boolean existsToken = database.listCollectionNames().into(new ArrayList<String>()).contains("tokens");
////                if (!existsToken) {
////
////                    MongoCollection<Document> collection = database.getCollection("tokens");
////                    Document token = new Document().append("refreshToken", "example").append("email", "ex@gmail.com").append("expiration", "2021-12-31T00:00:00.000Z");
////                    collection.insertOne(token);
////
////
////                }
//
//                boolean exists = database.listCollectionNames().into(new ArrayList<String>()).contains("lawyers");
//                if (!exists) {
//
//                    MongoCollection<Document> collection = database.getCollection("lawyers");
//                    Document lawyer = new Document().append("username", "lawyer").append("password", "password").append("email", "lawyer@test.com").append("name", "Kim").append("lawyerNo", "7777-8888");
//                    collection.insertOne(lawyer);
//
//
//                }
//                System.out.println(" >>>> 8 컬렉션 생성 확인 >>>>");
//            } catch (MongoException me) {
//                System.out.println(" >>>> 9 에러 발생 >>>>");
//                System.err.println(me);
//            }
//        }
//
//        return args -> {
//            IntStream.range(0, 1).forEach(i -> {
//                Mono<LawyerModel> userMono = Mono.just(LawyerModel.builder().email(i + "@test.com").username("James" + i).name("Byden " + i).password("aaa").build());
//
//                repo.insert(userMono);
//            });
//        };
//    }
//}