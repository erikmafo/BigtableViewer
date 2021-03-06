package com.erikmafo.btviewer.model;

import java.util.Collections;
import java.util.List;

public class BigtableTableSettings {

    private List<CellDefinition> cellDefinitions;

    public BigtableTableSettings() {
        cellDefinitions = Collections.emptyList();
    }

    public BigtableTableSettings(List<CellDefinition> cellDefinitions) {
        this.cellDefinitions = cellDefinitions;
    }

    public List<CellDefinition> getCellDefinitions() {
        return cellDefinitions;
    }

    public void setCellDefinitions(List<CellDefinition> cellDefinitions) {
        this.cellDefinitions = cellDefinitions;
    }


    public CellDefinition getCellDefinition(String family, String qualifier) {
        return cellDefinitions.stream()
                .filter(cellDefinition -> cellDefinition.getFamily().equals(family) &&
                                cellDefinition.getQualifier().equals(qualifier))
                .findFirst()
                .orElse(new CellDefinition("String", family, qualifier));
    }
}
