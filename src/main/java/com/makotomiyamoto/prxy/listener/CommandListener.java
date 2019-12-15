package com.makotomiyamoto.prxy.listener;

import com.google.gson.Gson;
import com.makotomiyamoto.prxy.Client;
import com.makotomiyamoto.prxy.PrxyConfig;
import com.makotomiyamoto.prxy.data.Fetcher;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public final class CommandListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.decode("#78b9de"));
        builder.setTitle("DrillSessionBot - A game session handler");
        if (event.getAuthor().getId().equals("410666964458012673")) {
            /*
            builder.setAuthor("DrillSessionBot");
            builder.setDescription("This is a test!");
            builder.setFooter("This is the footer.");
            event.getChannel().sendMessage(builder.build()).complete();*/

        }
        if (Fetcher.match(event.getAuthor().getId())) {
            new Thread(() -> {

                PrxyConfig config = null;
                try {
                    config = new Gson().fromJson(new FileReader(new File(Client.CONFIG_PATH)), PrxyConfig.class);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                if (config.exitQueryStatus) {
                    if (event.getMessage().getContentRaw().equals("!dsbYES")) {
                        builder.setColor(Color.red);
                        builder.setDescription("Going offline.\n`javac Client.java` to restart.");
                        Message t = event.getChannel().sendMessage(builder.build()).complete();
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        t.delete();
                        System.exit(0);
                    } else {
                        config.setExitQueryStatus(false);
                        event.getChannel().sendMessage("System exit cancelled. I live on.").complete();
                    }
                }

                if (event.getMessage().getContentRaw().toLowerCase().startsWith("!dsblock")) {
                    event.getMessage().delete().complete();
                    builder.setDescription("Assigning roles and\nlocking voice channels...");
                    Message msg = event.getChannel().sendMessage(builder.build()).complete();
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // assign roles and lock vcs
                    msg.editMessage(builder.setDescription("Finished, see you soon!").build()).complete();
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    msg.delete().complete();
                }
                if (event.getMessage().getContentRaw().toLowerCase().startsWith("!dsbexit")) {
                    builder.setDescription("Are you sure you want to turn\noff the machine?");
                    event.getChannel().sendMessage(builder.build()).complete();
                    assert config != null;
                    config.setExitQueryStatus(true);
                }
            }).start();
        }
    }
}
