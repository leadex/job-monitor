package jobmonitor.backend.api;

import jobmonitor.backend.api.controller.MongoController;
import jobmonitor.backend.api.model.ClientDTO;
import jobmonitor.backend.api.util.DTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.leadexsystems.startup.jobmonitor.common.exception.NotFoundParameterException;
import ru.leadexsystems.startup.jobmonitor.common.model.Client;

import java.security.Principal;

/**
 * Created by ekabardinsky on 11/25/16.
 */
@Controller
public class ClientApi {

    @Autowired
    private MongoController mongoController;

    @RequestMapping(value = "/api/client", method = RequestMethod.GET)
    public ResponseEntity<ClientDTO> getClient(Principal principal) throws IllegalAccessException, NotFoundParameterException {
        HttpStatus status = HttpStatus.OK;
        Client client = null;
        try {
            //search exist client
            client = mongoController.getClientByPrincipal(principal.getName());
        } catch (MongoController.NotFoundRegisteredClientException e) {
            //create user if not exist
            mongoController.updateOrCreateClient(principal.getName(), new Client());
        } catch (Exception e) {
            //other exception
            e.printStackTrace();
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<ClientDTO>(DTOMapper.getDTOFromClient(client), status);
    }

    @RequestMapping(value = "/api/client", method = RequestMethod.POST)
    public ResponseEntity<String> setClient(@RequestBody ClientDTO client, Principal principal) {
        HttpStatus status = HttpStatus.OK;
        try {
            mongoController.updateOrCreateClient(principal.getName(), DTOMapper.getClientFromDTO(client));
        } catch (Exception e) {
            e.printStackTrace();
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<String>("", status);
    }
}
