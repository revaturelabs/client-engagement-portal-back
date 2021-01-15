package com.engagement.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.engagement.model.Admin;
import com.engagement.model.Client;
import com.engagement.model.ClientBatch;
import com.engagement.model.dto.BatchName;
import com.engagement.repo.AdminRepo;
import com.engagement.repo.ClientBatchRepo;
import com.engagement.repo.ClientRepo;
import com.engagement.repo.caliber.TrainingClient;
import com.engagement.util.FirebaseUtil;
import com.google.firebase.auth.FirebaseAuthException;

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
		
	@Mock
	private static FirebaseUtil firebaseUtil;
	

	/**
	 * @author Brooke Wursten
	 * tests the FindAllMappings method to be sure it gets the map we expect
	 */
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

		Map<String,String> resultMap = new HashMap<String,String>(){/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
			put("TR-101","client0@client.net");
			put("TR-102","client0@client.net");
			put("TR-103","client1@client.net");
		}};

		assertEquals((HashMap<String,String> )as.findAllMappings(), resultMap);

	}

	/**
	 * @author Brooke Wursten
	 * Unit test for findByAdminId
	 */
	@Test
	void testFindByAdminId(){
		Admin admin1= new Admin(1,"admin@rev.net","admin","adminson");

		Mockito.when(ar.findByAdminId(1)).thenReturn(admin1);
		assertEquals(admin1,as.findByAdminId(1));
	}



	/**
	 * @author daniel constantinescu
	 * This unit test whether the service return succesfully all admins
	 */
	@Test
	void testFindAll() {
		/**
		 *Set up mock from Admin repo 
		 */
		Admin admin1= new Admin(1,"a@b","fstubtest1","lstubtest1");
		Admin admin2= new Admin(12,"a@b","fstubtest2","lstubtest2");

		List<Admin> admins = new ArrayList<Admin>();
		admins.add(admin1);
		admins.add(admin2);

		Mockito.when(ar.findAll()).thenReturn(admins);
		assertEquals(admins,as.findAll());
	}

	/**
	 * @author daniel constantinescu
	 * This unit test if  finding by email an admin works properly
	 */
	@Test
	void testFindByEmailSucces() {
		/**
		 *Set up mock from Admin repo 
		 */
		Admin admin1= new Admin(1,"a@b","fstubtest1","lstubtest1");

		Mockito.when(ar.findByEmail(admin1.getEmail())).thenReturn(admin1);
		assertEquals(admin1,as.findByEmail(admin1.getEmail()));

	}

	/**
	 * @author daniel constantinescu
	 * This unit test the  case  for admin not found 
	 */
	@Test
	void testFindByEmailFail() {
		/**
		 *Set up mock from Admin repo 
		 */
		Admin admin1= new Admin(1,"a@b","fstubtest1","lstubtest1");

		Mockito.when(ar.findByEmail(admin1.getEmail())).thenReturn(null);
		assertNull(as.findByEmail(admin1.getEmail()));
	}	


	/**
	 * @author daniel constantinescu
	 * This unit test if  save properly Admin
	 * @throws FirebaseAuthException 
	 */
	@Test
	void testSaveSuccess() throws FirebaseAuthException {
		/**
		 *Set up mock from Admin repo 
		 */
		Admin admin1= new Admin(1,"a@b","fstubtest1","lstubtest1");
		Admin admin2= new Admin(3,"a@b","fstubtest1","lstubtest1");
		Map<String, Object> claims = new HashMap<String, Object>();
		boolean success;
		claims.put("role", "admin");
		
		
		Mockito.when(ar.save(admin1)).thenReturn(admin1);
		Mockito.doNothing().when(firebaseUtil).setCustomClaims(admin1.getEmail());
		
		
		
		assertTrue( as.save(admin1));
		
	
		assertFalse(as.save(null));
		
		Mockito.when(ar.save(admin2)).thenThrow(IllegalArgumentException.class);
		assertFalse(as.save(admin2));
		
	}

	/**
	 * @author daniel constantinescu
	 * This unit test if  save properly Admin
	 */
	@Test
	void testUpdateSuccess() {
		/**
		*Set up mock from Admin repo 
		*/
		Admin admin1= new Admin(1,"a@b","fstubtest1","lstubtest1");
		Admin admin2= new Admin(3,"a@b","fstubtest1","lstubtest1");

		Mockito.when(ar.save(admin1)).thenReturn(admin1);
		assertEquals(admin1, as.update(admin1));
		
		assertNull(as.update(null));
		
		Mockito.when(ar.save(admin2)).thenThrow(IllegalArgumentException.class);
		assertNull(as.update(admin2));		
		
	}
	
	@Test
	void testDelete() {
		assertAll(() -> as.delete(null));	
	}

	/**
	 * @author Carlo Anselmo
	 * Tests if the service properly receives a list of batch names from the repo
	 */
	@Test
	void testAllBatchesByName() {
		BatchName namedBatch = new BatchName("TR-1759", "Mock Batch 505");
		List<BatchName> batchList = new ArrayList<>();
		batchList.add(namedBatch);
		Mockito.when(tc.getBatches()).thenReturn(batchList);
		
		assertEquals(batchList, as.getAllBatchNames());
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
		assertTrue(as.mapBatchtoClient(BatchId,email));
		
		Mockito.when(cr.findByEmail(null)).thenReturn(null);
		assertFalse(as.mapBatchtoClient(null, null));
	}	

	/**
	 * @author daniel constantinescu
	 * test unmap batch from a client 
	 * @param batchId,email
	 * @return true
	 */

	@Test
	void testUnMapBatchFromClientSucces(){
		String batchId="ABC";
		String email="a@b";

		Client c =  new Client (1,"a@b","company", "2488546789");
		ClientBatch cb=new ClientBatch(1000,"ABC",c);


		Mockito.when(cr.findByEmail(email)).thenReturn(c);
		
		Mockito.when(cbr.findByBatchIdAndClient(batchId,c)).thenReturn(cb);
		doNothing().when(cbr).delete(cb);
		assertTrue(as.unmapBatchFromClient(batchId, email));

		
		Mockito.when(cbr.findByBatchIdAndClient(null,null)).thenReturn(null);
		assertFalse(as.unmapBatchFromClient(null, null));
	}
}

