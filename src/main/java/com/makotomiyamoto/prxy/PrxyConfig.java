package com.makotomiyamoto.prxy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class PrxyConfig {
    public String token;
    public String[] IDs;
    public boolean exitQueryStatus;
    public PrxyConfig() {
    }
    public void setExitQueryStatus(boolean status) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            File file = new File(Client.CONFIG_PATH);
            PrxyConfig local = new Gson().fromJson(new FileReader(file), this.getClass());
            local.exitQueryStatus = status;
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(gson.toJson(local));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
