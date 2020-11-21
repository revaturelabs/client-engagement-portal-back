package com.engagement.repo.caliber;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.engagement.model.dto.Grade;

/**
 * 
 * The GradeClient accesses the grade-controller of the caliber 2 API.
 * @author Kelsey Iafrate
 *
 */

@FeignClient(value = "grades", url = "https://caliber2-mock.revaturelabs.com/mock/evaluation/grades/")
public interface GradeClient {

	/**
	 * This method gets all the grades of every trainee in a batch by batch id.
	 * @param id: batch id, who would have guessed based off the Caliber API? Nobody.
	 * @return List<Grade> is the list of all grades for the associates in the specified batch.
	 * @author Kelsey Iafrate
	 */

	@GetMapping("/batch/{batchId}")
	List<Grade> getGradesByBatchId(@PathVariable("batchId") String batchId);
}