package com.hrant.service;

import com.hrant.dto.PositionDto;
import com.hrant.model.Position;
import com.hrant.repository.PositionRepository;
import com.hrant.repository.Repository;
import com.hrant.util.DataSourceFactory;
import com.hrant.util.DtoConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class PositionService {
    private static Repository<Position> positionRepository = new PositionRepository();

    private static DataSource dataSource;

    static {
        dataSource = DataSourceFactory.getInstance();
    }

    private final static Logger LOGGER = LoggerFactory.getLogger(PositionService.class);

    private PositionService() {
        throw new AssertionError();
    }

    public static PositionDto findPositionById(int id) {
        Position position = null;
        try {
            position = positionRepository.findById(dataSource, id);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        if (position == null) {
            LOGGER.warn("The position with the id of " + id + " was not found in the list");
            System.out.println("The position with the id of " + id + " was not found in the list");

            return new PositionDto();
        }

        return DtoConverter.positionToDto(position);
    }

    public static List<PositionDto> getPositions() {
        List<Position> positions = new ArrayList<>();
        try {
            positions = positionRepository.getAll(dataSource);
            LOGGER.warn("The list of positions was successfully found");
            System.out.println("The list of positions was successfully found");
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        if (positions == null || positions.isEmpty()) {
            LOGGER.warn("The list of employees is empty");
            System.out.println("\nThe list of employees is empty\n");

            return Collections.emptyList();
        }
        List<PositionDto> positionsDto = new ArrayList<>();
        for (Position p : positions) {
            positionsDto.add(DtoConverter.positionToDto(p));
        }

        return positionsDto;
    }

    public static int findPositionIdByName(String name) {
        PositionDto positionDto = getPositions().stream().filter(p -> p.getName().equalsIgnoreCase(name)).findAny().orElse(null);
        return Objects.nonNull(positionDto) ? positionDto.getPositionId() : -1;
    }
}
