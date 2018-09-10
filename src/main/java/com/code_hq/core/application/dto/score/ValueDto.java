package com.code_hq.core.application.dto.score;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = ValueDtoDeserializer.class)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true, defaultImpl = SimpleValueDto.class)
@JsonSubTypes({
    @JsonSubTypes.Type(value = SimpleValueDto.class, name = "simple"),
    @JsonSubTypes.Type(value = FractionalValueDto.class, name = "fraction"),
})
public abstract class ValueDto
{
}
