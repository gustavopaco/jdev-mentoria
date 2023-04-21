// ResponseJunoCriarChavePixDto.java

//YApi QuickType插件生成，具体参考文档:https://github.com/RmondJone/YapiQuickType

package com.pacoprojects.api.integration.juno.cobranca.criar.chave.pix.response;

import java.util.List;

public record ResponseJunoCriarChavePixDto(

        String key,

        String creationDateTime,

        String ownershipDateTime,

        List<ResponseCriarChavePixLinkDto> _links) {

}
