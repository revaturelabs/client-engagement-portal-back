package com.engagement.repo.caliber;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.engagement.model.dto.Batch;

@FeignClient(value = "batch", url = "https://caliber2-mock.revaturelabs.com/mock/training/")
public interface TrainingClient {
	
	@RequestMapping(method = RequestMethod.GET, value = "/batch")
	List<Batch> getBatches();
	
	@RequestMapping(method = RequestMethod.GET, value = "/batch/{batchId}")
	List<Batch> getBatchById(@PathVariable("batchId") String batchId);

}