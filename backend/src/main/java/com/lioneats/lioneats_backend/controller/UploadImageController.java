package com.lioneats.lioneats_backend.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lioneats.lioneats_backend.service.external.AzureBlobStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

@RestController
@RequestMapping("/api/upload")
public class UploadImageController {

    private final AzureBlobStorageService azureBlobStorageService;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String mlModelApiUrl = "https://lioneatsml-hfanfbfxgvdzduh7.southeastasia-01.azurewebsites.net/predict";

    @Autowired
    public UploadImageController(AzureBlobStorageService azureBlobStorageService) {
        this.azureBlobStorageService = azureBlobStorageService;
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            // Step 3: Convert MultipartFile to InputStream and upload to Azure Blob Storage
            InputStream inputStream = file.getInputStream();
            String fileName = file.getOriginalFilename();
            String imageUrl = azureBlobStorageService.uploadFile(fileName, inputStream, file.getSize());

            // Step 4: Call the ML model API with the MultipartFile
            String mlModelPrediction = getMlModelPrediction(file);

            // Step 6: Parse the prediction JSON to find the dish with the highest probability
            String predictedDish = getDishWithHighestProbability(mlModelPrediction);

            // Return the image URL and predicted dish name to the frontend
            return ResponseEntity.ok(Map.of("imageUrl", imageUrl, "predictedDish", predictedDish));

        } catch (IOException e) {
            return ResponseEntity.status(500).body(Map.of("error", "Image upload failed: " + e.getMessage()));
        }
    }

    // Step 4: Call the ML model API
    private String getMlModelPrediction(MultipartFile file) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", file.getResource());

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // Send the request to the ML model API and get the response
        ResponseEntity<String> response = restTemplate.postForEntity(mlModelApiUrl, requestEntity, String.class);

        // Return the response body (prediction JSON)
        return response.getBody();
    }

    // Step 6: Parse the prediction JSON to find the dish with the highest probability
    private String getDishWithHighestProbability(String mlModelPrediction) throws IOException {
        // Parse the JSON response
        JsonNode rootNode = objectMapper.readTree(mlModelPrediction);
        JsonNode predictionNode = rootNode.path("prediction");

        String topDish = null;
        double highestProbability = -1;

        Iterator<Map.Entry<String, JsonNode>> fields = predictionNode.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            double probability = entry.getValue().asDouble();
            if (probability > highestProbability) {
                highestProbability = probability;
                topDish = entry.getKey();
            }
        }

        return topDish;
    }
}
