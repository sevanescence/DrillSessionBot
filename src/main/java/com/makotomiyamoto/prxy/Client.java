package com.makotomiyamoto.prxy;

import com.google.gson.Gson;
import com.makotomiyamoto.prxy.listener.CommandListener;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public final class Client {
    private static String s = File.separator;
    public static String CONFIG_PATH = "C:" + s + "Users" + s + "TestOS" + s
            + "Desktop" + s + "MACL" + s + "settings.json";
    public static void main(String[] args) throws LoginException {
        try {
            JDABuilder client = new JDABuilder(AccountType.BOT);
            client.setToken(new Gson().fromJson(new FileReader(new File(CONFIG_PATH)), PrxyConfig.class).token);
            client.addEventListeners(new CommandListener());
            client.build();
        } catch (FileNotFoundException e) {
            return;
        }
    }
}
