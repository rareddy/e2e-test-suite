/*
 * Copyright 2019, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.managed.services.test.framework;

@FunctionalInterface
public interface ThrowableRunner {
    void run() throws Exception;
}