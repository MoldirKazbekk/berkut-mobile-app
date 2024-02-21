package kz.sdu.edu.berkutapp.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public enum UserTypeEnum {
    @JsonAlias({"parent"})
    PARENT,
    @JsonAlias({"child"})
    CHILD;
}
