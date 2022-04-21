package com.example.liveasyloadapis.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.liveasyloadapis.exception.ResourceNotFoundException;
import com.example.liveasyloadapis.model.Payload;
import com.example.liveasyloadapis.repository.PayloadRepository;

@RestController
@RequestMapping("/api/v1/")
public class PayloadController {
	@Autowired
	private PayloadRepository payloadRepository;

	//get(retrieve) all the records of payloads
	@GetMapping("load")
	public List<Payload> getAllPayloads() {
		return payloadRepository.findAll();
	}

	//get(retrieve) record of one payload by its shipperId
	@GetMapping("load/{id}")
	public ResponseEntity<Payload> getPayloadById(@PathVariable(value = "id") Long shipperId)
			throws ResourceNotFoundException {
		Payload Payload = payloadRepository.findById(shipperId)
				.orElseThrow(() -> new ResourceNotFoundException("Payload not found for this id :: " +shipperId));
		return ResponseEntity.ok().body(Payload);
	}

	//add payload in table
	@PostMapping("load")
	public Payload createPayload(@RequestBody Payload payload) {
		return payloadRepository.save(payload);
	}

	//update record of respective payload 
	@PutMapping("load/{id}")
	public ResponseEntity<Payload> updatePayload(@PathVariable(value = "id") Long shipperId,
		 @RequestBody Payload PayloadDetails) throws ResourceNotFoundException {
		Payload payload = payloadRepository.findById(shipperId)
				.orElseThrow(() -> new ResourceNotFoundException("Payload not found for this id :: " + shipperId));

		payload.setLoadingPoint(PayloadDetails.getLoadingPoint());
		payload.setUnloadingPoint(PayloadDetails.getUnloadingPoint());
		payload.setProductType(PayloadDetails.getProductType());
		payload.setTruckType(PayloadDetails.getTruckType());
		payload.setNoOfTrucks(PayloadDetails.getNoOfTrucks());
		payload.setWeight(PayloadDetails.getWeight());
		payload.setComment(PayloadDetails.getComment());
		payload.setDate(PayloadDetails.getDate());
		final Payload updatedPayload = payloadRepository.save(payload);
		return ResponseEntity.ok(updatedPayload);
	}
	
    //delete record of specific payload 
   	@DeleteMapping("load/{id}")
	public Map<String, Boolean> deletePayload(@PathVariable(value = "id") Long shipperId)
			throws ResourceNotFoundException {
		Payload Payload = payloadRepository.findById(shipperId)
				.orElseThrow(() -> new ResourceNotFoundException("Payload not found for this id :: " + shipperId));

		payloadRepository.delete(Payload);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}


