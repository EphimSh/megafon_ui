package guru.qa.tests.ui;

import com.github.javafaker.Faker;
import guru.qa.tests.TestBase;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;

public class TopUpPayTest extends TestBase {

    public static Faker faker = new Faker();

    @BeforeEach
    void init() {
        step("Перейти на страницу пополнения баланса", () -> {
            open("https://spb.megafon.ru/pay/topup/");
        });
    }

    @Tag("web")
    @Owner("EphimSh")
    @Feature("Пополнение баланса")
    @Story("Недопустимое пополнение баланса")
    @DisplayName("Пополнение баланса недопустимой суммой")
    @ValueSource(strings = {"0", "0,1", "9,99", "15001"})
    @ParameterizedTest(name = "Пополнение баланса на {0} невозможно")
    void unacceptableSumTopUpCheck(String sum) {
        step("Ввести случайный номер в поле для ввода: ", () -> {
            $("[name=ONLINE_PAYMENT_PHONE_NUMBER]").click();
            $("[name=ONLINE_PAYMENT_PHONE_NUMBER]")
                    .sendKeys(faker.phoneNumber().subscriberNumber(10));
        });
        step("Ввести сумму: " + sum, ()->{
            $("[name=ONLINE_PAYMENT_SUM]").setValue(sum).pressEnter();
        });
        step("Пополнение невозможно",()->{
            $("[class*=field-bottom-wrapper_filled]")
                    .shouldHave(text("От 10 до 15000 ₽"))
                    .should(cssValue("color", "rgba(246, 36, 52, 1)"));
        });
    }

    @Test
    @Tag("web")
    @Owner("EphimSh")
    @Feature("Пополнение баланса")
    @Story("Недопустимое пополнение баланса")
    @DisplayName("Пополнение баланса на незарегистрированый номер невозможно")
    void unRegisteredNumberTopUpCheck() {
        String sum = String.valueOf(faker.random().nextInt(10,15000));
        step("Ввести случайный номер в поле для ввода: ", () -> {
            $("[name=ONLINE_PAYMENT_PHONE_NUMBER]").click();
            $("[name=ONLINE_PAYMENT_PHONE_NUMBER]")
                    .sendKeys(faker.phoneNumber().cellPhone());
        });
        step("Ввести сумму: " + sum, ()->{
            $("[name=ONLINE_PAYMENT_SUM]").setValue(sum).pressEnter();
        });
        step("Пополнение невозможно: Телефон не найден",()->{
            $("[class*=field-bottom-wrapper_filled]")
                    .shouldHave(text("Телефон не найден"))
                    .should(cssValue("color", "rgba(246, 36, 52, 1)"));
        });
    }
}
