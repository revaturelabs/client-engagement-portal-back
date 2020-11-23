package com.engagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.engagement.model.Request;
import com.engagement.model.Request.RequestTypes;
import com.engagement.model.Request.Status;
import com.engagement.model.dto.RequestDto;
import com.engagement.service.RequestService;

import io.swagger.annotations.ApiOperation;

/**
 * Controller that handles requests for new client interventions
 * 
 * @author Robert Porto
 */

@CrossOrigin
@RestController
@RequestMapping("/intervention")
public class RequestController {

	private RequestService rs;

	@Autowired
	public RequestController(RequestService rs) {
		super();
		this.rs = rs;
	}

	/**
	 * Returns a list of all intervention requests in the database
	 * 
	 * @return List of all requests
	 */

	@ApiOperation(value = "Returns a list of all intervention requests in the DB")
	@GetMapping("/")
	public List<Request> getAllInterventions() {
		return rs.findAll();
	}

	/**
	 * Saves a request to the database
	 * 
	 * @param request A request to be saved to the database
	 * @return ResponseEntity containing status code and message.
	 * 
	 * 
	 */

//	@ApiOperation(value = "Saves an intervention request to the database.")
//	@PostMapping("/")
//	public ResponseEntity<String> save(@RequestBody Request request) {
//		if (rs.save(request)) {
//			return new ResponseEntity<>("Request succesfully created", HttpStatus.CREATED);
//		} else {
//			return new ResponseEntity<>("Request creation failed", HttpStatus.CONFLICT);
//		}
//
//	}
	@ApiOperation(value = "Saves an intervention request to the database.")
	@PostMapping("/")
	public ResponseEntity<String> save(@RequestBody RequestDto requestDTO) {
		Request persistentRequest = new Request(0, RequestTypes.valueOf(requestDTO.getRequestType()),
				Status.valueOf(requestDTO.getStatus()), requestDTO.getMessage(), requestDTO.getClientId());

		if (rs.save(persistentRequest)) {
			return new ResponseEntity<>("Request succesfully created", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Request creation failed", HttpStatus.CONFLICT);
		}

	}

	/**
	 * Find a request by id
	 * 
	 * @param id An id pertaining to a request in the database
	 * @return Request associated with id
	 */
	@GetMapping("/{id}")
	@ApiOperation(value = "Returns a Request with id \"id\".")
	@ResponseBody
	public Request getInterventionById(@PathVariable int id) {
		return rs.findByRequestId(id);
	}

}
