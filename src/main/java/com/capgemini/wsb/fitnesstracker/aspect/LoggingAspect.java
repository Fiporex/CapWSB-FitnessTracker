import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(* com.capgemini.wsb.fitnesstracker..*(..))")
    private void anyMethod() {}

    @Before("anyMethod()")
    public void logBefore(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getName();
        String className = signature.getDeclaringTypeName();
        String args = Arrays.toString(joinPoint.getArgs());
        System.out.println("Before: " + className + "." + methodName + "()" + " with arguments " + args);
    }

    @AfterReturning(pointcut = "anyMethod()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getName();
        String className = signature.getDeclaringTypeName();
        String args = Arrays.toString(joinPoint.getArgs());
        System.out.println("After: " + className + "." + methodName + "()" + " with arguments " + args + " returned " + result);
    }
}
