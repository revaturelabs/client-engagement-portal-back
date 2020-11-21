package com.engagement.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.engagement.model.Client;

@Repository
public interface ClientBatchRepo extends JpaRepository<Client, Integer> {

}