package com.hostels;

import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.function.aws.runtime.AbstractMicronautLambdaRuntime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
public class FunctionLambdaRuntime extends AbstractMicronautLambdaRuntime<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent, APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent>
{
    private static final Logger log = LoggerFactory.getLogger(FunctionLambdaRuntime.class);

    public static void main(String[] args) {
        try {
            log.info("Test lombok");
            new FunctionLambdaRuntime().run(args);
        } catch (MalformedURLException e) {
            log.error("Error",e);
        }
    }

    @Override
    @Nullable
    protected RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> createRequestHandler(String... args) {
        return new FunctionRequestHandler();
    }
}
