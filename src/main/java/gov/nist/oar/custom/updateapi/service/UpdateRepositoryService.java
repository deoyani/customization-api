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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import gov.nist.oar.custom.updateapi.repositories.ProcessInputRequest;
import gov.nist.oar.custom.updateapi.repositories.UpdateRepository;
import gov.nist.oar.custom.updateapi.config.MongoConfig;

/**
 * @author Deoyani Nandrekar-Heinis
 *
 */
@Service
public class UpdateRepositoryService implements UpdateRepository{
    
    private Logger logger = LoggerFactory.getLogger(UpdateRepositoryService.class);
    @Autowired
    MongoConfig mconfig;
    /*
     * 
     * */
    @Override
    public boolean update(Map<String, String> params, String recordid) {
	ProcessInputRequest req = new ProcessInputRequest();
	req.parseInputParams(params);
	
	return false;
    }

    /* 
     * 
     * */
    @Override
    public Document edit(String recordid) {
	AccessEditableData accessData = new AccessEditableData();
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
