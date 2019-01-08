package org.slf4j.simple;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.slf4j.ILoggerFactory;
import org.slf4j.IMarkerFactory;
import org.slf4j.helpers.BasicMarkerFactory;
import org.slf4j.helpers.NOPMDCAdapter;
import org.slf4j.spi.MDCAdapter;
import org.slf4j.spi.SLF4JServiceProvider;

/**
 * SLF4J SLF4JServiceProvider implementation using MavenSimpleLogger.
 * This class is part of the required classes used to specify an
 * SLF4J logger provider implementation.
 *
 * @since 3.6.1
 */
public class MavenSimpleServiceProvider implements SLF4JServiceProvider
{

    /**
     * Declare the version of the SLF4J API this implementation is compiled against.
     * The value of this field is modified with each major release.
     */
    // to avoid constant folding by the compiler, this field must *not* be final
    @SuppressWarnings( { "checkstyle:staticvariablename", "checkstyle:visibilitymodifier" } )
    public static String REQUESTED_API_VERSION = "1.8.99"; // !final

    private ILoggerFactory loggerFactory;
    private IMarkerFactory markerFactory;
    private MDCAdapter mdcAdapter;

    public ILoggerFactory getLoggerFactory()
    {
        return loggerFactory;
    }

    public IMarkerFactory getMarkerFactory()
    {
        return markerFactory;
    }

    public MDCAdapter getMDCAdapter()
    {
        return mdcAdapter;
    }

    public String getRequesteApiVersion()
    {
        return REQUESTED_API_VERSION;
    }


    @Override
    public void initialize()
    {
        loggerFactory = new MavenSimpleLoggerFactory();
        markerFactory = new BasicMarkerFactory();
        mdcAdapter = new NOPMDCAdapter();
    }
}
