package com.engagement.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

import com.engagement.model.Client;
import com.engagement.model.Request;
import com.engagement.model.Request.RequestTypes;
import com.engagement.model.Request.Status;
import com.engagement.model.dto.RequestDto;
import com.engagement.repo.ClientRepo;
import com.engagement.repo.RequestRepo;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration
class RequestServiceTest {

	@InjectMocks
	private static RequestService rs;

	@Mock
	private static RequestRepo rr;
	@Mock
	private static ClientRepo cr;

	Client TestClient = new Client(1, "a@a.com", "revature", "5555555");

	private Request testRequest0 = new Request(0, RequestTypes.INTERVENTION, Status.PENDING, "test comment", TestClient,
			null);

	private RequestDto testRequest0DTO = new RequestDto(0, "INTERVENTION", "PENDING", "test comment", "a@a.com",
			null);

	private Request testRequest1 = new Request(1, RequestTypes.TALENT, Status.DONE, "test comment2", TestClient,
			null);

	private RequestDto testRequest1DTO = new RequestDto(0, "TALENT", "DONE", "test comment2", "a@a.com",
			null);

	private List<Request> testRequests = new ArrayList<>();

	/**
	 * This tests that the findAll method returns not null.
	 * 
	 * @author Robert Porto
	 */
	@Test
	void findAllTest() {
		testRequests.add(testRequest0);
		testRequests.add(testRequest1);
		Mockito.when(rr.findAll()).thenReturn(testRequests);
		assertNotNull(rs.findAll());
	}

	/**
	 * This tests that the findByRequestId method returns not null.
	 * 
	 * @author Robert Porto
	 */
	@Test
	void findByRequestId() {
		Mockito.when(rr.findByRequestId(0)).thenReturn(testRequest0);
		assertNotNull(rs.findByRequestId(0));
	}

	/**
	 * This tests that the save method returns not true.
	 * 
	 * @author Robert Porto
	 */
	@Test
	void saveTest() {
		Mockito.when(cr.findByEmail("a@a.com")).thenReturn(TestClient);
		Mockito.when(rr.save(testRequest0)).thenReturn(testRequest0);
		assertTrue(rs.save(testRequest0DTO));
		assertFalse(rs.save(null));
		Mockito.when(rs.save(testRequest1DTO)).thenThrow(IllegalArgumentException.class);
		assertFalse(rs.save(testRequest1DTO));

	}

}
