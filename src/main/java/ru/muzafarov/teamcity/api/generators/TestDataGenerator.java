package ru.muzafarov.teamcity.api.generators;

import ru.muzafarov.teamcity.api.models.*;

import java.util.Arrays;

import static ru.muzafarov.teamcity.api.enums.Role.SYSTEM_ADMIN;
import static ru.muzafarov.teamcity.api.generators.RandomData.getString;

public class TestDataGenerator {

    public static TestData generate() {
        NewProjectDescription newProjectDescription = generateNewProjectDescription();
        return TestData.builder()
                .user(generateUser(SYSTEM_ADMIN))
                .project(newProjectDescription)
                .buildType(generateBuildType(newProjectDescription))
                .build();
    }

    public static Roles generateRole(ru.muzafarov.teamcity.api.enums.Role role) {
        return Roles.builder()
                .role(Arrays.asList(Role.builder()
                        .roleId(role.getText())
                        .scope("g")
                        .build()))
                .build();
    }

    public static Roles generateRole(ru.muzafarov.teamcity.api.enums.Role role, String scope) {
        return Roles.builder()
                .role(Arrays.asList(Role.builder()
                        .roleId(role.getText())
                        .scope(scope)
                        .build()))
                .build();
    }

    public static NewProjectDescription generateNewProjectDescription() {
        return NewProjectDescription
                .builder()
                .parentProject(Project.builder()
                        .locator("_Root")
                        .build())
                .name("project_" + getString())
                .id(getString())
                .copyAllAssociatedSettings(true)
                .build();
    }

    public static User generateUser(ru.muzafarov.teamcity.api.enums.Role role) {
        return User.builder()
                .userName(getString())
                .password(getString())
                .email(getString() + "@gmail.com")
                .roles(generateRole(role))
                .build();
    }

    public static BuildType generateBuildType(NewProjectDescription newProjectDescription) {
        return BuildType.builder()
                .id(getString())
                .name(getString())
                .project(newProjectDescription.toProject())
                .projectName(newProjectDescription.getName())
                .build();
    }
}
