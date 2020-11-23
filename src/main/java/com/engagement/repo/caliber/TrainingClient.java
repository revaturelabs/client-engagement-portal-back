package com.engagement.repo.caliber;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.engagement.model.dto.Batch;
import com.engagement.model.dto.BatchName;

/**
 * 
 * The TrainingClient accesses the Training endpoints of the caliber 2 API.
 * The endpoints you can access include batch and associate 
 * @author Kelsey Iafrate
 *
 */

@FeignClient(value = "batch", url = "https://caliber2-mock.revaturelabs.com/mock/training")
public interface TrainingClient {
	
	/**
	 * Return every batch in the Caliber 2 API
	 * @return List<Batch> Returns the list of all batches
	 * @author Kelsey Iafrate
	 */

	@GetMapping("/batch")
	List<BatchName> getBatches();

	/**
	 * Get a batch by batchId. For example TR-1142
	 * @param batchId The id for a batch.
	 * @return List<Batch> Returns a list of one or zero Batches based on the batchId
	 * @author Kelsey Iafrate
	 */

	@GetMapping("/batch/{batchId}")
	Batch getBatchById(@PathVariable("batchId") String batchId);

}