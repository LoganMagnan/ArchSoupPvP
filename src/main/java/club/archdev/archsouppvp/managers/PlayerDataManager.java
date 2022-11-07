package club.archdev.archsouppvp.managers;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.managers.hotbar.impl.HotbarItem;
import club.archdev.archsouppvp.playerdata.PlayerData;
import club.archdev.archsouppvp.playerdata.PlayerState;
import club.archdev.archsouppvp.utils.PlayerUtil;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import lombok.Getter;
import org.bson.Document;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class PlayerDataManager {

	private ArchSoupPvP plugin = ArchSoupPvP.getInstance();

	@Getter private Map<UUID, PlayerData> players = new HashMap<>();

	public PlayerData getOrCreate(UUID uniqueId) {
		return this.players.computeIfAbsent(uniqueId, PlayerData::new);
	}

	public PlayerData getPlayerData(UUID uniqueId) {
		return this.players.getOrDefault(uniqueId, new PlayerData(uniqueId));
	}

	public Collection<PlayerData> getAllPlayers() {
		return this.players.values();
	}

	public void loadPlayerData(PlayerData playerData) {
		Document document = this.plugin.getMongoManager().getPlayers().find(Filters.eq("uniqueId", playerData.getUniqueId().toString())).first();

		playerData.setPlayerState(PlayerState.SPAWN);

		if (document == null) {
			this.savePlayerData(playerData);

			playerData.setLoaded(true);

			return;
		}

		playerData.setXp(document.getDouble("xp"));
		playerData.setLevel(document.getInteger("level"));
		playerData.setCurrentKitName(document.getString("currentKitName"));
		playerData.setUnlockedKits((List<String>) document.get("unlockedKits"));
		playerData.setKills(document.getInteger("kills"));
		playerData.setDeaths(document.getInteger("deaths"));
		playerData.setBounty(document.getInteger("bounty"));
		playerData.setCoins(document.getInteger("coins"));
		playerData.setCurrentKillstreak(document.getInteger("currentKillstreak"));
		playerData.setHighestKillstreak(document.getInteger("highestKillstreak"));
		playerData.setLoaded(true);
	}

	public void savePlayerData(PlayerData playerData) {
		Document document = new Document();

		document.put("uniqueId", playerData.getUniqueId().toString());
		document.put("xp", playerData.getXp());
		document.put("level", playerData.getLevel());
		document.put("currentKitName", playerData.getCurrentKitName());
		document.put("unlockedKits", playerData.getUnlockedKits());
		document.put("kills", playerData.getKills());
		document.put("deaths", playerData.getDeaths());
		document.put("bounty", playerData.getBounty());
		document.put("coins", playerData.getCoins());
		document.put("currentKillstreak", playerData.getCurrentKillstreak());
		document.put("highestKillstreak", playerData.getHighestKillstreak());

		this.plugin.getMongoManager().getPlayers().replaceOne(Filters.eq("uniqueId", playerData.getUniqueId().toString()), document, new UpdateOptions().upsert(true));
	}

	public void deletePlayer(UUID uniqueId) {
		this.plugin.getServer().getScheduler().runTaskAsynchronously(this.plugin, () -> {
			this.savePlayerData(this.players.get(uniqueId));
			this.players.remove(uniqueId);
		});
	}

	public void sendToSpawnAndResetPlayer(Player player) {
		PlayerData playerData = this.getPlayerData(player.getUniqueId());
		playerData.setPlayerState(PlayerState.SPAWN);

		PlayerUtil.clearPlayer(player);

		this.giveSpawnItems(player);

		if (!player.isOnline()) {
			return;
		}

		this.updatePlayerView();

		if (this.plugin.getSpawnManager().getSpawnLocation() != null) {
			player.teleport(this.plugin.getSpawnManager().getSpawnLocation().toBukkitLocation());
		}
	}

	public void resetPlayer(Player player) {
		PlayerData playerData = this.getPlayerData(player.getUniqueId());
		playerData.setPlayerState(PlayerState.SPAWN);

		PlayerUtil.clearPlayer(player);

		this.giveSpawnItems(player);

		if (!player.isOnline()) {
			return;
		}

		this.updatePlayerView();
	}

	public void giveSpawnItems(Player player) {
		this.plugin.getHotbarManager().getSpawnItems().stream().filter(HotbarItem::isEnabled).forEach(item -> player.getInventory().setItem(item.getSlot(), item.getItemStack()));

		player.updateInventory();
	}

	public void updatePlayerView() {
		new BukkitRunnable() {
			public void run() {
				PlayerDataManager.this.plugin.getServer().getOnlinePlayers().forEach(player -> {
					PlayerData playerData = PlayerDataManager.this.plugin.getPlayerDataManager().getPlayerData(player.getUniqueId());

					if (playerData.isInSpawn()) {
						if (playerData.getPlayerSettings().isPlayerVisibilityEnabled()) {
							PlayerDataManager.this.plugin.getServer().getOnlinePlayers().forEach(playerOnline -> player.showPlayer(playerOnline));
						} else {
							PlayerDataManager.this.plugin.getServer().getOnlinePlayers().forEach(playerOnline -> player.hidePlayer(playerOnline));
						}
					}
				});
			}
		}.runTaskAsynchronously(this.plugin);
	}

	public MongoCursor<Document> getPlayersSorted(String stat, int limit) {
		Document document = new Document();
		document.put(stat, -1);

		return this.plugin.getMongoManager().getPlayers().find().sort(document).limit(limit).iterator();
	}
}
