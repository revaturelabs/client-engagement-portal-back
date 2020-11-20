package com.engagement.service;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.engagement.controller.RequestController;

@RunWith(MockitoJUnitRunner.class)
public class RequestServiceTest {

	@InjectMocks
	private RequestController rc;

	@MockBean
	private RequestService rs;

}
