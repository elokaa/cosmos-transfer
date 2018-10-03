package com.staarline.cosmostransfer.repositories;

import com.google.gson.Gson;
import com.microsoft.azure.documentdb.Document;
import com.microsoft.azure.documentdb.DocumentClient;
import com.microsoft.azure.documentdb.DocumentCollection;
import com.microsoft.azure.spring.data.documentdb.DocumentDbFactory;
import com.staarline.cosmostransfer.models.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountBalanceDao {

    private DocumentClient documentClient;

    private DocumentCollection accountDocumentCollection;

    private static Gson gson = new Gson();

    @Autowired
    public AccountBalanceDao(DocumentDbFactory documentDbFactory, @Qualifier(value = "accountDocumentCollection") DocumentCollection accountDocumentCollection) {
        this.documentClient = documentDbFactory.getDocumentClient();
        this.accountDocumentCollection = accountDocumentCollection;
    }

    public Account findOneByAccountNumber(String accountNumber) {
        // Retrieve the document using the DocumentClient.
        List<Document> documentList = documentClient
                .queryDocuments(accountDocumentCollection.getSelfLink(),
                        "SELECT * FROM root r WHERE r.id='" + accountNumber + "'", null)
                .getQueryIterable().toList();

        if (documentList.size() > 0) {
            return gson.fromJson(documentList.get(0).toJson(), Account.class);
        } else {
            return null;
        }
    }
}
