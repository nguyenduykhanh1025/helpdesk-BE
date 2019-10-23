package com.backend.helpdesk.response;

import lombok.Data;

@Data
public class NumberOfDayOff {
    private float used;
    private float remaining;
}
