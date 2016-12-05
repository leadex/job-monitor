package jobmonitor.backend.api.util;

import jobmonitor.backend.api.model.ClientDTO;
import ru.leadexsystems.startup.jobmonitor.common.model.Client;

/**
 * Created by ekabardinsky on 11/28/16.
 */
public class DTOMapper {
    public static Client getClientFromDTO(ClientDTO dto) {
        Client client = new Client();
        client.setNotification(dto.getNotification());
        client.setQuery(dto.getQuery());
        return client;
    }

    public static ClientDTO getDTOFromClient(Client client) {
        ClientDTO dto = new ClientDTO();
        if (client != null) {
            dto.setQuery(client.getQuery());
            dto.setNotification(client.getNotification());
        }
        return dto;
    }
}
