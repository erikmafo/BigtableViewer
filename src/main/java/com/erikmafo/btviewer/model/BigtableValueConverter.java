package com.erikmafo.btviewer.model;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Created by erikmafo on 23.12.17.
 */
public class BigtableValueConverter {

    public static BigtableValueConverter from(BigtableTableSettings config) {
        if (config == null) {
            return new BigtableValueConverter(new LinkedList<>());
        }

        return new BigtableValueConverter(config.getCellDefinitions());
    }

    private final List<CellDefinition> cellDefinitions;

    public BigtableValueConverter(List<CellDefinition> cellDefinitions) {
        this.cellDefinitions = cellDefinitions;
    }

    public List<CellDefinition> getCellDefinitions() {
        return cellDefinitions;
    }

    public Object convert(BigtableCell cell) {
        if (cell == null) {
            return null;
        }

        var cellDefinition = cellDefinitions.stream()
                .filter(c -> c.getFamily().equals(cell.getFamily())
                        && c.getQualifier().equals(cell.getQualifier()))
                .findFirst()
                .orElse(new CellDefinition("string", cell.getFamily(), cell.getQualifier()));

        try {
            return convertUsingValueType(cell, cellDefinition.getValueType());
        } catch (BufferUnderflowException ex) {
            return String.format("not a %s", cellDefinition.getValueType());
        }

    }

    private Object convertUsingValueType(BigtableCell cell, String valueType) {
        switch (valueType.toLowerCase()) {
            case "double":
                return ByteBuffer.wrap(cell.getBytes()).getDouble();
            case "integer":
                return ByteBuffer.wrap(cell.getBytes()).getInt();
            case "float":
                return ByteBuffer.wrap(cell.getBytes()).getFloat();
            default:
                return cell.getValueAsString();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BigtableValueConverter that = (BigtableValueConverter) o;
        return Objects.equals(cellDefinitions, that.cellDefinitions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cellDefinitions);
    }
}
