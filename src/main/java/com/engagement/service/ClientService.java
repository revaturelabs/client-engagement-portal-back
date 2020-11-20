package com.engagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.engagement.model.Client;

import com.engagement.model.dto.AssociateAssignment;
import com.engagement.model.dto.Batch;
import com.engagement.model.dto.Grade;
import com.engagement.repo.ClientRepo;
import com.engagement.repo.caliber.GradeClient;

import com.engagement.repo.caliber.TrainingClient;

/**
 * Service for handling business logic of client requests
 * 
 * @author Tucker Fritz
 *
 */
@Service
public class ClientService {

	ClientRepo cr;
	private TrainingClient bc;
	private GradeClient gc;

	@Autowired
	public ClientService(ClientRepo cr, TrainingClient bc, GradeClient gc) {
		super();
		this.cr = cr;
		this.bc = bc;
		this.gc = gc;
	}

	/**
	 * Returns a list of all clients in the database
	 * 
	 * @return List of all clients
	 */
	public List<Client> findAll() {
		return cr.findAll();
	}

	/**
	 * Saves a client to the database
	 * 
	 * @param client A client to be saved to the database
	 * @return true if success, false if fail
	 */
	public boolean save(Client client) {
		if (client == null) {
			return false;
		}
		
		try {
			cr.save(client);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Find a client by email
	 * 
	 * @param email An email pertaining to a client in the database
	 * @return Client associated with id w/ default values if client is non-existant
	 */
	public Client findByEmail(String email) {
		return cr.findByEmail(email);
	}


	/**
	 * Find a batch by it's identifier in Caliber.
	 * 
	 * @param batchId: The batch's identifier in Caliber.
	 * @return Returns the batch associated to the id.
	 * @author Kelsey Iafrate
	 */
	public Batch getBatchByBatchId(String batchId) {

		Batch b = bc.getBatchById(batchId);// gets a list of zero or one batch this is associated with the id.

		if (!b.equals(null)) {

			List<Grade> grades = gc.getGradesByBatchId(batchId); // gets all of the grades associated with the batch.

			/**
			 * For every grade, check if its traineeId equals any salesForceId of an
			 * associate of the batch. Once a match is found, add that grade to the list of
			 * grades of that associate, then move on to the next grade.
			 * 
			 * @author Kelsey Iafrate

			 */
			for (Grade grade : grades) {
				for (AssociateAssignment a : b.getAssociateAssignments()) {
					if (grade.getTraineeId().equals(a.getAssociate().getSalesforceId())) {
						a.getAssociate().getGrades().add(grade);
						break;
					}
				}
			}
			return b; // Returns the batch with all associates and their grades.
		}

		return null; // If a batch with that batchId was found, return null.
	}
}