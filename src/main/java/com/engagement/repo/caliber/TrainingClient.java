package com.engagement.repo.caliber;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.engagement.model.dto.Batch;
import com.engagement.model.dto.BatchName;

/**
 * 
 * The TrainingClient accesses the Training endpoints of the caliber 2 API.
 * The endpoints you can access include batch and associate 
 *
 */

@FeignClient(value = "batch", url = "https://caliber2-mock.revaturelabs.com/mock/training")
public interface TrainingClient {
	
	/**
	 * 
	 * @return List<Batch> Returns the list of all batches
	 */
	@GetMapping(value = "/batch/current")
	List<BatchName> getBatches();
	
	/**
	 * 
	 * @param batchId The id for a batch. For example TR-1142
	 * @return List<Batch> Returns a list of one or zero Batches based on the batchId
	 */
	@GetMapping(value = "/batch/{batchId}")
	List<Batch> getBatchById(@PathVariable("batchId") String batchId);

}