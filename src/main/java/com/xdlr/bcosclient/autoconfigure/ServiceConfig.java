package com.example.mytestapplication.autoconfigure;

import android.util.Log;

import com.example.mytestapplication.constants.ConnectConstants;

import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.channel.handler.GroupChannelConnectionsConfig;

public class ServiceConfig {
    public static final String TAG = "ServiceConfig";

    private String agencyName;
    private int groupId;

    public Service getService(GroupChannelConnectionsConfig groupChannelConnectionsConfig) {
        Service channelService = new Service();
        channelService.setConnectSeconds(ConnectConstants.CONNECT_SECONDS);
        channelService.setOrgID(agencyName);
        Log.d(TAG, "agencyName : " + agencyName);
        channelService.setConnectSleepPerMillis(ConnectConstants.CONNECT_SLEEP_PER_MILLIS);
        channelService.setGroupId(groupId);
        channelService.setAllChannelConnections(groupChannelConnectionsConfig);
        return channelService;
    }

    /**
     * @return the agencyName
     */
    public String getAgencyName() {
        return agencyName;
    }

    /**
     * @param agencyName the agencyName to set
     */
    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    /**
     * @return the groupId
     */
    public int getGroupId() {
        return groupId;
    }

    /**
     * @param groupId the groupId to set
     */
    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }


}
