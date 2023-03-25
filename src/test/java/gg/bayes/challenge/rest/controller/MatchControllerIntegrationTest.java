package gg.bayes.challenge.rest.controller;

import gg.bayes.challenge.rest.services.MatchService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/*
 * Integration test template to get you started. Add tests and make modifications as you see fit.
 */
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class MatchControllerIntegrationTest {

    private static final String COMBATLOG_FILE_1 = "/data/combatlog_1.log.txt";
    private static final String COMBATLOG_FILE_2 = "/data/combatlog_2.log.txt";

    @Autowired
    private MockMvc mvc;

    private Map<String, Long> matchIds;

    @InjectMocks
    private MatchController matchController;
    @Mock
    private MatchService matchService;

    @BeforeAll
    void setup() throws Exception {
        // Populate the database with all events from both sample data files and store the returned
        // match IDS.
        matchIds = Map.of(
                COMBATLOG_FILE_1, ingestMatch(COMBATLOG_FILE_1),
                COMBATLOG_FILE_2, ingestMatch(COMBATLOG_FILE_2));
    }

    // TODO: add your tests
    // Replace this test method with the tests that you consider appropriate to test your implementation.
    @Test
    void someTest() {
        assertThat(mvc).isNotNull();
    }

    /**
     * Helper method that ingests a combat log file and returns the match id associated with all parsed events.
     *
     * @param file file path as a classpath resource, e.g.: /data/combatlog_1.log.txt.
     * @return the id of the match associated with the events parsed from the given file
     * @throws Exception if an error happens when reading or ingesting the file
     */
    private Long ingestMatch(String file) throws Exception {
        String fileContent = IOUtils.resourceToString(file, StandardCharsets.UTF_8);

        return Long.parseLong(mvc.perform(post("/api/match")
                                         .contentType(MediaType.TEXT_PLAIN)
                                         .content(fileContent))
                                 .andReturn()
                                 .getResponse()
                                 .getContentAsString());
    }

    @Test
    public void getMatchTest() throws Exception {
        String uri = "/api/match/1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).content(String.valueOf(1))
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        assertThat(content);
    }

    @Test
    public void getHeroItem() throws Exception {
        String uri = "/api/match/1/maro/items";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).content(String.valueOf(1))
                        .content("")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        assertThat(content);
    }

    @Test
    public void getHeroSpellTest() throws Exception {
        String uri = "/api/match/1/maro/spells";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).content(String.valueOf(1))
                .content("maro")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        assertThat(content);
    }

    @Test
    public void getHeroDamageTest() throws Exception {
        String uri = "/api/match/1/maro/damage";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).content(String.valueOf(1))
                .content("maro")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        assertThat(content);
    }
}
