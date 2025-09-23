package com.example.backend;

import com.example.backend.model.Projects.DataFramework;
import com.example.backend.model.Users.UserRequest;
import com.example.backend.model.Users.Users;
import com.example.backend.security.configs.KeyConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) throws NoSuchAlgorithmException, InterruptedException, IOException {
		SpringApplication.run(BackendApplication.class, args);
        DataFramework f = new DataFramework();
        f.findTableAnnotations();
	}

}
