package com.hrant.util;

import com.hrant.dto.DepartmentDto;
import com.hrant.dto.PositionDto;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

public class ServletUtil {

    public static final String POSITION_ATTR = "positions";
    public static final String DEPARTMENT_ATTR = "departments";

    public static String getPositionsAsString(List<PositionDto> positions) {
        List<String> positionsAsString = positions.stream().map(PositionDto::getName).collect(Collectors.toList());
        return StringUtils.join(positionsAsString, ",");
    }

    public static String getDepartmentsAsString(List<DepartmentDto> departments) {
        List<String> departmentsAsString = departments.stream().map(DepartmentDto::getName).collect(Collectors.toList());
        return StringUtils.join(departmentsAsString, ",");
    }
}
