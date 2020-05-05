package org.sudhs;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Assert;
import org.junit.Test;

public class JsonDeltaTest {

    @Test
    public void testFindDelta() throws JsonProcessingException {
        String firstJson = "{\"order\": {\"id\": \"Order 1\",\"item\": {\"id\": \"124\",\"name\": \"drugs\",\"quantity\": \"100\",\"strength\":\"medium\"}}}";
        String secondJson ="{\"order\": {\"id\": \"Order 1\",\"item\": {\"id\": \"124\",\"name\": \"drugs\",\"quantity\": \"200\",\"strength\":\"strong\"}}}";

        JsonDelta delta = new JsonDelta();
        String deltaJson = delta.findDelta(firstJson,secondJson);
        Assert.assertNotNull(deltaJson);
        Assert.assertTrue(deltaJson.contains("\"oldValue\" : \"medium\","));
        Assert.assertTrue(deltaJson.contains("\"newValue\" : \"strong\""));
        Assert.assertTrue(deltaJson.contains("\"oldValue\" : \"100\""));
        Assert.assertTrue(deltaJson.contains("\"newValue\" : \"200\""));



    }
}
