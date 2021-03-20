package cz.muni.fi.pa165;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import javax.inject.Named;
import java.time.Duration;
import java.time.Instant;

@Aspect
@Named
public class TimeAspect {
    @Around("execution(public * cz.muni.fi.pa165.currency.*.*(..))")
    public Object timeMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {

        System.err.println("Calling method: "
                + joinPoint.getSignature());
       Instant timeBefore = Instant.now();

        Object result = joinPoint.proceed();

        Instant timeAfter = Instant.now();

        System.err.println("Method " + joinPoint.getSignature() + " finished after: "+ Duration.between(timeBefore, timeAfter).toNanos() + " nanoseconds with result: " + result);
        return result;
    }
}
