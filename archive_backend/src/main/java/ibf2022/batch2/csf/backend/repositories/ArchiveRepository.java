package ibf2022.batch2.csf.backend.repositories;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.domain.Sort;
import ibf2022.batch2.csf.backend.models.Bundle;
import org.springframework.stereotype.Repository;


@Repository
public class ArchiveRepository {

	//TODO: Task 4
	// You are free to change the parameter and the return type
	// Do not change the method's name
	// Write the native mongo query that you will be using in this method
	//

	// db.archives.insertOne({
	// 	bundleId: UUID().toString().substr(0, 8),
	// 	date: new Date(),
	// 	title: "",
	// 	name: "",
	// 	comments: "Example comment",
	// 	urls: ["url1", "url2"...]
	//   })

	//
    @Autowired
    private MongoTemplate mongoTemplate;

	public Bundle recordBundle(Bundle bundle) {
		return mongoTemplate.insert(bundle, "archives");
	}

	//TODO: Task 5
	// You are free to change the parameter and the return type
	// Do not change the method's name
	// Write the native mongo query that you will be using in this method
	//
	//
	public Bundle getBundleByBundleId(String bundleId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("bundleId").is(bundleId));
		return mongoTemplate.findOne(query, Bundle.class, "archives");	
	}

	//TODO: Task 6
	// You are free to change the parameter and the return type
	// Do not change the method's name
	// Write the native mongo query that you will be using in this method
	//
	//
	public ArrayList<Bundle> getBundles() {
		Query query = new Query();
		query.with(Sort.by(Sort.Order.desc("date"), Sort.Order.asc("title")));
		query.fields().include("bundleId").include("date").include("title");
		return new ArrayList<>(mongoTemplate.find(query, Bundle.class, "archives"));
	}

}