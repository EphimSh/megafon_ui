package guru.qa.tests.ui;

import guru.qa.TestBase;
import io.qameta.allure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;

public class SearchTest extends TestBase {

    @BeforeEach
    void openMainPage() {
        step("Перейти на spb.megafon.ru", () -> {
            open("https://spb.megafon.ru");
        });
    }


    @ValueSource(strings = {"интернет", "sim-карта", "usb-модем"})
    @ParameterizedTest(name="Результат поиска содержит текст: {0}")
    @Tag("web")
    @Feature("Поисковая строка на главной странице")
    @Story("Поиск интересующих услуг и товаров")
    @Owner("EphimSh")
    @Severity(SeverityLevel.CRITICAL)
    @Link(value = "spb.megafon.ru", url = "https://spb.megafon.ru")
    @DisplayName("Ввод текста в поисковую строку")
    void searchTest(String searchItem) {

        step("Нажать на кнопку поиск", () -> {
            $("[class*=search-trigger]").click();
        });
        step("Ввести в поле поиска: " + searchItem, () ->{
            $("[data-testid=ChSearch-input]").setValue(searchItem).submit();
        });
        step("Результат поиска содержит текст: " + searchItem, ()->{
            $("[class*=search-page-results]").shouldHave(text(searchItem));
        });
    }
}
