package com.makotomiyamoto.prxy.data;

import com.google.gson.Gson;
import com.makotomiyamoto.prxy.Client;
import com.makotomiyamoto.prxy.PrxyConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public final class Fetcher {
    public static boolean match(final String id) {
        try {
            File file = new File(Client.CONFIG_PATH);
            Gson gson = new Gson();
            PrxyConfig config = gson.fromJson(new FileReader(file), PrxyConfig.class);
            for (String user : config.IDs) {
                if (user.equals(id)) {
                    return true;
                }
            }
            return false;
        } catch (FileNotFoundException | NullPointerException e) {
            return false;
        }
    }
}
