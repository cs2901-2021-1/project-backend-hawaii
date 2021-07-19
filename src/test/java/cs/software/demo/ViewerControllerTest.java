package cs.software.demo;

import controller.ViewerController;
import cs.software.demo.OAuthUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static cs.software.demo.OAuthUtils.getOauthAuthenticationFor;
import static java.lang.System.lineSeparator;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ViewerController.class)
public class ViewerControllerTest {
    
    @Autowired
    private MockMvc mvc;
    
    @MockBean
    private ClientRegistrationRepository clientRegistrationRepository;
    
    private OAuth2User principal;
    
    @Before
    public void setUpUser() {

        principal = OAuthUtils.createOAuth2User(
                "Kawaii", "tkawaiiutec@gmail.com");
    }

    @Test
    public void getAllPrediction() throws Exception {
        mvc.perform(get("/viewers").with(authentication(getOauthAuthenticationFor(principal)))).andExpect(status().isFound());
    }
}
