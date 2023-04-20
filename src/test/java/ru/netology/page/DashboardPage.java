package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");

    public DashboardPage() {
        heading.shouldBe(visible);
    }


    public String getCardIDByIndex(int cardIndex) {
        return $$("ul[class*='CardList'] div").get(cardIndex - 1).getAttribute("data-test-id");
    }

    public int sumCheckByIndex(int cardIndex) {
        reload();
        String[] splitMessage = ($("[data-test-id='" + getCardIDByIndex(cardIndex) + "']")
                .text()).split(":");
        return Integer.parseInt((splitMessage[1]).substring(0, (splitMessage[1]).indexOf("р.")).trim());
    }

    public TransferPage moneyTransferByIndex(int cardToIndex) {
        $("[data-test-id='" + getCardIDByIndex(cardToIndex) + "'] button").click();
        return new TransferPage();
    }

    public DashboardPage reload() {
        $("button[data-test-id='action-reload']").click();
        return this;
    }
}