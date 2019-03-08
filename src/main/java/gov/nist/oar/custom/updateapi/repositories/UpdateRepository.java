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
package gov.nist.oar.custom.updateapi.repositories;

import java.util.Map;

import org.bson.Document;

/**
 * This is repository works with getting editable data, 
 * update cache or save final results by passing it to backend service.
 * @author Deoyani Nandrekar-Heinis
 *
 */
public interface UpdateRepository {
    public boolean update(Map<String,String> param, String recordid);
    public Document edit(String recordid);
    public boolean save(String recordid);
}
