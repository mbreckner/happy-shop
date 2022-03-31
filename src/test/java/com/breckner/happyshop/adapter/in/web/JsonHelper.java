package com.breckner.happyshop.adapter.in.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonHelper {

    static String readAsJsonString(String fileName) {
        try {
            return new String(Files.readAllBytes(Paths.get("src/test/resources/adapter/web/" + fileName)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
