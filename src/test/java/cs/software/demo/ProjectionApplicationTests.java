package cs.software.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import data.dtos.ViewerDTO;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static cs.software.demo.OAuthUtils.getOauthAuthenticationFor;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class ProjectionApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private MockMvc mvc;

	@MockBean
	private ClientRegistrationRepository clientRegistrationRepository;

	@Test
	void getAllPredictionOK() throws Exception {
		var principal = OAuthUtils.createOAuth2User("Team Kawaii", "tkawaiiutec@gmail.com");
		mvc.perform(get("/viewers").with(authentication(getOauthAuthenticationFor(principal)))).andExpect(status().isOk());
	}

	@Test
	void getAllPredictionUnautherized() throws Exception {
		var principal = OAuthUtils.createOAuth2User("UTEC", "utec@gmail.com");
		mvc.perform(get("/viewers").with(authentication(getOauthAuthenticationFor(principal)))).andExpect(status().isUnauthorized());
	}


	@Test
	void authenticateViewer() throws Exception {
		mvc.perform(get("/viewers/auth")).andExpect(status().isFound());
	}

	@Test
	void authenticateTI() throws Exception {
		mvc.perform(get("/ti/auth")).andExpect(status().isFound());
	}

	@Test
	void setCoursesOK() throws Exception{
		var principal = OAuthUtils.createOAuth2User("Team Kawaii", "tkawaiiutec@gmail.com");
		mvc.perform(get("/ti/update").with(authentication(getOauthAuthenticationFor(principal)))).andExpect(status().isOk());
	}

	@Test
	void setCoursesUnauthorized() throws Exception{
		var principal = OAuthUtils.createOAuth2User("UTEC", "utec@gmail.com");
		mvc.perform(get("/ti/update").with(authentication(getOauthAuthenticationFor(principal)))).andExpect(status().isUnauthorized());
	}

	@Test
	void addViewerForbidden() throws Exception {
		var principal = OAuthUtils.createOAuth2User("Team Kawaii", "tkawaiiutec@gmail.com");
		mvc.perform(post("/ti/add")
				.content(asJsonString(new ViewerDTO("jose.huby@utec.edu.pe")))
				.contentType(MediaType.APPLICATION_JSON)
				.with(authentication(getOauthAuthenticationFor(principal)))).andExpect(status().isForbidden());
	}

	@Test
	void deleteViewerForbidden() throws Exception {
		var principal = OAuthUtils.createOAuth2User("Team Kawaii", "tkawaiiutec@gmail.com");
		mvc.perform(post("/ti/add")
				.content(asJsonString(new ViewerDTO("jose.huby@utec.edu.pe")))
				.contentType(MediaType.APPLICATION_JSON)
				.with(authentication(getOauthAuthenticationFor(principal)))).andExpect(status().isForbidden());
	}




	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}




}
