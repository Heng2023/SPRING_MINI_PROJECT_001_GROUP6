package com.homework.spring_mini_project_001_group6.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;

public class RoleDeserializer extends JsonDeserializer<Role> {

    @Override
    public Role deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
        String roleValue = jsonParser.getText().toUpperCase();
        try {
            return Role.valueOf(roleValue);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role '" + roleValue + "'. Role must be either 'AUTHOR' or 'READER'.");
        }
    }
}
