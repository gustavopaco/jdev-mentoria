package com.pacoprojects.util;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class GenerateMiniatureImage {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;


    public String getMiniature(String image64) {


        try {
            // Convertendo Base64 para byte[]
            byte[] imageByteArray = Base64.getDecoder().decode(image64);

            // Gerando um InputStream de Imagem
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageByteArray));

            // Verificando o tipo da imagem
            int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();

            // Configurando a imagem em um Buffer com "LARGURA", "ALTURA" e "Tipo" da Imagem
            BufferedImage scaledImage = new BufferedImage(WIDTH, HEIGHT, type);

            // Gerando Imagem a partir das configuracoes
            Graphics2D graphics2D = scaledImage.createGraphics();
            graphics2D.drawImage(bufferedImage, 0 ,0, WIDTH, HEIGHT, null);
            graphics2D.dispose();

            // Imprimindo a imagem no formato png
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(scaledImage, "png", byteArrayOutputStream);

            // Formatando a imagem para Base64
            String miniatureImg = "data:image/png;base64," + Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());

            byteArrayOutputStream.flush();
            byteArrayOutputStream.close();
            bufferedImage.flush();
            scaledImage.flush();
            return miniatureImg;

        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Falha ao converter imagem no servidor, por favor entre em contato com o Administrador");
        }
    }
}
