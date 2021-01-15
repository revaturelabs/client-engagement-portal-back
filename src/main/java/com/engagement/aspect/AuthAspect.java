package com.engagement.aspect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

@Aspect
@Component
public class AuthAspect {
	
	@Autowired
	HttpServletRequest req;
	
	@Autowired
	HttpServletResponse res;

	@Pointcut("execution(* com.engagement.controller.*.*(..))")
	public void controllerMethods() {}
	
	@Around("controllerMethods()")
	public Object Authenticate(ProceedingJoinPoint pjp) {
		try {
			FirebaseToken auth = FirebaseAuth.getInstance().verifyIdToken(req.getHeader("tokenId"));
			return pjp.proceed(pjp.getArgs());
		} catch (FirebaseAuthException e) {
			e.printStackTrace();
			res.setStatus(401);
			return null;
		} catch (Throwable e) {
			e.printStackTrace();
			res.setStatus(400);
			return null;
		}
	}
}
