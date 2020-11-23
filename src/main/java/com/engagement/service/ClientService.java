package com.engagement.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.engagement.model.Client;
import com.engagement.model.ClientBatch;
import com.engagement.model.dto.AssociateAssignment;
import com.engagement.model.dto.Batch;
import com.engagement.model.dto.BatchOverview;
import com.engagement.model.dto.ClientName;
import com.engagement.model.dto.Grade;
import com.engagement.repo.ClientBatchRepo;
import com.engagement.repo.ClientRepo;
import com.engagement.repo.caliber.GradeClient;
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
	ClientBatchRepo cbr;
	private TrainingClient bc;
	private GradeClient gc;

	@Autowired
	public ClientService(ClientRepo cr, TrainingClient bc, GradeClient gc, ClientBatchRepo cbr) {
		super();
		this.cr = cr;
		this.bc = bc;
		this.gc = gc;
		this.cbr = cbr;
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

		if (b != null) {

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
		
		/**
		 * Find all client names
		 * 
		 * @param none
		 * @return All clients with only number and name
		 */
		public List<ClientName> findClientNames()
		{
			List<Client> clients = cr.findAll();
			List<ClientName> clientsdto = new LinkedList<>();
			for(int i = 0; i < clients.size(); i++)
				clientsdto.add(new ClientName(clients.get(i).getCompanyName(), String.valueOf(clients.get(i).getClientId())));
			
			return clientsdto;
		}
		
		
		public List<BatchOverview> getBatchInfoByEmail(String email) {
					List<BatchOverview> results = new LinkedList<BatchOverview>();
					Client client = cr.findByEmail(email);
					List<ClientBatch> batches = cbr.findByClient(client);
					Batch batchesapi;
					
					System.out.println(batches.get(0).getBatchId());
					
					for(int i = 0; i < batches.size(); i++)
					{
						batchesapi = bc.getBatchById(batches.get(i).getBatchId());
						results.add(new BatchOverview(batchesapi.getBatchId(), batchesapi.getName(), batchesapi.getSkill()));
					}
						
					return results;
				}	
		
		
}
