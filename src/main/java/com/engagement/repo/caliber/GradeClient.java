package com.engagement.repo.caliber;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.engagement.model.dto.Grade;

@FeignClient(value = "grades", url = "https://caliber2-mock.revaturelabs.com/mock/evaluation/grades/")
public interface GradeClient {

	/**
	 * This method gets all the grades of every trainee in a batch by batch id.
	 * @param id: batch id, who would have guessed based off the Caliber API? Nobody
	 * @return Batch: the Batch DTO
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/batch/{id}")
	List<Grade> getGradesByBatchId(@PathVariable("id") String batchId);

}