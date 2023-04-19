package ru.netology.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

class MoneyTransferTest {

    @BeforeEach
    // Логинимся и возвращаем SUT в исходное состояние после предыдущих тестов
    void shouldLoginAndRestoreSUT() {
        open("http://localhost:9999");
        var authInfo = DataHelper.getValidAuthInfo();
        new LoginPage()
                .validLogin(authInfo)
                .validVerify(DataHelper.getVerificationCodeFor(authInfo));
        DashboardPage.restoreSUT();
    }

    @Test
    void shouldTransferMoneyFromFirstToSecondCard() {
        var authInfo = DataHelper.getValidAuthInfo();

        int card1BeforeTransfer = DashboardPage.sumCheck(DataHelper.getCard1(authInfo));
        int card2BeforeTransfer = DashboardPage.sumCheck(DataHelper.getCard2(authInfo));

        int sumForTransfer = 9000;
        DashboardPage.moneyTransfer(DataHelper.getCard1(authInfo), DataHelper.getCard2(authInfo), sumForTransfer );

        int card1AfterTransfer = DashboardPage.sumCheck(DataHelper.getCard1(authInfo));
        int card2AfterTransfer = DashboardPage.sumCheck(DataHelper.getCard2(authInfo));

        Assertions.assertEquals((card1BeforeTransfer - sumForTransfer), card1AfterTransfer);
        Assertions.assertEquals((card2BeforeTransfer + sumForTransfer), card2AfterTransfer);
    }


    @Test
    void shouldTransferMoneyFromSecondToFirstCard() {
        var authInfo = DataHelper.getValidAuthInfo();

        int card1BeforeTransfer = DashboardPage.sumCheck(DataHelper.getCard1(authInfo));
        int card2BeforeTransfer = DashboardPage.sumCheck(DataHelper.getCard2(authInfo));

        int sumForTransfer = 4000;
        DashboardPage.moneyTransfer(DataHelper.getCard2(authInfo), DataHelper.getCard1(authInfo), sumForTransfer );

        int card1AfterTransfer = DashboardPage.sumCheck(DataHelper.getCard1(authInfo));
        int card2AfterTransfer = DashboardPage.sumCheck(DataHelper.getCard2(authInfo));

        Assertions.assertEquals((card2BeforeTransfer - sumForTransfer), card2AfterTransfer);
        Assertions.assertEquals((card1BeforeTransfer + sumForTransfer), card1AfterTransfer);
    }

    @Test
    void shouldNotTransferMoneyBecauseAmountIsNotEnough() {
        var authInfo = DataHelper.getValidAuthInfo();

        int card1BeforeTransfer = DashboardPage.sumCheck(DataHelper.getCard1(authInfo));
        int card2BeforeTransfer = DashboardPage.sumCheck(DataHelper.getCard2(authInfo));

        int sumForTransfer = 12000;
        DashboardPage.moneyTransfer(DataHelper.getCard1(authInfo), DataHelper.getCard2(authInfo), sumForTransfer );

        int card1AfterTransfer = DashboardPage.sumCheck(DataHelper.getCard1(authInfo));
        int card2AfterTransfer = DashboardPage.sumCheck(DataHelper.getCard2(authInfo));

        Assertions.assertEquals(card1BeforeTransfer, card1AfterTransfer);
        Assertions.assertEquals(card2BeforeTransfer, card2AfterTransfer);
    }

    @Test
    void shouldShowErrorMessageIfBadCardNumber() {
        var authInfo = DataHelper.getValidAuthInfo();

        int sumForTransfer = 3000;
        DashboardPage.moneyTransfer(DataHelper.getInvalidCard(authInfo), DataHelper.getCard2(authInfo), sumForTransfer );
        $("[data-test-id='error-notification']").should(appear);
    }

}

