package com.engagement.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.engagement.model.ClientBatch;

@Repository
public interface ClientBatchRepo extends JpaRepository<ClientBatch, Integer> {

}