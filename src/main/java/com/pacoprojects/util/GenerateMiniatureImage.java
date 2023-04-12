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

    private static final int WIDTH = 515;
    private static final int HEIGHT = 290;


    public String getMiniature(String image64) {

        try (ByteArrayInputStream bais = new ByteArrayInputStream(Base64.getDecoder().decode(image64));
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            BufferedImage bufferedImage = ImageIO.read(bais);
            int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();
            BufferedImage resizedImage = resizeImage(bufferedImage, WIDTH, HEIGHT, type);

            ImageIO.write(resizedImage, "png", baos);

            return "data:image/png;base64," + Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Falha ao converter imagem no servidor, por favor entre em contato com o Administrador");
        }
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int width, int height, int type) {
        BufferedImage resizedImage = new BufferedImage(width, height, type);

        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, width, height, null);
        graphics2D.dispose();

        return resizedImage;
    }

    private String getMiniatureOld(String image64) {

        if (image64.contains("data:image/") || image64.contains(";base64,")) {
            image64 = image64.split(",")[1];
        }

        try {

            byte[] imageByteArray = Base64.getDecoder().decode(image64);

            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageByteArray));

            int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();
            BufferedImage resizedImage = new BufferedImage(WIDTH, HEIGHT, type);

            Graphics2D graphics2D = resizedImage.createGraphics();
            graphics2D.drawImage(bufferedImage, 0, 0, WIDTH, HEIGHT, null);
            graphics2D.dispose();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(resizedImage, "png", baos);

            String miniImageBase64 = "data:image/png;base64," + Base64.getEncoder().encodeToString(baos.toByteArray());

            bufferedImage.flush();
            resizedImage.flush();
            baos.flush();
            baos.close();
            return miniImageBase64;

        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Falha ao converter imagem no servidor, por favor entre em contato com o Administrador");
        }
    }
}
