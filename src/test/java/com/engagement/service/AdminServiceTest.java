package com.engagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

import com.engagement.model.Client;
import com.engagement.model.ClientBatch;
import com.engagement.repo.AdminRepo;
import com.engagement.repo.ClientBatchRepo;
import com.engagement.repo.ClientRepo;
import com.engagement.repo.caliber.TrainingClient;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration
class AdminServiceTest {
	
	@Mock
	private AdminRepo ar;
	
	@Mock
	private TrainingClient tc;
	
	@Mock
	private ClientBatchRepo cbr;
	
	@Mock
	private ClientRepo cr;
	
	@InjectMocks
	private AdminService as;
	
	
	

	@Test
	void testFindAllMappings() {
		
		/**
		 * Set up mock return from client-batch repo
		 */
		List<ClientBatch> clientBatchList = new ArrayList<ClientBatch>();
		Client client0 = new Client(1,"client0@client.net","revarev","9998887777");
		Client client1 = new Client(2,"client1@client.net","wahlmert","9997776666");
		clientBatchList.add(new ClientBatch(1,"TR-101",client0));
		clientBatchList.add(new ClientBatch(2,"TR-102",client0));
		clientBatchList.add(new ClientBatch(3,"TR-103",client1));
		Mockito.when(cbr.findAll()).thenReturn(clientBatchList);
		
		Map<String,Integer> resultMap = new HashMap<String,Integer>(){{
			put("TR-101",1);
			put("TR-102",1);
			put("TR-103",2);
			}};
		
		assertEquals((HashMap<String,Integer> )as.findAllMappings(), resultMap);
		
	}

	/**
	 * @author daniel constantinescu
	 * test map  batch to a  client
	 * @param BatchId,email
	 * @return true
	 */
	
	@Test
	void testMapBatchToClientSucces() {
		String BatchId="ABC";
		String email="a@b";
		
		Client c =  new Client (1,"a@b","company", "2488546789");
		ClientBatch cb=new ClientBatch(1000,"ABC",c);
		
		Mockito.when(cr.findByEmail(email)).thenReturn(c);
		Mockito.when(cbr.save(cb)).thenReturn(cb);
	
		assertTrue(as.MapBatchtoClient(BatchId,email));
	}	
	
	/**
	 * @author daniel constantinescu
	 * test unmap batch from a client 
	 * @param batchId,email
	 * @return true
	 */
	
	@Test
	void testUnMapBatchFromClientSucces(){
		String BatchId="ABC";
		String email="a@b";
		
		Client c =  new Client (1,"a@b","company", "2488546789");
		ClientBatch cb=new ClientBatch(1000,"ABC",c);
		
		Mockito.when(cbr.findByBatchId(BatchId)).thenReturn(cb);
		doNothing().when(cbr).deleteByBatchId(BatchId);
		assertTrue(as.UnMapBatchFromClient(BatchId, email));
	}
}
