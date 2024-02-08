package ru.muzafarov.teamcity.ui;

import org.testng.annotations.Test;
import ru.muzafarov.teamcity.ui.pages.FirstStartPage;

public class SetupFirstStartTest extends BaseUiTest {
    @Test
    public void setupTeamCityServerTest() {
        new FirstStartPage().open()
                .proceed()
                .waitUntilPageIsLoaded();
        new FirstStartPage().proceed()
                .waitUntilPageIsLoaded();
        new FirstStartPage().acceptLicenseAgreement()
                .createAdmin();
    }
}
