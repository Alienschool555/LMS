package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

public final class AddNewCourseModal extends BaseModal<AddNewCourseModal> {
    private final Locator mvpSubscribeButton =
      locator("//span[text()='MVP']/parent::div/parent::div/button[text()='Subscribe']");
    private final Locator chooseAProductHeading = locator("#chooseProduct>div>div>span");
    private final Locator mvpHeader = exactText("MVP");
    private final Locator allStarHeader = exactText("All-Star");
    private final Locator rookieHeader = exactText("Rookie");

    AddNewCourseModal(Page page) {
        super(page);
    }

    @Override
    public AddNewCourseModal init() {

        return new AddNewCourseModal(getPage());
    }

    @Step("Click MVP plan 'Subscribe' button.")
    public StripeModal clickMVPSubscribeButton() {
        mvpSubscribeButton.click();

        return new StripeModal(getPage()).init();
    }

    public Locator getChooseAProductHeading() {

        return chooseAProductHeading;
    }

    public Locator getMvpSubscribeButton() {

        return mvpSubscribeButton;
    }

    public Locator getMVPHeading() {

        return mvpHeader;
    }

    public Locator getAllStarHeading() {

        return allStarHeader;
    }

    public Locator getRookieHeading() {

        return rookieHeader;
    }
}
