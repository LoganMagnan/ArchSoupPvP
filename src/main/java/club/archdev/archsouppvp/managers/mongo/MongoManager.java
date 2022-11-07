package club.archdev.archsouppvp.managers.mongo;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.utils.Utils;
import club.archdev.archsouppvp.utils.config.file.Config;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Collections;

@Getter
public class MongoManager {

    private final MongoManager instance;
    private final ArchSoupPvP plugin = ArchSoupPvP.getInstance();

    private final Config configFile = this.plugin.getMainConfig();
    private final FileConfiguration fileConfig = configFile.getConfig();
    private final ConfigurationSection config = fileConfig.getConfigurationSection("MONGO");

    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;

    private final String host = config.getString("HOST");
    private final int port = config.getInt("PORT");
    private final String database = config.getString("DATABASE");
    private final boolean auth = config.getBoolean("AUTH.ENABLED");
    private final String user = config.getString("AUTH.USERNAME");
    private final String password = config.getString("AUTH.PASSWORD");
    private final String authDatabase = config.getString("AUTH.AUTH-DATABASE");

    private boolean connected;

    private MongoCollection<Document> players;

    public MongoManager() {
        instance = this;
        try {
            if (auth) {
                final MongoCredential credential = MongoCredential.createCredential(user, authDatabase, password.toCharArray());
                mongoClient = new MongoClient(new ServerAddress(host, port), Collections.singletonList(credential));
            } else {
                mongoClient = new MongoClient(host, port);
            }
            connected = true;
            mongoDatabase = mongoClient.getDatabase(database);
            Bukkit.getConsoleSender().sendMessage(Utils.translate("&d[archsouppvp] &aSuccessfully connected to the database!"));
            this.players = this.mongoDatabase.getCollection("players");
        } catch (Exception exception) {
            connected = false;
            Bukkit.getConsoleSender().sendMessage(Utils.translate("&d[archsouppvp] &cFailed to connect to the database!"));
            exception.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(this.plugin);
            Bukkit.getConsoleSender().sendMessage(Utils.translate("&b[archsouppvp] &cDisabling archsouppvp..."));
        }
    }

    public void disconnect() {
        if (this.mongoClient != null) {
            this.mongoClient.close();
            this.connected = false;
            Bukkit.getConsoleSender().sendMessage(Utils.translate("&d[archsouppvp] &aSuccessfully disconnected from the database!"));
        }
    }
}
