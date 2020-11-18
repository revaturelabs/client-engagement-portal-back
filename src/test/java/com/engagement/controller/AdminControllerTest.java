package com.engagement.controller;

import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@WebMvcTest(AdminController.class)
class AdminControllerTest {
	
	
	
//	@Autowired
//    private MockMvc mvc;
//	
//	
//	@MockBean
//	private AdminService as;
//	
//	private AdminController ac;
//	
//	
//	
//	private String mockAdminJson = "{'admin':[{'adminId':1 ,'email':'a@a.net','firstName':'admin',lastName':'adminson'}]}";
//
//	
//
//	@Test
//	void testCreateNewAdmin() {
//		
//		mvc.perform( MockMvcRequestBuilders.post("admin/new")
//			      .contentType(MediaType.APPLICATION_JSON).andExpect(MockMvcResultMatchers.status().isOk())
//		            .andExpect(content().json(mockAdminJson));
//
//	}
//
}
