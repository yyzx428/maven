package org.apache.maven.model.interpolation;

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