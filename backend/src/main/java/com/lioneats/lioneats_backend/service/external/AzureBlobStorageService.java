package com.lioneats.lioneats_backend.service.external;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.lioneats.lioneats_backend.config.AzureStoragePropertiesConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.azure.storage.blob.models.BlobItem;
import java.util.ArrayList;
import java.util.List;

import java.io.InputStream;

@Service
public class AzureBlobStorageService {

    private final BlobServiceClient blobServiceClient;
    private final String containerName;

    @Autowired
    public AzureBlobStorageService(AzureStoragePropertiesConfig azureStoragePropertiesConfig) {
        this.blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(azureStoragePropertiesConfig.getConnectionString())
                .buildClient();
        this.containerName = azureStoragePropertiesConfig.getContainerName();
    }

    public String uploadFile(String fileName, InputStream fileStream, long fileSize) {
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
        BlobClient blobClient = containerClient.getBlobClient(fileName);

        blobClient.upload(fileStream, fileSize, true);
        return blobClient.getBlobUrl();
    }

    public void deleteFile(String fileName) {
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
        BlobClient blobClient = containerClient.getBlobClient(fileName);

        if (blobClient.exists()) {
            blobClient.delete();
            System.out.println("Deleted blob: " + fileName);
        } else {
            System.out.println("Blob not found: " + fileName);
        }
    }

    public void deleteAllBlobs() {
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);

        List<String> blobNames = new ArrayList<>();
        for (BlobItem blobItem : containerClient.listBlobs()) {
            blobNames.add(blobItem.getName());
        }

        for (String blobName : blobNames) {
            deleteFile(blobName);
        }

        System.out.println("All blobs have been deleted.");
    }
}
