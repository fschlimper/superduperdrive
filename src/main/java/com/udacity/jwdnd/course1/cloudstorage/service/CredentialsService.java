package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialsService {

    private final CredentialsMapper credentialsMapper;
    private final EncryptionService encryptionService;

    public CredentialsService(CredentialsMapper credentialsMapper, EncryptionService encryptionService) {
        this.credentialsMapper = credentialsMapper;
        this.encryptionService = encryptionService;
    }

    public String addOrUpdateCredentials(Credentials credentials) {

        String errMsg = null;

        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[16];
        secureRandom.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credentials.getClearPassword(), encodedKey);

        credentials.setKey(encodedKey);
        credentials.setPassword(encryptedPassword);

        if (credentials.getCredentialid() == null) {
            if (credentialsMapper.getCredentialsByUrl(credentials.getUserid(), credentials.getUrl()) != null) {
                errMsg = "Credentials for this webpage already exists.";
            } else {
                credentialsMapper.insertCredentials(credentials);
            }
        } else {
            credentialsMapper.updateCredentials(credentials);
        }
        return errMsg;
    }

    public List<Credentials> getCredentialsByUserId(Integer userId) {
        List<Credentials> credentialsList = credentialsMapper.getCredentialsByUserId(userId);
        credentialsList.forEach(cr ->
                cr.setClearPassword(encryptionService.decryptValue(cr.getPassword(), cr.getKey())));
        return credentialsList;
    }

    public Credentials getCredentialsByUrl(Integer userId, String url) {
        Credentials credentials = credentialsMapper.getCredentialsByUrl(userId, url);
        if (credentials != null) {
            credentials.setClearPassword(encryptionService.decryptValue(credentials.getPassword(), credentials.getKey()));
        }
        return credentials;
    }

    public String deleteCredential(Integer credentialId) {
        credentialsMapper.deleteCredential(credentialId);
        return null;
    }
}
