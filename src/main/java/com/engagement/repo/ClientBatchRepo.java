package com.engagement.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.engagement.model.Client;
import com.engagement.model.ClientBatch;

@Repository
public interface ClientBatchRepo extends JpaRepository<ClientBatch, Integer> {
	public List<ClientBatch> findByClient(Client c);
}