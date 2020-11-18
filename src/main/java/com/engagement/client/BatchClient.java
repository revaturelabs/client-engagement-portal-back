package com.engagement.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.engagement.model.DTO.Batch;

@FeignClient(value = "batch", url = "https://caliber2-mock.revaturelabs.com/mock/training/")
public interface BatchClient {
	
	@RequestMapping(method = RequestMethod.GET, value = "/batch")
	List<Batch> getBatches();

}
