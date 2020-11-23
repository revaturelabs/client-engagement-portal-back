package com.engagement.aspect;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

import com.engagement.controller.AdminController;
import com.engagement.service.AdminService;





@ExtendWith(MockitoExtension.class)
class LoggingAspectTest {

	@Mock
	private AdminService as;
	
	@InjectMocks	
	private AdminController ac;
	
	private LoggingAspect la;
	
	@Mock
    private Appender mockAppender;
	
    private ArgumentCaptor<LogEvent> captor;

    @BeforeEach
    public void setup() {
    	reset(mockAppender);
        when(mockAppender.getName()).thenReturn("Appender");
        when(mockAppender.isStarted()).thenReturn(true);
        when(mockAppender.isStopped()).thenReturn(false);

        LoggerContext context = (LoggerContext) LogManager.getContext();
        Configuration config = context.getConfiguration();
        config.addAppender(mockAppender);
        LoggerConfig rootConfig = config.getRootLogger();
        rootConfig.setLevel(Level.INFO);
        rootConfig.addAppender(mockAppender, Level.INFO, null);
        context.updateLoggers();
    }
    
	@Test
	void testSpringBeanPointcut() {
		la = new LoggingAspect();
		la.setLogger(LogManager.getLogger());
		AspectJProxyFactory factory = new AspectJProxyFactory(ac);
		factory.addAspect(la);
		
		AdminController proxyAc = factory.getProxy();
		Mockito.when(proxyAc.findAll()).thenReturn(null);
		verify(mockAppender).append(captor.capture());
		LogEvent logEvent = captor.getValue();
		String msg = logEvent.getMessage().getFormattedMessage();
	    assertTrue(msg.contains("Enter"));
	}
	
	@Test
	void testException() {
		la = new LoggingAspect();
		Logger log = LogManager.getLogger();
		la.setLogger(log);
		AspectJProxyFactory factory = new AspectJProxyFactory(ac);
		factory.addAspect(la);
		
		AdminController proxyAc = factory.getProxy();
		
		Mockito.when(proxyAc.delete(1)).thenThrow(IllegalArgumentException.class);
		
		assertThrows(IllegalArgumentException.class, () -> {
	        proxyAc.delete(1);
	    });
		
	}

}
