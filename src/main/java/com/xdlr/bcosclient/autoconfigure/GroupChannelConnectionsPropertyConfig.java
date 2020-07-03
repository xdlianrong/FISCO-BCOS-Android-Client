package com.example.mytestapplication.autoconfigure;

import android.content.Context;
import android.util.Log;

import org.fisco.bcos.channel.handler.ChannelConnections;
import org.fisco.bcos.channel.handler.GroupChannelConnectionsConfig;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GroupChannelConnectionsPropertyConfig {
    public static final String TAG = "AccountConfig";

    List<ChannelConnections> allChannelConnections = new ArrayList<>();
    private Resource caCert;
    private Resource sslCert;
    private Resource sslKey;

    public static class MyResource extends ClassPathResource {
        private Context context;
        private String fileName;

        public MyResource(Context context, String fileName) {
            super("");
            this.context = context;
            this.fileName = fileName;
        }

        @Override
        public InputStream getInputStream() throws IOException {
            Log.d(TAG, "Call my resource getInputStream");
            InputStream inputStream = null;
            try {
                inputStream = context.getResources().getAssets().open(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return inputStream;
        }

        @Override
        public boolean exists() {
            return true;
        }
    }

    public GroupChannelConnectionsConfig getGroupChannelConnections() {
        GroupChannelConnectionsConfig groupChannelConnectionsConfig =
                new GroupChannelConnectionsConfig();
        groupChannelConnectionsConfig.setCaCert(caCert);
        groupChannelConnectionsConfig.setSslCert(sslCert);
        groupChannelConnectionsConfig.setSslKey(sslKey);
        groupChannelConnectionsConfig.setAllChannelConnections(allChannelConnections);
        return groupChannelConnectionsConfig;
    }

    /**
     * @return the caCert
     */
    public Resource getCaCert() {
        return caCert;
    }

    /**
     * @param caCert the caCert to set
     */
    public void setCaCert(Resource caCert) {
        this.caCert = caCert;
    }

    /**
     * @return the sslCert
     */
    public Resource getSslCert() {
        return sslCert;
    }

    /**
     * @param sslCert the sslCert to set
     */
    public void setSslCert(Resource sslCert) {
        this.sslCert = sslCert;
    }

    /**
     * @return the sslKey
     */
    public Resource getSslKey() {
        return sslKey;
    }

    /**
     * @param sslKey the sslKey to set
     */
    public void setSslKey(Resource sslKey) {
        this.sslKey = sslKey;
    }

    /**
     * @return the allChannelConnections
     */
    public List<ChannelConnections> getAllChannelConnections() {
        return allChannelConnections;
    }

    public void setAllChannelConnections(String[] allChannelConnectionsStr, int groupID) {
//        现在只能从properties里面分别读取allChannelConnections和channelGroupID，且只能读一组
//        正式开发时可以考虑使用res下面的values目录配置xml文件，实现复杂结构的读取，省得这里手写解析
        List<ChannelConnections> allChannelConnections = new ArrayList<>();
        List<String> allChannelConnectionsList;
        ChannelConnections channelConnections = new ChannelConnections();

        allChannelConnectionsList = Arrays.asList(allChannelConnectionsStr);
        channelConnections.setConnectionsStr(allChannelConnectionsList);
        channelConnections.setGroupId(groupID);
        allChannelConnections.add(channelConnections);
        this.allChannelConnections = allChannelConnections;
    }

}
