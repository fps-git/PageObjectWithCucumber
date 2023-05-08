package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {

    private SelenideElement transferSumField = $("[data-test-id='amount'] input");
    private SelenideElement fromField = $("[data-test-id='from'] input");
    private SelenideElement transferButton = $("[data-test-id='action-transfer']");

    public TransferPage() {
        transferSumField.shouldBe(visible);
        fromField.shouldBe(visible);
        transferButton.shouldBe(visible);
    }

    private void fillDataAndSend(String cardFrom, int sum) {
        transferSumField.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        transferSumField.setValue(String.valueOf(sum));
        fromField.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        fromField.setValue(cardFrom);
        transferButton.click();
    }

    public DashboardPage makeSuccessTransfer(String cardFrom, int sum) {
        fillDataAndSend(cardFrom, sum);
        return new DashboardPage();
    }

    public TransferPage makeNotSuccessTransfer(String cardFrom, int sum) {
        fillDataAndSend(cardFrom, sum);
        return this;
    }

    public TransferPage checkErrorMessage(String errorMessage) {
        $("[data-test-id='error-notification']").shouldHave(text(errorMessage));
        return this;
    }
}
