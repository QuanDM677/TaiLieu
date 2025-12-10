package com.fashion.site.storage;

import com.fashion.site.model.User;
import com.fashion.site.model.Document;
import java.util.*;

public class MemoryStorage {
    private static MemoryStorage instance = new MemoryStorage();
    private Map<String, User> users = new HashMap<>();
    private List<Document> documents = new ArrayList<>();

    private MemoryStorage() {}
    public static MemoryStorage getInstance() { return instance; }

    public Map<String, User> getUsers() { return users; }
    public List<Document> getDocuments() { return documents; }

    // Add user
    public void addUser(User user) { users.put(user.getUsername(), user); }

    // Add document
    public void addDocument(Document doc) { documents.add(doc); }

    // Find user
    public User findUser(String username) { return users.get(username); }
}
