package com.staarline.cosmostransfer.config;

import com.microsoft.azure.documentdb.*;
import com.microsoft.azure.spring.data.documentdb.config.AbstractDocumentDbConfiguration;
import com.microsoft.azure.spring.data.documentdb.repository.config.EnableDocumentDbRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@EnableDocumentDbRepositories(basePackages = {"com.staarline.cosmostransfer.repositories"})
public class CosmosDBConfiguration extends AbstractDocumentDbConfiguration {

    @Value("${azure.documentdb.uri}")
    private String uri;

    @Value("${azure.documentdb.key}")
    private String key;

    @Value("${azure.documentdb.database}")
    private String dbName;

    @Override
    public String getDatabase() {
        return dbName;
    }

    @Bean
    public Database getDatabaseObject() throws DocumentClientException {
        // Get the database if it exists
        List<Database> databaseList = documentClient()
                .queryDatabases(
                        "SELECT * FROM root r WHERE r.id='" + getDatabase()
                                + "'", null).getQueryIterable().toList();

        if (databaseList.size() > 0) {
            return databaseList.get(0);
        } else {
            // Create the database if it doesn't exist.
            Database databaseDefinition = new Database();
            databaseDefinition.setId(getDatabase());

            return documentClient().createDatabase(
                    databaseDefinition, null).getResource();
        }
    }

    @Override
    public DocumentClient documentClient() {
        return new DocumentClient(uri, key,
                ConnectionPolicy.GetDefault(), ConsistencyLevel.Session);
    }
}