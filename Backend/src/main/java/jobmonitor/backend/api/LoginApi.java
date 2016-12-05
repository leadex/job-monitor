package jobmonitor.backend.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class LoginApi {

    @RequestMapping(value = "/api/isAuth", method = RequestMethod.GET)
    public ResponseEntity<String> loginAuthorizeServiceTypeGet(Principal principal) {
        HttpStatus status = HttpStatus.OK;
        if (principal != null) {
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.UNAUTHORIZED;
        }
        String body = status.name();

        return new ResponseEntity<String>("{ \"code\": \"" + body + "\" } ", status);
    }
}
