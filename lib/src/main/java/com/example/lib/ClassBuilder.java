package com.example.lib;

import java.io.FileWriter;
import java.io.IOException;

public class ClassBuilder {

    public static void fileWriter(String name, String type) {
        String path = name + type;
        try (FileWriter w = new FileWriter(path)) {
            w.write("hello file!");
            System.out.println("Created: " + path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}