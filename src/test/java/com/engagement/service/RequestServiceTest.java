package com.engagement.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.engagement.model.Request;
import com.engagement.model.Request.RequestTypes;
import com.engagement.model.Request.Status;
import com.engagement.repo.RequestRepo;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class RequestServiceTest {

	@InjectMocks
	private RequestService rs;

	@MockBean
	private RequestRepo rr;

	private String testRequestJson2 = "{\"requestId\":1, \"requestType\":\"INTERVENTION\", \"status\":\"PENDING\",\"message\":\"test message\",\"clientId\":1}";
	private Request testRequest0 = new Request(0, RequestTypes.INTERVENTION, Status.PENDING, "test comment", 1);
	private Request testRequest1 = new Request(1, RequestTypes.TALENT, Status.DONE, "test comment2", 2);

	private List<Request> testRequests = new ArrayList<>();

	/**
	 * This tests that the findAll method returns not null.
	 * 
	 * @author Robert Porto
	 */
	@Test
	public void findAllTest() {
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
	public void findByRequestId() {
		Mockito.when(rr.findByRequestId(0)).thenReturn(testRequest0);
		assertNotNull(rs.findByRequestId(0));
	}

	/**
	 * This tests that the save method returns not true.
	 * 
	 * @author Robert Porto
	 */
	@Test
	public void saveTest() {
		Mockito.when(rr.save(testRequest0)).thenReturn(testRequest0);
		assertTrue(rs.save(testRequest0));
	}

}
