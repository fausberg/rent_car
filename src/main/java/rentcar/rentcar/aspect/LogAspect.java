package rentcar.rentcar.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Before("within(rentcar.rentcar.service.*)")
    public void logBeforeMethod(JoinPoint joinPoint) {
        log.info("Start doing method " + joinPoint.getSignature());
    }

    @After("within(rentcar.rentcar.service.*)")
    public void logAfterMethod(JoinPoint joinPoint) {
        log.info("Finish doing method " + joinPoint.getSignature());
    }

    @AfterReturning("within(rentcar.rentcar.*)")
    public void logAfterReturningMethod(JoinPoint joinPoint) {
        log.info("Log after returning " + joinPoint.getSignature());
    }

    @AfterThrowing(throwing = "error", value = "within(rentcar.rentcar.*)")
    public void logAfterThrowingMethod(JoinPoint joinPoint, Throwable error) {
        log.error("Exception in this method" + joinPoint.getSignature());
    }
}
