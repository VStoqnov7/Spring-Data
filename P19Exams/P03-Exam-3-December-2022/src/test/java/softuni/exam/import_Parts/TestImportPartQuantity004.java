package softuni.exam.import_Parts;
//TestImportPartNameSIze002

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import softuni.exam.service.PartsService;
import softuni.exam.service.impl.PartsServiceImpl;

import java.io.IOException;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class TestImportPartQuantity004 {

    @Autowired
    private PartsServiceImpl partsService;

    @Test
    void testImportPartQuantity004() throws IOException {
        PartsService spyConstellationService = Mockito.spy(partsService);

        String testJSON = "[\n" +
                "  {\n" +
                "    \"partName\": \"Spark plug\",\n" +
                "    \"price\": 10.00,\n" +
                "    \"quantity\": 1\n" +
                "  },\n" +
                "  {\n" +
                "    \"partName\": \"Glow Plug\",\n" +
                "    \"price\": 0,\n" +
                "    \"quantity\": 6\n" +
                "  },\n" +
                "  {\n" +
                "    \"partName\": \"Starter drive\",\n" +
                "    \"price\": 9.99,\n" +
                "    \"quantity\": -1\n" +
                "  },\n" +
                "  {\n" +
                "    \"partName\": \"starter pinion gear\",\n" +
                "    \"price\": 2000.01,\n" +
                "    \"quantity\": null\n" +
                "  }\n" +
                "]";

        Mockito.when(spyConstellationService.readPartsFileContent()).thenReturn(testJSON);

        String expected = "Successfully imported part Spark plug - 10,00\n" +
                "Invalid part\n" +
                "Invalid part\n" +
                "Invalid part";
        String[] expectedSplit = expected.split("\\r\\n?|\\n");

        String actual = spyConstellationService.importParts();
        String[] actualSplit = actual.split("\\r\\n?|\\n");

        Assertions.assertArrayEquals(expectedSplit,actualSplit);
    }
}
