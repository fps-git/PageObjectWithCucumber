package ru.netology.steps;

import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import org.junit.jupiter.api.Assertions;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.TransferPage;


public class TemplateSteps {

    DashboardPage dashboard;
    @Пусть("пользователь залогинен с именем {string} и паролем {string},")
    public void login(String login, String password) {
        var authInfo = new DataHelper.AuthInfo(login, password);
        dashboard = new LoginPage().validLogin(authInfo)
                .validVerify("12345");

        // Логинимся и возвращаем SUT в исходное состояние, если необходимо:
        int card1Sum = dashboard.sumCheckByIndex(1);

        if (card1Sum < 10_000) {
            dashboard.moneyTransferByIndex(1)
                    .makeSuccessTransfer(DataHelper.getCard2(authInfo).getCardNumber(), 10_000 - card1Sum);
        } else {
            if (card1Sum > 10_000) {
                dashboard.moneyTransferByIndex(2)
                        .makeSuccessTransfer(DataHelper.getCard1(authInfo).getCardNumber(), card1Sum - 10_000);
            }
        }
    }

    @Когда("пользователь переводит {int} рублей с карты с номером {string} на свою {int} карту с главной страницы,")
    public void moneyTransfer(int sum, String cardFrom, int cardIndex) {
        dashboard.moneyTransferByIndex(cardIndex).makeSuccessTransfer(cardFrom, sum);
    }

    @Тогда("баланс его {int} карты из списка на главной странице должен стать {int} рублей.")
    public void checkFirstCardSum(int cardIndex, int sumToCheck) {
        Assertions.assertEquals(sumToCheck, dashboard.sumCheckByIndex(cardIndex));
    }


    @Когда("пользователь переводит {int} рублей с несуществующей карты с номером {string} на свою {int} карту с главной страницы,")
    public void notSuccessMoneyTransfer(int sum, String cardFrom, int cardIndex) {
        dashboard.moneyTransferByIndex(cardIndex).makeNotSuccessTransfer(cardFrom, sum);
    }

    @Тогда("выдается сообщение об ошибке с текстом {string}.")
    public void checkErrorMessage(String errorMessage) {
        var transferPage = new TransferPage();
        transferPage.checkErrorMessage(errorMessage);
    }


}

