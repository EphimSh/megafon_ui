package guru.qa.tests.ui;

import guru.qa.tests.TestBase;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;
@Tag("web")
@Owner("EphimSh")
@Feature("Автотесты на главную страницу")
@Link(value = "spb.megafon.ru", url = "https://spb.megafon.ru")
public class MainPageTests extends TestBase {



    @Test()
    @Tag("region")
    @Story("Смена региона")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Успешная смена региона")
    void regionConfirmCheck(){
        step("Открыть главную страницу", ()->{
            open("https://spb.megafon.ru");
        });
        step("Нажать на кнопку выбора региона", ()-> {
            $("[class*=region-trigger]").click();
        });

        step("Выбрать регион Москва и область", ()->{
            $$("[class*=salable-regions] div")
                    .find(text("Москва и область")).$("a").click();
        });
        step("Confirm-region элемент содержит текст: Москва и область", () ->{
            $("[class*=region-trigger]").shouldHave(text("Москва и область"));
        });
    }

    @Story("Все элементы меню на месте")
    @Severity(SeverityLevel.NORMAL)
    @ValueSource(strings = {
            "Тарифы", "Услуги", "Оплата", "Акции", "Поддержка"
    })
    @ParameterizedTest(name = "bottomHeader меню содержит элемент {0}")
    @DisplayName("Все элементы bottomheader меню присутсвтуют")
    void bottomHeaderMenuButtonsCheck(String menuItem){
        step("Открыть главную страницу", ()->{
            open("/");
        });
        step("bottomHeader меню содержит элемент:" + menuItem, ()->{
            $("[class*=container-v2_bottom]").shouldHave(text(menuItem));
        });
    }


    @Story("Слайдер 'сервисы' работает ")
    @Severity(SeverityLevel.CRITICAL)
    @CsvSource(value = {
                    "Онлайн‑кинотеатр START | START",
                    "Ева | Ева",
                    "МегаФон Музыка | Мегафон Музыка",
                    "Цифровая полка | Кино, музыка, книги, игры, подкасты и многое другое",
                    "МегаФон Технологии | Мобильный оператор № 1"}, delimiter = '|')
    @ParameterizedTest(name = "Кнопка слайдера {0} работает")
    @DisplayName("Кнопки слайдера сервисов работают")
    void servicesSlideCheck(String sliderButtonName, String item){
        step("Открыть главную страницу", ()->{
            open("/");
        });
        step("Нажать кнопку {0} слайдера сервисов" + sliderButtonName, ()->{
            $$("ul[class*='tab-list'] li").find(text(sliderButtonName)).click();
        });
        step("Аттрибут видимости элемента слайдера равен: true", ()->{
           $$("ul[class*='tab-list'] li").find(text(sliderButtonName))
                   .shouldHave(attribute("data-gtm-tab-title-active", "true"));
        });
        step("slider-tab виден", ()->{
            $$("[class*=main-page-services__tab-panels] [data-tab-title]")
                    .find(attribute("data-tab-title", sliderButtonName))
                    .$("a[class*=mfui-button]").click();

        });
        sleep(8000);
        step("Кнопка работает", ()->{
           $$("[class*=page]").filter(visible).find(text(item)).shouldBe(visible);
        });
    }
}
