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
package gov.nist.oar.custom.updateapi.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gov.nist.oar.custom.updateapi.repositories.UpdateRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author  Deoyani Nandrekar-Heinis
 *
 */
@RestController
@Api(value = "Api endpoints to access editable data, update changes to data, save in the backend", tags = "Customization API")
@Validated
@RequestMapping("/")
public class UpdateController {
    private Logger logger = LoggerFactory.getLogger(UpdateController.class);

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UpdateRepository uRepo;

    @RequestMapping(value = {
	    "update/{ediid}" }, method = RequestMethod.POST)
    @ApiOperation(value = ".", nickname = "Cache Record Changes", notes = "Resource returns a record if it is editable and user is authenticated.")
    public void updateRecord(@PathVariable @Valid String ediid,
	    @Valid @RequestBody String params) {
	uRepo.update(params, ediid);
    }

    @RequestMapping(value = {
	    "save/{ediid}" }, method = RequestMethod.POST)
    @ApiOperation(value = ".", nickname = "Save changes to server", notes = "Resource returns a boolean based on success or failure of the request.")
    public void saveRecord(@PathVariable @Valid String ediid) {
	uRepo.save(ediid);
    }

    @RequestMapping(value = {
	    "edit/{ediid}" }, method = RequestMethod.GET)
    @ApiOperation(value = ".", nickname = "Access editable Record", notes = "Resource returns a record if it is editable and user is authenticated.")
    public Document editRecord(@PathVariable @Valid String ediid) {
	return uRepo.edit(ediid);
    }
}
