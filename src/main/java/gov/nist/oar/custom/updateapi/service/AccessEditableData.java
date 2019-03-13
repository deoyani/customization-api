/**
 * This software was developed at the National Institute of Standards and Technology by employees of
 * the Federal Government in the course of their official duties. Pursuant to title 17 Section 105
 * of the United States Code this software is not subject to copyright protection and is in the
 * public domain. This is an experimental system. NIST assumes no responsibility whatsoever for its
 * use by other parties, and makes no guarantees, expressed or implied, about its quality,
 * reliability, or any other characteristic. We would appreciate acknowledgement if the software is
 * used. This software can be redistributed and/or modified freely provided that any derivative
 * works bear some notice that they are derived from it, and any modified versions bear some notice
 * that they have been modified.
 * @author: Deoyani Nandrekar-Heinis
 */
package gov.nist.oar.custom.updateapi.service;

import java.util.Arrays;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.changestream.ChangeStreamDocument;
import com.mongodb.client.model.changestream.FullDocument;

import gov.nist.oar.custom.updateapi.config.MongoConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class connects to the cache database to get updated record, if the
 * record does not exist in the database, contact Mdserver and getdata.
 * 
 * @author Deoyani Nandrekar-Heinis
 *
 */
public class AccessEditableData {
    private static final Logger log = LoggerFactory.getLogger(AccessEditableData.class);
//    @Autowired
    MongoConfig mconfig;
    MongoCollection<Document> mcollection;
    String mdserver ="";

    AccessEditableData( MongoConfig mconfig, String mdserver) {
	this.mconfig = mconfig;
	mcollection = mconfig.getRecordCollection();
mdserver = mdserver;
    }

    /**
     * Check whether record exists in updated database
     * 
     * @param recordid
     * @return
     */
    public boolean checkRecordInCache(String recordid) {
	Pattern p = Pattern.compile("[^a-z0-9]", Pattern.CASE_INSENSITIVE);
	Matcher m = p.matcher(recordid);
	if (m.find())
	    throw new IllegalArgumentException("check input parameters.");

	long count = mcollection.count(Filters.eq("ediid", recordid));
	if (count == 0)
	    return false;
	return true;
    }

    /**
     * Get data for give recordid
     * 
     * @param recordid
     * @return
     */
    public Document getData(String recordid) {
	if (checkRecordInCache(recordid))
	    return mcollection.find(Filters.eq("ediid", recordid)).first();
	else
	    return this.getDataFromServer(recordid);
    }

    public void getUpdatedData(String recordid){
	FindIterable<Document> fd = mcollection.find(Filters.eq("ediid", recordid)).projection(Projections.include("ediid","title","description"));
	Iterator<Document> iterator = fd.iterator();
	while (iterator.hasNext()) {
	   Document d = iterator.next();
	   System.out.println("Document::"+ d);
	}
	
	//Another tests
	mcollection.watch(Arrays.asList(Aggregates.match(Filters.in("operationType", Arrays.asList("insert", "update", "replace", "delete")))))
        .fullDocument(FullDocument.UPDATE_LOOKUP).forEach(printBlock);
    }
    Block<ChangeStreamDocument<Document>> printBlock = new Block<ChangeStreamDocument<Document>>() {
	    @Override
	    public void apply(final ChangeStreamDocument<Document> changeStreamDocument) {
	        System.out.println(changeStreamDocument);
	    }
	};
    /**
     * 
     * @param recordid
     * @return
     */
    private Document getDataFromServer(String recordid) {
	RestTemplate restTemplate = new RestTemplate();
	return restTemplate.getForObject(mdserver + recordid, Document.class);
    }
    
    /**
     * 
     * @param recordid
     * @param update
     * @return
     */
    public boolean updateDataInCache(String recordid, Document update){
	Document tempUpdateOp = new Document("$set", update);
	UpdateResult updates = mcollection.updateOne(Filters.eq("ediid", recordid), tempUpdateOp);
	return updates != null;
    }
}
