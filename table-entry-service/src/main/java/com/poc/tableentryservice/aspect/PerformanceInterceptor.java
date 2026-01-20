package com.poc.tableentryservice.aspect;

import jakarta.annotation.Priority;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import org.jboss.logging.Logger;

/**
 * Interceptor that measures and logs method execution time.
 */
@Timed
@Interceptor
@Priority(Interceptor.Priority.APPLICATION + 1)
public class PerformanceInterceptor {

    private static final Logger LOG = Logger.getLogger(PerformanceInterceptor.class);

    /**
     * Measures the execution time of a method and logs it.
     *
     * @param context the invocation context
     * @return the result of the method invocation
     * @throws Exception if the method throws an exception
     */
    @AroundInvoke
    public Object measureExecutionTime(InvocationContext context) throws Exception {
        String className = context.getTarget().getClass().getSimpleName();
        String methodName = context.getMethod().getName();

        long startTime = System.currentTimeMillis();

        try {
            return context.proceed();
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            LOG.infof("[PERF] %s.%s() executed in %d ms", className, methodName, duration);
        }
    }
}
