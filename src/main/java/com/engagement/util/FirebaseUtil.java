package com.engagement.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

/**
 * Class to hold logic methods dealing with firebase. 
 * This class is meant to help with encapsulation as well as mocking for testing. 
 * @author Jaden Wilson
 *
 */
@Component
public class FirebaseUtil {

	public void setCustomClaims(String email) throws FirebaseAuthException {
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("role", "admin");
		UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(email);
		FirebaseAuth.getInstance().setCustomUserClaims(userRecord.getUid(), claims);
	}
}
