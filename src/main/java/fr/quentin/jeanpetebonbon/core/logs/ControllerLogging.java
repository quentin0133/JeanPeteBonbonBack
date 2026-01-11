package fr.quentin.jeanpetebonbon.core.logs;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * The type Controller logging.
 */
@Aspect
@Component
@Slf4j
public class ControllerLogging {

    /**
     * Log controller object.
     *
     * @param joinPoint the join point
     * @return the object
     * @throws Throwable the throwable
     */
    @Around("@annotation(LogController)")
    public Object logController(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getMethod().getName();

        Object[] args = joinPoint.getArgs();

        long start = System.currentTimeMillis();

        log.info("{} : ENTER {}", methodName, Arrays.toString(args));

        Object result = joinPoint.proceed();

        log.info(
            "{} : EXIT ({} ms) with {}",
            methodName,
            result,
            System.currentTimeMillis() - start
        );

        return result;
    }
}
