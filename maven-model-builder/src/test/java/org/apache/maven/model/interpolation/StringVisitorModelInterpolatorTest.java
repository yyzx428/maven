package org.apache.maven.model.interpolation;

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

import org.apache.maven.model.Model;
import org.apache.maven.model.building.DefaultModelBuildingRequest;
import org.apache.maven.model.building.SimpleProblemCollector;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StringVisitorModelInterpolatorTest
{
    @Test
    public void testNPE()
    {
        Properties props = new Properties();
        props.setProperty( "aa", "${bb}" );
        props.setProperty( "bb", "${aa}" );
        DefaultModelBuildingRequest request = new DefaultModelBuildingRequest();

        Model model = new Model();
        model.setProperties( props );

        SimpleProblemCollector collector = new SimpleProblemCollector();
        StringVisitorModelInterpolator interpolator = new StringVisitorModelInterpolator();
        interpolator.interpolateModel( model, null, request, collector );

        assertEquals( "Expected no errors", 2, collector.getErrors().size() );
        assertTrue( collector.getErrors().get( 0 ).contains( "Detected the following recursive expression cycle" ) );
    }
}