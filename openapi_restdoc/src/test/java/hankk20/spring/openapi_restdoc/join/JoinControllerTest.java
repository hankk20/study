package hankk20.spring.openapi_restdoc.join;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;


@WebMvcTest(controllers = JoinController.class)
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "rest.hankk20.io", uriPort = 8080)
class JoinControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Test
    void joinTest() throws Exception {
        JoinRequest joinRequest = new JoinRequest();
        joinRequest.setAddress("address");
        joinRequest.setEmail("email@e.com");
        joinRequest.setName("han");
        joinRequest.setPosition(Position.DEVELOPER);
        joinRequest.setPhoneNumber("01022223333");

        mockMvc.perform(post("/join")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(joinRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(document("join"));

    }

}