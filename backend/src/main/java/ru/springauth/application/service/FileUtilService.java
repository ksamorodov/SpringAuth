package ru.springauth.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.commons.io.FileUtils;
import ru.springauth.application.dao.UserPrincipal;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class FileUtilService {
    public static void write(List<UserPrincipal> principalList) {
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(principalList);
            FileUtils.writeStringToFile(new File("db.txt"), json, "UTF-8");
        } catch (JsonProcessingException e) {

        } catch (IOException e) {

        }
    }

    public static List<UserPrincipal> read() {
        try {
            String json = FileUtils.readFileToString(new File("db.txt"));
            ObjectMapper mapper = new ObjectMapper();
            List<UserPrincipal> list = mapper.readValue(json, new TypeReference<List<UserPrincipal>>() {});

            return list;
        } catch (JsonProcessingException e) {

        } catch (IOException e) {

        }
        return Collections.emptyList();
    }
}
