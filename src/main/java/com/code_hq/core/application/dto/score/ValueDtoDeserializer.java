package com.code_hq.core.application.dto.score;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValueDtoDeserializer extends StdDeserializer<ValueDto>
{
    private static Pattern fractions = Pattern.compile("^(.+)/(.+)$");

    protected ValueDtoDeserializer()
    {
        super(ValueDto.class);
    }

    @Override
    public ValueDto deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
    {
        JsonNode node = p.getCodec().readTree(p);
        String type;

        if (!node.has("type")) {
            type = "detect";
        } else {
            type = node.get("type").asText("detect");
        }

        switch (type) {
            case "simple":   return deserializeSimple(p, node);
            case "fraction": return deserializeFractional(p, node);
            case "detect":   return deserializeStringValue(p, node.asText());
        }

        throw new JsonMappingException(p, "Unable to map JSON object to ValueDto resource.");
    }

    private SimpleValueDto deserializeSimple(JsonParser p, JsonNode node) throws JsonMappingException
    {
        if (!node.hasNonNull("value")) {
            throw new JsonMappingException(p, "A value field is required for simple values.");
        }
        return new SimpleValueDto(node.get("value").asDouble());
    }

    private FractionalValueDto deserializeFractional(JsonParser p, JsonNode node) throws JsonMappingException
    {
        if (!node.hasNonNull("numerator") || !node.hasNonNull("denominator")) {
            throw new JsonMappingException(p, "Numerator and denominator fields are required for simple values.");
        }

        return new FractionalValueDto(
            node.get("numerator").asDouble(),
            node.get("denominator").asDouble()
        );
    }

    private ValueDto deserializeStringValue(JsonParser p, String stringValue) throws JsonMappingException
    {
        // Is it a fraction of the form "15/20"?
        // Note: this must be checked before calling NumberFormat::parse() because that method will convert such
        // a fraction into Double(15) and drop the rest of the string.
        Matcher m = fractions.matcher(stringValue);
        if (m.matches()) {
            try {
                Double numerator   = NumberFormat.getInstance().parse(m.group(1)).doubleValue();
                Double denominator = NumberFormat.getInstance().parse(m.group(2)).doubleValue();
                return new FractionalValueDto(numerator, denominator);
            } catch (ParseException ignored) {}
        }

        // Is it a simple number or numeric string?
        try {
            Double value = NumberFormat.getInstance().parse(stringValue).doubleValue();
            return new SimpleValueDto(value);
        } catch (ParseException ignored) {}

        throw new JsonMappingException(p, "Cannot detect value from string.");
    }
}
