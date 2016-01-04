package com.netgames.clashoffishes.server;

import com.netgames.clashoffishes.server.remote.IMessage;

/**
 * Implementation of an IMessage.
 *
 * @author Hein Dauven
 */
public class Message implements IMessage {

    private String text;
    private String username;

    public Message(String username, String text) {
        this.username = username;
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return this.username + ": " + this.text;
    }
}
