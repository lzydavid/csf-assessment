package ibf2022.batch2.csf.backend.repositories;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import ibf2022.batch2.csf.backend.models.Bundle;
import ibf2022.batch2.csf.backend.models.BundleId;
import ibf2022.batch2.csf.backend.models.Upload;

@Repository
public class ArchiveRepository {

	private static final String ARCHIVE_COLLECTION = "archives"
;
	@Autowired
	private MongoTemplate mongoTemplate;
	
	// db.collection.insertOne({
	// 	"bundleId" : "c63c06ca",
	// 	"date" : "2023-05-12",
	// 	"title" : "stu",
	// 	"comments" : "hi",
	// 	"urls" : [
	// 		"e87e13e3.gif",
	// 		...
	// 	]
	// })
	public Optional<BundleId> recordBundle(String name,String title,String comment,List<String> urls) {

		String str = "https://lzydavidibf2022.sgp1.digitaloceanspaces.com/";

		urls = urls.stream().map(s->{return str+s;}).toList();

		String bundleId = UUID.randomUUID().toString().substring(0, 8);
		Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateString = dateFormat.format(currentDate);

		Document doc = new Document();
		doc.put("bundleId", bundleId);
		doc.put("date", currentDateString);
		doc.put("title", title);
		doc.put("name", name);
		doc.put("comments", comment);
		doc.put("urls",urls);

		Document insertedDoc = mongoTemplate.insert(doc, ARCHIVE_COLLECTION);
		if(insertedDoc!=null){
			BundleId id = new BundleId();
			id.setBundleId(bundleId);
			return Optional.of(id);
		}

		return Optional.empty();
	}

	// db.archives.find({
	// 	bundleId:'{bundleId}'
	// })
	public Upload getBundleByBundleId(String bundleId) {

		Query q = new Query(Criteria.where("bundleId").is(bundleId));

		Document doc = mongoTemplate.findOne(q, Document.class, ARCHIVE_COLLECTION);

		Upload u = new Upload();
		u.setBundleID(bundleId);
		u.setDate(doc.getString("date"));
		u.setTitle(doc.getString("title"));
		u.setName(doc.getString("name"));
		u.setComments(doc.getString("comments"));
		List<String> s = (List<String>) doc.get("urls");
		u.setUrls(s);

		return u;
	}

	// db.archives.find({}).sort(
    // {title:-1},{date:1}
	// )
	public List<Bundle> getBundles(/* any number of parameters here */) {

		List<Bundle> bundles = new ArrayList<>();

		Query q = new Query().with(Sort.by(Sort.Direction.ASC,"title")).with(Sort.by(Sort.Direction.DESC,"date"));

		List<Document> documents = mongoTemplate.find(q, Document.class, ARCHIVE_COLLECTION);

		for (Document d : documents) {
			Bundle b = new Bundle();
			b.setTitle(d.getString("title"));
			b.setDate(d.getString("date"));
			bundles.add(b);
		}

		return bundles;
	}


}
