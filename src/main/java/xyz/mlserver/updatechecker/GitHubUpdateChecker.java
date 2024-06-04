package xyz.mlserver.updatechecker;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public final class GitHubUpdateChecker {

    private final JavaPlugin plugin;
    private URL checkURL;

    private String currentVersion;
    private String availableVersion;

    private String freeDownloadLink = null;
    private String plusDownloadLink = null;
    private boolean sendOpMessage = false;
    private String donationLink = null;

    private UpdateResult result = UpdateResult.FAIL_GITHUB;

    public enum UpdateResult {
        NO_UPDATE,
        FAIL_GITHUB,
        UPDATE_AVAILABLE
    }

    /**
     * update checker for github plugins
     * @param plugin JavaPlugin
     * @param author github author name
     * @param repository github repository name
     */
    public GitHubUpdateChecker(JavaPlugin plugin, String author, String repository) {
        this.plugin = plugin;
        this.currentVersion = regex(this.plugin.getDescription().getVersion());
        try {
            this.checkURL = new URL("https://github.com/" + author + "/" + repository + "/releases/latest");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        run();
    }

    private void run() {
        try {
            availableVersion = getJsonData(checkURL).get("tag_name").asText();
        } catch (IOException e) {
            result = UpdateResult.FAIL_GITHUB;
            return;
        }

        if (availableVersion.isEmpty()) {
            result = UpdateResult.FAIL_GITHUB;
            return;
        } else if (availableVersion.equalsIgnoreCase(currentVersion)) {
            result = UpdateResult.NO_UPDATE;
            return;
        } else if (!availableVersion.equalsIgnoreCase(currentVersion)) {
            result = UpdateResult.UPDATE_AVAILABLE;
            return;
        }
        result = UpdateResult.FAIL_GITHUB;
    }

    private static JsonNode getJsonData(URL url) throws IOException {
        String result = "";
        JsonNode root = null;

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.connect(); // URL接続
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String tmp = "";

        while ((tmp = in.readLine()) != null) {
            result += tmp;
        }

        ObjectMapper mapper = new ObjectMapper();
        root = mapper.readTree(result);
        in.close();
        con.disconnect();

        return root;
    }

    /**
     * update result
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
     * @return plus download link
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
     * send op message flag
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
     * @return GitHubUpdateChecker
     */
    public GitHubUpdateChecker setFreeDownloadLink(String freeDownloadLink) {
        this.freeDownloadLink = freeDownloadLink;
        return this;
    }

    /**
     * set premium download link
     * @param plusDownloadLink plus download link
     * @return GitHubUpdateChecker
     */
    public GitHubUpdateChecker setPlusDownloadLink(String plusDownloadLink) {
        this.plusDownloadLink = plusDownloadLink;
        return this;
    }

    /**
     * set send op message flag
     * @param sendOpMessage send op message flag
     * @return GitHubUpdateChecker
     */
    public GitHubUpdateChecker setSendOpMessage(boolean sendOpMessage) {
        this.sendOpMessage = sendOpMessage;
        return this;
    }

    /**
     * set donation link
     * @param donationLink donation link
     * @return GitHubUpdateChecker
     */
    public GitHubUpdateChecker setDonationLink(String donationLink) {
        this.donationLink = donationLink;
        return this;
    }

}
