package hankk20.spring.openapi_restdoc.join;

import com.epages.restdocs.apispec.ConstrainedFields;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

        ConstrainedFields cf = new ConstrainedFields(JoinRequest.class);

        mockMvc.perform(post("/join")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(joinRequest)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(Status.ENABLE.toString()))
                .andExpect(status().isOk())
                .andDo(document("join", "????????????",requestFields(cf.withPath("name").description("??????"),
                        cf.withPath("email").description("?????????"),
                        cf.withPath("position").description("??????"),
                        cf.withPath("phoneNumber").description("????????????"),
                        cf.withPath("address").optional().description("?????????")),
                        responseFields(fieldWithPath("id").description("?????? ?????????"),
                                fieldWithPath("status").description("????????????"))
                        ))
                .andDo(print());

    }

}