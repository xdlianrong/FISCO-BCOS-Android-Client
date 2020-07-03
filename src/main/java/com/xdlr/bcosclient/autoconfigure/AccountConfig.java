/*
 * Copyright 2014-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.mytestapplication.autoconfigure;

import android.content.Context;
import android.util.Log;

import org.fisco.bcos.channel.client.PEMManager;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.ECKeyPair;
import org.fisco.bcos.web3j.crypto.gm.GenCredential;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;

public class AccountConfig {
    public static final String TAG = "AccountConfig";
    private String pemFile;
    private String p12File;
    private String password;

    static class MyPEMManager extends PEMManager {
        Context context;
        String fileName;

        public MyPEMManager(Context context, String fileName) {
            this.context = context;
            this.fileName = fileName;
        }

        public void load()
                throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException,
                NoSuchProviderException, InvalidKeySpecException {
            Log.d(TAG, "PEM manager load...");
            InputStream inputStream = null;
            try {
                inputStream = context.getResources().getAssets().open(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (inputStream == null) {
                Log.w(TAG, "PEM file error");
                return;
            }
            load(inputStream);
        }
    }

    public Credentials getCredentials(Context context, String fileName)
            throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException,
            InvalidKeySpecException, NoSuchProviderException, CertificateException,
            IOException {
        return loadPemAccount(context, fileName);
    }

    // load pem account file
    private Credentials loadPemAccount(Context context, String fileName)
            throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException,
            NoSuchProviderException, InvalidKeySpecException, UnrecoverableKeyException {
        PEMManager pem = new MyPEMManager(context, fileName);
        pem.load();
        ECKeyPair keyPair = pem.getECKeyPair();
        Credentials credentials = GenCredential.create(keyPair.getPrivateKey().toString(16));
        System.out.println(credentials.getAddress());
        return credentials;
    }

    /**
     * @return the pemFile
     */
    public String getPemFile() {
        return pemFile;
    }

    /**
     * @param pemFile the pemFile to set
     */
    public void setPemFile(String pemFile) {
        this.pemFile = pemFile;
    }

    /**
     * @return the p12File
     */
    public String getP12File() {
        return p12File;
    }

    /**
     * @param p12File the p12File to set
     */
    public void setP12File(String p12File) {
        this.p12File = p12File;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }


}
