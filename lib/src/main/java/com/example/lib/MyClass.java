package com.example.lib;

import java.io.FileWriter;
import java.io.IOException;

public class MyClass {
    public static void main(String[] args) {
        String path = "TestFile" + ".txt";
        try (FileWriter w = new FileWriter(path)) {
            w.write("hello file!");
            System.out.println("Created: " + path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
