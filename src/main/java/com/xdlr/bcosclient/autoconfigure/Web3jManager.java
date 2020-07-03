package com.example.mytestapplication.autoconfigure;

import android.content.Context;

import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.channel.handler.GroupChannelConnectionsConfig;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;

public class Web3jManager {
    Context context;

    public Web3jManager(Context context) {
        this.context = context;
    }

    public Web3j genWeb3j() {
        Web3jConfig config = new Web3jConfig();
        try {
            return config.getWeb3j(genService(context));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Credentials genCredentials() {
        AccountConfig accountConfig = new AccountConfig();
        try {
            return accountConfig.getCredentials(context, "0xcdcce60801c0a2e6bb534322c32ae528b9dec8d2.pem");
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private GroupChannelConnectionsConfig genGroupChannelConnectionsConfig(Context context) {
        GroupChannelConnectionsPropertyConfig config = new GroupChannelConnectionsPropertyConfig();

        Resource caCert = new GroupChannelConnectionsPropertyConfig.MyResource(context, "ca.crt");
        Resource sslCert = new GroupChannelConnectionsPropertyConfig.MyResource(context, "node.crt");
        Resource sslKey = new GroupChannelConnectionsPropertyConfig.MyResource(context, "node.key");

        String[] allChannelConnectionsStr = {"115.28.136.132:20200", "115.28.136.132:20201", "115.28.136.132:20202", "115.28.136.132:20203"};
        config.setAllChannelConnections(allChannelConnectionsStr, 1);
        config.setCaCert(caCert);
        config.setSslCert(sslCert);
        config.setSslKey(sslKey);
        return config.getGroupChannelConnections();
    }

    private Service genService(Context context) {
        ServiceConfig config = new ServiceConfig();
        config.setAgencyName("fisco");
        config.setGroupId(1);
        return config.getService(genGroupChannelConnectionsConfig(context));
    }
}
