package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public static DashboardPage moneyTransfer(DataHelper.Card cardFrom, DataHelper.Card cardTo, int sum) {
        $("[data-test-id='" + cardTo.getId() + "'] button").click();
        $("[data-test-id='amount'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='amount'] input").setValue(String.valueOf(sum));
        $("[data-test-id='from'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='from'] input").setValue(cardFrom.getCardNumber());
        $("[data-test-id='action-transfer']").click();
        return new DashboardPage();
    }

    public static int sumCheck(DataHelper.Card cardToCheck) {
        String[] splitMessage = ($("[data-test-id='" + cardToCheck.getId() + "']").text()).split(":");
        int sum = Integer.parseInt((splitMessage[1]).substring(0, (splitMessage[1]).indexOf("р.")).trim());
        return sum;
    }
    public static void restoreSUT() {
        var authInfo = DataHelper.getValidAuthInfo();
        if (sumCheck(DataHelper.getCard1(authInfo)) < 10_000) {
            moneyTransfer(DataHelper.getCard2(authInfo), DataHelper.getCard1(authInfo),
                    10_000 - sumCheck(DataHelper.getCard1(authInfo)));
        } else {
            if (sumCheck(DataHelper.getCard1(authInfo)) > 10_000) {
                moneyTransfer(DataHelper.getCard1(authInfo), DataHelper.getCard2(authInfo),
                        sumCheck(DataHelper.getCard1(authInfo)) - 10_000);
            }
        }
    }
}