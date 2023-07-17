package guru.qa.tests.file;

import com.codeborne.pdftest.PDF;
import guru.qa.tests.TestBase;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileTests extends TestBase {

    @BeforeEach
    void init() {
        open("https://spb.megafon.ru/pay/topup/");

    }
    @Test
    @Tag("file")
    @Owner("EphimSh")
    @Feature("Скачивание файла")
    @Story("Проверка имени автора документа")
    @DisplayName("Имя автора верное")
    void downloadedPdfCheck() throws IOException {
        File file = $(".download-link__link a").download();
        InputStream is = new FileInputStream(file);
        PDF pdf = new PDF(is);

        assertEquals("Sergey Solyakov (HQ)", pdf.author);
    }


}
