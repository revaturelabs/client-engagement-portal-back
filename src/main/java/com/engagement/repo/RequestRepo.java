package com.engagement.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.engagement.model.Request;

@Repository
public interface RequestRepo extends JpaRepository<Request, Integer> {
	Request findByRequestId(int requestId);

	List<Request> findAll();

}
