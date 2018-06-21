package ru.desided.campaign_zones_clear_spring.logic.CustomsDeserializers;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.desided.campaign_zones_clear_spring.POJO.groups.BrowsersList;
import ru.desided.campaign_zones_clear_spring.POJO.groups.CommonObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CustomDeserializer extends JsonDeserializer<BrowsersList> {
    @Override
    public BrowsersList deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);

        BrowsersList browsersList = new BrowsersList();
        List<CommonObject> list = new ArrayList<>();
        JsonNode listNode = node.get("rows");
        Iterator<JsonNode> slaids = listNode.elements();

        while (slaids.hasNext()){

            JsonNode innerNode = slaids.next();
            ObjectMapper objectMapper = new ObjectMapper();
            CommonObject browsers = objectMapper.readValue(innerNode.toString(), CommonObject.class);

            if (browsers.getVisits().compareTo(new BigDecimal(1000)) == -1) {
                continue;
            }
            list.add(browsers);
        }
        browsersList.setRows(list);
        return browsersList;
    }
}
