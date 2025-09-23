package com.example.backend.model.Projects;

import com.example.backend.ToExport;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Generated;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.sound.midi.Patch;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static org.reflections.ReflectionUtils.Methods;

@Component
public class DataFramework {

    String pck = "com.example";
    Reflections ref = new Reflections(pck);
    Set<Class<?>> entities = ref.getTypesAnnotatedWith(ToExport.class);

    public void createCMD(String name, String path, String ext) throws IOException {
        String full = path+name+ext;
        System.out.println("Path: " + full);
        Path outputPath = Paths.get(full);
        Files.createDirectories(outputPath.getParent());
        try(BufferedWriter w = Files.newBufferedWriter(outputPath)) {
            w.write("@echo off\n");
            w.write("echo Hello\n");
            w.write("pause\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    record FieldRow(String name_field, String type){}
    record EntityRow(String name_class, List<FieldRow> fields){}

    private void JsonWriter(List<?> data) throws IOException {
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();


        mapper.writerWithDefaultPrettyPrinter().writeValue(new File("models.json"), data);
    }

        @PostConstruct
        public void findTableAnnotations() throws IOException {

            List<EntityRow> s = new ArrayList<>();
            for (Class<?> clazz : entities) {
                    List<FieldRow> rows = new ArrayList<>();
                    for (Field f : clazz.getDeclaredFields()) {
                        rows.add(new FieldRow(f.getName(), f.getType().getSimpleName()));
                    }
                    s.add(new EntityRow(clazz.getSimpleName(), rows));


            }    JsonWriter(s);
        }

        }




//@PostConstruct
//public void findTableAnnotations() throws IOException {
//
//    List<EntityRow> s = new ArrayList<>();
//    for (Class<?> clazz : entities) {
//        if (clazz.isAnnotationPresent(Table.class)) {
//            List<FieldRow> rows = new ArrayList<>();
//            for (Field f : clazz.getDeclaredFields()) {
//                rows.add(new FieldRow(f.getName(), f.getType().getSimpleName()));
//            }
//            s.add(new EntityRow(clazz.getSimpleName(), rows));
//        }
//
//    }    JsonWriter(s);
//}


//                Method[] method = clazz.getDeclaredMethods();
//
//                for (Method m : method){
//                    m.getName();
//                    m.getReturnType();
//                    m.getGenericReturnType();
//
//                }






//
//                List<FieldRow> rows = new ArrayList<>();
//                if (ann.value().equals("Controller")){
//                    for (Method f : clazz.getMethods()) {
//                        rows.add(new FieldRow(f.getName(), f.getType().getSimpleName()));
//                    }
//                    s.add(new EntityRow(clazz.getSimpleName(), rows));
//                    }
//                }
//
//
//
//
//            for (Field f : clazz.getDeclaredFields()) {
//                rows.add(new FieldRow(f.getName(), f.getType().getSimpleName()));
//                }
//                s.add(new EntityRow(clazz.getSimpleName(), rows));
//            }
//
//           JsonWriter(s);



//    private static String createTemplate(String className, Map<String, String> fields){
//        StringBuilder sb = new StringBuilder();
//        sb.append("export interface ").append(className).append(" {\n");
//        for (Map.Entry<String, String> entry : fields.entrySet()) {
//            String fieldName = entry.getKey();
//            String fieldType = entry.getValue();
//            sb.append("    ").append(fieldName).append(": ").append(fieldType).append(";\n");
//        }
//        sb.append("}\n");
//        return sb.toString();
//    }

//@PostConstruct
//public void findTableAnnotations() throws IOException {
//    List<EntityRow> s = new ArrayList<>();
//    for (Class<?> clazz : entities) {
//        ToExport ann = clazz.getAnnotation(ToExport.class);
//        if(ann.value().equals("Controller")){
//            String resourcePath = "/" + clazz.getName().replace('.', '/') + ".class";
//            String className = clazz.getName();
//            byte[] classBytes = null;
//            try (InputStream in = clazz.getResourceAsStream(
//                    "/" + clazz.getName().replace('.', '/') + ".class")) {
//                if (in != null) {
//                    classBytes = in.readAllBytes();
//                }
//                Path out = Path.of(clazz.getSimpleName() + "d.class");
//                Files.write(out, classBytes);
//                System.out.println("Saved class file to: " + out.toAbsolutePath());
//
//                URL url = clazz.getProtectionDomain()
//                        .getCodeSource()
//                        .getLocation();
//                System.out.println("Loaded from: " + url);
//
//            }
//        }
//    }
//}
//}


/*
'
    @PostConstruct
    public void findTableAnnotations() throws IOException {
        List<EntityRow> s = new ArrayList<>();
        for (Class<?> clazz : entities) {
                ToExport ann = clazz.getAnnotation(ToExport.class);
                if (ann.value().equals("controller")){
                    RequestMapping req = clazz.getAnnotation(RequestMapping.class);
                    GetMapping get = clazz.getAnnotation(GetMapping.class);
                    PostMapping post = clazz.getAnnotation(PostMapping.class);
                    PutMapping put = clazz.getAnnotation(PutMapping.class);
                    PatchMapping patch = clazz.getAnnotation(PatchMapping.class);
                    DeleteMapping delete = clazz.getAnnotation(DeleteMapping.class);

                    String className = clazz.getSimpleName();
                    String basePath = Arrays.stream(req.value()).findFirst().orElseThrow();
                    String getPaths = Arrays.stream(req.value()).findFirst().orElseThrow();
                    System.out.println(className + " " + getPaths);

                };

                }
                }
 */