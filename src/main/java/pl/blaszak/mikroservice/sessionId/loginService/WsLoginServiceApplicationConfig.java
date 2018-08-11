package pl.blaszak.mikroservice.sessionId.loginService;



import java.util.Properties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;
import pl.blaszak.microservice.login.ObjectFactory;
import pl.blaszak.mikroservice.sessionId.loginService.service.LoginService;
import pl.blaszak.mikroservice.sessionId.loginService.util.LoginServiceResponseToLoginResponseMapper;

@EnableWs
@Configuration
public class WsLoginServiceApplicationConfig extends WsConfigurerAdapter {

        /*@Bean
        public LoginEndpoint createLoginEndpoint(ObjectFactory loginObjectFactory, LoginService loginService,
                                                 LoginServiceResponseToLoginResponseMapper loginServiceResponseToLoginResponseMapper) {

                return new LoginEndpoint(loginObjectFactory, loginService, loginServiceResponseToLoginResponseMapper);
        }*/

        @Bean
        public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
                MessageDispatcherServlet servlet = new MessageDispatcherServlet();
                servlet.setApplicationContext(applicationContext);
                servlet.setTransformWsdlLocations(true);
                return new ServletRegistrationBean(servlet, "/ws/*");
        }

        @Bean(name = "login")
        public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema loginSchema) {
                DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
                wsdl11Definition.setPortTypeName("LoginPort");
                wsdl11Definition.setLocationUri("/ws");
                wsdl11Definition.setTargetNamespace(LoginEndpoint.NAMESPACE_URI);
                wsdl11Definition.setSchema(loginSchema);
                Properties soapActions = new Properties();
                soapActions.setProperty("login", "http://microservice.blaszak.pl/login");
                wsdl11Definition.setSoapActions(soapActions);
                return wsdl11Definition;
        }

        @Bean
        public XsdSchema loginRsSchema() {
                return new SimpleXsdSchema(new ClassPathResource("login.xsd"));
        }

}
