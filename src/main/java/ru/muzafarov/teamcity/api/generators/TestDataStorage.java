package ru.muzafarov.teamcity.api.generators;

import lombok.extern.log4j.Log4j2;
import ru.muzafarov.teamcity.api.models.NewProjectDescription;

import java.util.ArrayList;
import java.util.List;

import static ru.muzafarov.teamcity.api.generators.TestDataGenerator.generate;
import static ru.muzafarov.teamcity.api.generators.TestDataGenerator.generateNewProjectDescription;

@Log4j2
public class TestDataStorage {

    private static TestDataStorage testDataStorage;
    private List<TestData> testDataList;
    private List<NewProjectDescription> testDataProject;

    private TestDataStorage () {
        this.testDataList = new ArrayList<>();
        this.testDataProject = new ArrayList<>();
    }

    public static TestDataStorage getTestDataStorage() {
        if (testDataStorage == null) {
            testDataStorage = new TestDataStorage();
        }
        return testDataStorage;
    }

    public TestData addTestData() {
        return addTestData(generate());
    }

    public TestData addTestData(TestData testData) {
        getTestDataStorage().testDataList.add(testData);
        return testData;
    }

    public NewProjectDescription addProjectTestData() {
        return addProjectTestData(generateNewProjectDescription());
    }

    public NewProjectDescription addProjectTestData(NewProjectDescription projectDescription) {
        getTestDataStorage().testDataProject.add(projectDescription);
        return projectDescription;
    }

    public void delete() {
        log.info("==========================DELETE TEST DATA==========================");
        testDataList.forEach(TestData::deleteAll);
        testDataProject.forEach(project -> TestData.deleteProject(project.getId()));
    }
}
