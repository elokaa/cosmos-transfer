package com.staarline.cosmostransfer.config;

import com.microsoft.azure.documentdb.Database;
import com.microsoft.azure.documentdb.DocumentClient;
import com.microsoft.azure.documentdb.DocumentClientException;
import com.microsoft.azure.documentdb.DocumentCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.List;

@Configuration
@Import(CosmosDBConfiguration.class)
public class AccountBalanceCollectionConfiguration {

    private final DocumentClient documentClient;

    private final Database database;

    @Autowired
    public AccountBalanceCollectionConfiguration(DocumentClient documentClient, Database database) {
        this.documentClient = documentClient;
        this.database = database;
    }

    @Bean(name = "accountDocumentCollection")
    public DocumentCollection getAccountCollection() throws DocumentClientException {
        // Get the collection if it exists.
        List<DocumentCollection> collectionList = documentClient
                .queryCollections(
                        database.getSelfLink(),
                        "SELECT * FROM root r WHERE r.id='" + "balances"
                                + "'", null).getQueryIterable().toList();

        if (collectionList.size() > 0) {
            // Cache the collection object so we won't have to query for it
            // later to retrieve the selfLink.
            return collectionList.get(0);
        } else {
            // Create the collection if it doesn't exist.
            DocumentCollection collectionDefinition = new DocumentCollection();
            collectionDefinition.setId("balances");

            return documentClient.createCollection(
                    database.getSelfLink(),
                    collectionDefinition, null).getResource();
        }
    }
}
