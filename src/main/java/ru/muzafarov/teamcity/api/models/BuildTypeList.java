package ru.muzafarov.teamcity.api.models;

import lombok.Data;

import java.util.List;
@Data
public class BuildTypeList {
    private Integer count;
    List<BuildType> buildType;
}
