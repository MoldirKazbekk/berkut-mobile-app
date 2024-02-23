package kz.sdu.edu.berkutapp.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public enum UserType {
    @JsonAlias({"parent"})
    PARENT,
    @JsonAlias({"child"})
    CHILD;
}
