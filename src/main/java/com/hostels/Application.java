package com.hostels;

import io.micronaut.core.annotation.TypeHint;
import io.micronaut.data.intercept.annotation.DataMethod;
import io.micronaut.runtime.Micronaut;

@TypeHint(
    value = DataMethod.OperationType.class,
    accessType = TypeHint.AccessType.ALL_DECLARED_CONSTRUCTORS
)
public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }
}