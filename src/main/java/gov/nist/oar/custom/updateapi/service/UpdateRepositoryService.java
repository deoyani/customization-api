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

import java.util.Map;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.DBObject;
import com.mongodb.util.JSON;

import gov.nist.oar.custom.updateapi.config.MongoConfig;
import gov.nist.oar.custom.updateapi.repositories.ProcessInputRequest;
import gov.nist.oar.custom.updateapi.repositories.UpdateRepository;

/**
 * @author Deoyani Nandrekar-Heinis
 *
 */
@Service
public class UpdateRepositoryService implements UpdateRepository {

    private Logger logger = LoggerFactory.getLogger(UpdateRepositoryService.class);
    @Autowired
    MongoConfig mconfig;

    /*
     * 
     * */
    // @Override
    // public boolean update(Map<String, String> params, String recordid) {
    // ProcessInputRequest req = new ProcessInputRequest();
    // req.parseInputParams(params);
    // AccessEditableData accessData = new AccessEditableData();
    // accessData.checkRecordInCache(recordid);
    //
    // return false;
    // }
    @Override
    public boolean update(String params, String recordid) {
	ProcessInputRequest req = new ProcessInputRequest();
	if (req.validateInputParams(params)) {
	    AccessEditableData accessData = new AccessEditableData(mconfig);
	    accessData.checkRecordInCache(recordid);
	    Document update = Document.parse(params);
	    //DBObject bson = ( DBObject ) JSON.parse( params );
	    return accessData.updateDataInCache(recordid, update);
	} else
	    return false;
    }

    /* 
     * 
     * */
    @Override
    public Document edit(String recordid) {
	AccessEditableData accessData = new AccessEditableData(mconfig);
	return accessData.getData(recordid);
    }

    /* 
     * 
     *  */
    @Override
    public boolean save(String recordid) {

	return false;
    }

}
