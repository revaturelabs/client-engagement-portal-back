package com.engagement.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.engagement.model.Client;
import com.engagement.model.dto.AssociateAssignment;
import com.engagement.model.dto.Batch;
import com.engagement.model.dto.ClientName;
import com.engagement.model.dto.Grade;
import com.engagement.repo.ClientRepo;
import com.engagement.repo.caliber.GradeClient;
import com.engagement.repo.caliber.Params;
import com.engagement.repo.caliber.TrainingClient;

/**
 * Service for handling business logic of client requests
 * @author Tucker Fritz, Matt Hartmann
 *
 */
@Service
public class ClientService {

	@Autowired
	ClientRepo cr;
	@Autowired
	private TrainingClient bc;
	@Autowired
	private GradeClient gc;

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
	 * @param c A client to be saved to the database
	 * @return Client that was saved
	 */
	public Client save(Client c) {
		return cr.save(c);
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
	 */
	public Batch getBatchByBatchId(String batchId) {

		List<Batch> batches = bc.getBatchById(batchId);// gets a list of zero or one batch this is associated with the
														// id.

		if (!batches.isEmpty()) {

			Batch b = batches.get(0);// gets the first batch in the list, which is the batch associated with the
										// batchId.

			Params params = new Params();
			params.setId(batchId);
			List<Grade> grades = gc.getGradesByBatchId(batchId); // gets all of the grades associated with the batch.

			/**
			 * For every grade, check if its traineeId equals any salesForceId of an
			 * associate of the batch. Once a match is found, add that grade to the list of
			 * grades of that associate, then move on to the next grade.
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
		return null; // If no batch with that batchId was found, return null;
	}
		
		/**
		 * Find all client names
		 * 
		 * @param none
		 * @return All clients with only number and name
		 */
		public List<ClientName> ClientNames()
		{
			List<Client> clients = cr.findAll();
			List<ClientName> clientsdto = new LinkedList<ClientName>();
			for(int i = 0; i < clients.size(); i++)
				clientsdto.add(new ClientName(clients.get(i).getCompanyName(), String.valueOf(clients.get(i).getClientId())));
			
			return clientsdto;
		}
		
		
}
