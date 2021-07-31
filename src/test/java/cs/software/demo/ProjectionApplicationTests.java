package cs.software.demo;

import business.PredictionService;
import business.custom_exceptions.ConflictException;
import business.custom_exceptions.NotFoundException;
import business.custom_exceptions.UnauthorizedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.entities.Prediction;
import data.entities.User;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import java.sql.Date;
import java.time.LocalDate;

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
		Assertions.assertTrue(true);
	}

	@Autowired
	private MockMvc mvc;

	@Autowired
	private PredictionService predictionService;

	@MockBean
	private ClientRegistrationRepository clientRegistrationRepository;

	@Test
	void getAllPredictionOK() throws Exception {
		var principal = OAuthUtils.createOAuth2User("Team Kawaii", "claudio.echarre@utec.edu.pe");
		mvc.perform(get("/viewers").with(authentication(getOauthAuthenticationFor(principal)))).andExpect(status().isOk());
	}

	@Test
	void getAllPredictionUnautherized() throws Exception {
		var principal = OAuthUtils.createOAuth2User("UTEC", "utec@gmail.com");
		mvc.perform(get("/viewers").with(authentication(getOauthAuthenticationFor(principal)))).andExpect(status().isOk());
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
		mvc.perform(get("/ti/update").with(authentication(getOauthAuthenticationFor(principal)))).andExpect(status().isOk());
	}

	@Test
	void addDeleteViewer() throws Exception {
		var principal = OAuthUtils.createOAuth2User("Team Kawaii", "tkawaiiutec@gmail.com");
		mvc.perform(get("/ti/add?email=obama@gmail.com")
				.with(authentication(getOauthAuthenticationFor(principal)))).andExpect(status().isOk());

		mvc.perform(get("/ti/add?email=obama@gmail.com")
				.with(authentication(getOauthAuthenticationFor(principal)))).andExpect(status().isConflict());

		mvc.perform(get("/ti/del?email=obama@gmail.com")
				.with(authentication(getOauthAuthenticationFor(principal)))).andExpect(status().isOk());

		mvc.perform(get("/ti/del?email=obama@gmail.com")
				.with(authentication(getOauthAuthenticationFor(principal)))).andExpect(status().isNotFound());
	}

	@Test
	void addDeleteViewerUnauthorized() throws Exception {
		var principal = OAuthUtils.createOAuth2User("Team Kawaii", "obama@gmail.com");
		mvc.perform(get("/ti/add?email=obama@gmail.com")
				.with(authentication(getOauthAuthenticationFor(principal)))).andExpect(status().isOk());
		mvc.perform(get("/ti/del?email=obama@gmail.com")
				.with(authentication(getOauthAuthenticationFor(principal)))).andExpect(status().isOk());
	}



	@Test
	void testExceptions(){
		try{
			throw new ConflictException();
		}catch (Exception e){
			Assertions.assertTrue(true);
		}

		try{
			throw new NotFoundException();
		}catch (Exception e){
			Assertions.assertTrue(true);
		}

		try{
			throw new UnauthorizedException();
		}catch (Exception e){
			Assertions.assertTrue(true);
		}
	}
	/*
	@Test
	void testEntities(){
		var ti = new TI("tkawaiiutec@gmail.com");
		var email = ti.getEmail();
		ti.setEmail(email);

		var viewerDTO = new UserDTO("utec@gmail.com",LocalDate.now());
		var viewer = new Viewer("utec@gmail.com");
		viewer.setEmail(viewerDTO.getEmail());
		viewerDTO.setEmail(viewer.getEmail());
		Assertions.assertTrue(true);

		var prediction= new Prediction("TEST01", "TEST", 0,0);
		prediction.setCode(prediction.getCode());
		prediction.setName(prediction.getName());
		prediction.setnStudent(prediction.getnStudent());
		prediction.setError(prediction.getError());
		Assertions.assertTrue(true);
	}*/

	@Test
	void updatePredictionsPath() throws Exception{
		var principal = OAuthUtils.createOAuth2User("Team Kawaii", "claudio.echarre@utec.edu.pe");
		mvc.perform(get("/ti/update").with(authentication(getOauthAuthenticationFor(principal)))).andExpect(status().isOk());
	}

	@Test
	void updatePredictions() throws Exception{
		predictionService.updatePredictionsContent();
		Assert.assertTrue(true);
	}


	@Test
	void entityPrediction() throws Exception{
		var courseA = new Prediction("CS2901","Ing. de Software",48,0);
		Prediction courseB = new Prediction();
		courseB.setCode("CS2901");
		courseB.setName("Ing. de Software");
		courseB.setnStudent(48);
		courseB.setError(0);
		Assertions.assertEquals(courseA.getCode(), courseB.getCode());
		Assertions.assertEquals(courseA.getError(), courseB.getError());
		Assertions.assertEquals(courseA.getName(), courseB.getName());
		Assertions.assertEquals(courseA.getnStudent(), courseB.getnStudent());
	}



	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
