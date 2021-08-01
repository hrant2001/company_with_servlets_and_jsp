package com.hrant.dto;

import java.util.Objects;

public class PositionDto {
    private int positionId;
    private String name;
    private String shortName;

    public PositionDto() {
    }

    public PositionDto(int positionId, String name, String shortName) {
        this.positionId = positionId;
        this.name = name;
        this.shortName = shortName;
    }

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PositionDto positionDto = (PositionDto) o;
        return positionId == positionDto.positionId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(positionId, name, shortName);
    }

    @Override
    public String toString() {
        return positionId + " " + name + " " + shortName;
    }
}
