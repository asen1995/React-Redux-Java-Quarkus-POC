package com.poc.tableentryservice.aspect;

import jakarta.annotation.Priority;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import org.jboss.logging.Logger;

/**
 * Interceptor that logs method entry and exit.
 */
@LoggedAspect
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class LoggingInterceptor {

    private static final Logger LOG = Logger.getLogger(LoggingInterceptor.class);

    /**
     * Logs when a method is called and when it finishes.
     *
     * @param context the invocation context
     * @return the result of the method invocation
     * @throws Exception if the method throws an exception
     */
    @AroundInvoke
    public Object logMethodCall(InvocationContext context) throws Exception {
        String className = context.getTarget().getClass().getSimpleName();
        String methodName = context.getMethod().getName();

        LOG.infof(">>> Entering %s.%s()", className, methodName);

        try {
            Object result = context.proceed();
            LOG.infof("<<< Exiting %s.%s() - Success", className, methodName);
            return result;
        } catch (Exception e) {
            LOG.errorf("<<< Exiting %s.%s() - Exception: %s", className, methodName, e.getMessage());
            throw e;
        }
    }
}
