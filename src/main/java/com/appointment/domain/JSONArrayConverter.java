package com.appointment.domain;

import org.json.JSONArray;
import org.json.JSONException;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class JSONArrayConverter implements AttributeConverter<JSONArray, String> {
    @Override
    public String convertToDatabaseColumn(JSONArray jsonData) {
        String json;
        try{
            json = jsonData.toString();
        }
        catch (NullPointerException ex)
        {
            ex.printStackTrace();
            json = "";
        }
        return json;
    }

    @Override
    public JSONArray convertToEntityAttribute(String jsonDataAsJson) {
        JSONArray jsonData;
        try {
            jsonData = new JSONArray(jsonDataAsJson);
        } catch (JSONException ex) {
            ex.printStackTrace();
            jsonData = null;
        }
        return jsonData;
    }
}
