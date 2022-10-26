package ru.ksamorodov.springauth.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.commons.io.FileUtils;
import ru.ksamorodov.springauth.application.dao.UserPrincipal;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class FileUtilService {
    private static byte[] secretKey = "9mng65v8jf4lxn93nabf981m".getBytes();
    private static byte[] iv = "a76nb5h9".getBytes();
    private static SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey, "DESede");
    private static IvParameterSpec ivSpec = new IvParameterSpec(iv);


    public static void write(List<UserPrincipal> principalList) {
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(principalList);
            FileUtils.writeStringToFile(new File("decryptedBD.txt"), json, "UTF-8");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void cryptBd() {
        try {
            Path tempFile = (new File("enctyptedBD.txt").toPath());
            byte[] fileBytes = Files.readAllBytes((new File("decryptedBD.txt")).toPath());
            Cipher encryptCipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            encryptCipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec);
            byte[] encryptedFileBytes = encryptCipher.doFinal(fileBytes);
            try (FileOutputStream stream = new FileOutputStream(tempFile.toFile())) {
                stream.write(encryptedFileBytes);
            }
            FileUtils.forceDelete(new File("decryptedBD.txt"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void decryptBd() {
        try {
            byte[] encryptedFileBytes = Files.readAllBytes((new File("enctyptedBD.txt").toPath()));
            Cipher decryptCipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            decryptCipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivSpec);
            byte[] decryptedFileBytes = decryptCipher.doFinal(encryptedFileBytes);
            try (FileOutputStream stream = new FileOutputStream(new File("decryptedBD.txt"))) {
                stream.write(decryptedFileBytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<UserPrincipal> read() {
        try {
            String json = FileUtils.readFileToString(new File("decryptedBD.txt"));
            ObjectMapper mapper = new ObjectMapper();
            List<UserPrincipal> list = mapper.readValue(json, new TypeReference<List<UserPrincipal>>() {});

            return list;
        } catch (JsonProcessingException e) {

        } catch (IOException e) {

        }
        return Collections.emptyList();
    }
}
