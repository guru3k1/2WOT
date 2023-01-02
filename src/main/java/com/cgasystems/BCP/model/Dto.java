package com.cgasystems.BCP.model;

import javax.validation.constraints.NotNull;

public class Dto {

    public record User(
            @NotNull(message = "Name cannot be Null")
            String name
    ){}
}
