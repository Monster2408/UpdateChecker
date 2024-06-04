package xyz.mlserver.updatechecker;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public final class SpigotUpdateChecker {

    private final JavaPlugin plugin;
    private URL checkURL;

    private String currentVersion;
    private String availableVersion;

    private String freeDownloadLink = null;
    private String plusDownloadLink = null;
    private boolean sendOpMessage = false;
    private String donationLink = null;

    private UpdateResult result = UpdateResult.FAIL_SPIGOT;

    public enum UpdateResult {
        NO_UPDATE,
        FAIL_SPIGOT,
        UPDATE_AVAILABLE
    }

    /**
     * spigot update checker
     * @param plugin JavaPlugin
     * @param resourceId spigot resource id
     */
    public SpigotUpdateChecker(JavaPlugin plugin, Integer resourceId) {
        this.plugin = plugin;
        this.currentVersion = regex(this.plugin.getDescription().getVersion());

        try {
            this.checkURL = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + resourceId);
        } catch (MalformedURLException e) {
            result = UpdateResult.FAIL_SPIGOT;
            return;
        }

        run();
    }

    private void run() {
        URLConnection con;
        try {
            con = checkURL.openConnection();
        } catch (IOException e1) {
            result = UpdateResult.FAIL_SPIGOT;
            return;
        }

        try {
            availableVersion = regex(new BufferedReader(new InputStreamReader(con.getInputStream())).readLine());
        } catch (IOException e) {
            result = UpdateResult.FAIL_SPIGOT;
            return;
        }

        if (availableVersion.isEmpty()) {
            result = UpdateResult.FAIL_SPIGOT;
            return;
        } else if (availableVersion.equalsIgnoreCase(currentVersion)) {
            result = UpdateResult.NO_UPDATE;
            return;
        } else if (!availableVersion.equalsIgnoreCase(currentVersion)) {
            result = UpdateResult.UPDATE_AVAILABLE;
            return;
        }

        result = UpdateResult.FAIL_SPIGOT;

    }

    /**
     * get update result
     * @return UpdateResult
     */
    public UpdateResult getResult() {
        return this.result;
    }

    /**
     * get version
     * @return version
     */
    public String getVersion() {
        return regex(this.availableVersion);
    }

    private String regex(String version) {
        return version.replaceAll("[^0-9.]", "");
    }

    /**
     * get free download link
     * @return free download link
     */
    public String getFreeDownloadLink() {
        return freeDownloadLink;
    }

    /**
     * get premium download link
     * @return premium download link
     */
    public String getPlusDownloadLink() {
        return plusDownloadLink;
    }

    /**
     * get available version
     * @return available version
     */
    public String getAvailableVersion() {
        return availableVersion;
    }

    /**
     * get current version
     * @return current version
     */
    public String getCurrentVersion() {
        return currentVersion;
    }

    /**
     * get plugin
     * @return JavaPlugin
     */
    public JavaPlugin getPlugin() {
        return plugin;
    }

    /**
     * is send op message
     * @return boolean
     */
    public boolean isSendOpMessage() {
        return sendOpMessage;
    }

    /**
     * get donation link
     * @return donation link
     */
    public String getDonationLink() {
        return donationLink;
    }

    /**
     * set free download link
     * @param freeDownloadLink free download link
     * @return SpigotUpdateChecker
     */
    public SpigotUpdateChecker setFreeDownloadLink(String freeDownloadLink) {
        this.freeDownloadLink = freeDownloadLink;
        return this;
    }

    /**
     * set premium download link
     * @param plusDownloadLink premium download link
     * @return SpigotUpdateChecker
     */
    public SpigotUpdateChecker setPlusDownloadLink(String plusDownloadLink) {
        this.plusDownloadLink = plusDownloadLink;
        return this;
    }

    /**
     * set send op message
     * @param sendOpMessage send op message
     * @return SpigotUpdateChecker
     */
    public SpigotUpdateChecker setSendOpMessage(boolean sendOpMessage) {
        this.sendOpMessage = sendOpMessage;
        return this;
    }

    /**
     * set donation link
     * @param donationLink donation link
     * @return SpigotUpdateChecker
     */
    public SpigotUpdateChecker setDonationLink(String donationLink) {
        this.donationLink = donationLink;
        return this;
    }

}
