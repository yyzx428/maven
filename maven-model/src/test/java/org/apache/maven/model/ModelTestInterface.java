package org.apache.maven.model;

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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayNameGeneration( ModelTestInterface.NameGenerator.class )
interface ModelTestInterface< T >
{
    @SuppressWarnings( "unchecked" )
    default T createNewInstance(Class< ? > x) throws ReflectiveOperationException
    {
        return ( T ) x.getDeclaredConstructor().newInstance();
    }

    @Test
    @DisplayName( "hashCode should not fail with null." )
    default void hashCodeNullSafe()
    {
        assertThatCode( () -> createNewInstance( this.getClass() ).hashCode() ).doesNotThrowAnyException();
    }

    @Test
    @DisplayName( "equals should not fail with null." )
    default void equalsNullSafe() throws ReflectiveOperationException
    {
        assertThat( createNewInstance( this.getClass() ).equals( null ) ).isFalse();
    }

    @Test
    @DisplayName( "equals should result in false for two different instances." )
    default void equalsSameToBeFalse() throws ReflectiveOperationException
    {
        T firstInstance = createNewInstance( this.getClass() );
        T secondInstance = createNewInstance( this.getClass() );
        assertThat( firstInstance.equals( secondInstance ) ).isFalse();
    }

    @Test
    @DisplayName( "toString should not be null." )
    default void toStringNullSafe() throws ReflectiveOperationException
    {
        assertThat( createNewInstance( this.getClass() ).toString() ).isNotNull();
    }

    /**
     * The @DisplayName will be the test class name without the trailing "Test".
     */
    class NameGenerator extends DisplayNameGenerator.Standard
    {
        public String generateDisplayNameForClass( Class<?> testClass )
        {
            String name = testClass.getSimpleName();
            return name.substring(0, name.length() - 4);
        }
    }
}
