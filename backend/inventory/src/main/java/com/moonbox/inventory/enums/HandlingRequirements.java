package com.moonbox.inventory.enums;

import java.awt.*;
import java.awt.color.ColorSpace;

public enum HandlingRequirements {

    COLD_CHAIN("COLD CHAIN"),
    CONTROLLED_SUBSTANCE("CONTROLLED SUBSTANCE"),
    HAZARDOUS_MATERIAL("HAZARDOUS MATERIAL"),
    UNDEFINED("UNDEFINED");

    private final String handlingRequirement;

    HandlingRequirements(String handlingRequirement) {
        this.handlingRequirement = handlingRequirement;
    }

    public String getHandlingRequirement() {
        return handlingRequirement;
    }
}
