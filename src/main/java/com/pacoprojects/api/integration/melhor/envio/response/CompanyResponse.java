package com.pacoprojects.api.integration.melhor.envio.response;

import java.io.Serializable;

public record CompanyResponse(Integer id, String name, String picture) implements Serializable {
}
