package ru.leadexsystems.startup.jobmonitor.common.model;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Slack {

    @SerializedName("webHook")
    @Expose
    private String webHook;
    @SerializedName("channel")
    @Expose
    private String channel;
    @SerializedName("botName")
    @Expose
    private String botName;
    @SerializedName("iconEmoji")
    @Expose
    private String iconEmoji;

    /**
     *
     * @return
     * The webHook
     */
    public String getWebHook() {
        return webHook;
    }

    /**
     *
     * @param webHook
     * The webHook
     */
    public void setWebHook(String webHook) {
        this.webHook = webHook;
    }

    /**
     *
     * @return
     * The channel
     */
    public String getChannel() {
        return channel;
    }

    /**
     *
     * @param channel
     * The channel
     */
    public void setChannel(String channel) {
        this.channel = channel;
    }

    /**
     *
     * @return
     * The botName
     */
    public String getBotName() {
        return botName;
    }

    /**
     *
     * @param botName
     * The botName
     */
    public void setBotName(String botName) {
        this.botName = botName;
    }

    /**
     *
     * @return
     * The iconEmoji
     */
    public String getIconEmoji() {
        return iconEmoji;
    }

    /**
     *
     * @param iconEmoji
     * The iconEmoji
     */
    public void setIconEmoji(String iconEmoji) {
        this.iconEmoji = iconEmoji;
    }

}