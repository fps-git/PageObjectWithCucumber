package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {

    private SelenideElement transferSumField = $("[data-test-id='amount'] input");
    private SelenideElement fromField = $("[data-test-id='from'] input");

    public TransferPage() {
        transferSumField.shouldBe(visible);
        fromField.shouldBe(visible);
    }

    public DashboardPage makeSuccessTransfer(String cardFrom, int sum) {
        $("[data-test-id='amount'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='amount'] input").setValue(String.valueOf(sum));
        $("[data-test-id='from'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='from'] input").setValue(cardFrom);
        $("[data-test-id='action-transfer']").click();
        return new DashboardPage();
    }

    public TransferPage makeNotSuccessTransfer(String cardFrom, int sum) {
        $("[data-test-id='amount'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='amount'] input").setValue(String.valueOf(sum));
        $("[data-test-id='from'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='from'] input").setValue(cardFrom);
        $("[data-test-id='action-transfer']").click();
        return this;
    }

    public TransferPage checkErrorMessage(String errorMessage) {
        $("[data-test-id='error-notification']").shouldHave(text(errorMessage));
        return this;
    }
}
