package club.archdev.archsouppvp.listeners;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.playerdata.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

public class PlayerDataListener implements Listener {

	private ArchSoupPvP plugin = ArchSoupPvP.getInstance();

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onAsyncPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
		Player player = Bukkit.getPlayer(event.getUniqueId());
		if (player != null) {
			if (player.isOnline()) {
				event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
				event.setKickMessage("§cYou tried to login too quickly after disconnecting.\n§cTry again in a few seconds.");

				this.plugin.getServer().getScheduler().runTask(this.plugin, () -> player.kickPlayer("§cDuplicate Login"));
				return;
			}

			PlayerData playerData = this.plugin.getPlayerDataManager().getPlayerData(player.getUniqueId());
			this.plugin.getPlayerDataManager().savePlayerData(playerData);
		}
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onPlayerLogin(PlayerLoginEvent event) {
		PlayerData playerData = this.plugin.getPlayerDataManager().getOrCreate(event.getPlayer().getUniqueId());
		if (playerData == null) {
			event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
			event.setKickMessage("§cAn error has occurred while loading your profile. Please reconnect.");
			return;
		}

		if (!playerData.isLoaded()) {
			this.plugin.getPlayerDataManager().savePlayerData(playerData);
			event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
			event.setKickMessage("§cAn error has occurred while loading your profile. Please reconnect.");
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		this.plugin.getPlayerDataManager().sendToSpawnAndResetPlayer(player);
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		event.setQuitMessage(null);

		Player player = event.getPlayer();

		PlayerData playerData = this.plugin.getPlayerDataManager().getPlayerData(player.getUniqueId());

		this.handleLeave(player);
		this.handleDataSave(playerData);
	}

	@EventHandler
	public void onPlayerKick(PlayerKickEvent event) {
		event.setLeaveMessage(null);

		Player player = event.getPlayer();

		PlayerData playerData = this.plugin.getPlayerDataManager().getPlayerData(player.getUniqueId());

		this.handleLeave(player);
		this.handleDataSave(playerData);
	}

	private void handleLeave(Player player) {

	}

	private void handleDataSave(PlayerData playerData) {
		if (playerData != null) {
			this.plugin.getPlayerDataManager().deletePlayer(playerData.getUniqueId());
		}
	}
}
