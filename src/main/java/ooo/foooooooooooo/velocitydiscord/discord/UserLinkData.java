package ooo.foooooooooooo.velocitydiscord.discord;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserLinkData {
  private static final ObjectMapper objectMapper = new ObjectMapper()
    .enable(SerializationFeature.INDENT_OUTPUT);

  private static final File linkFile = new File("plugins/discord/LinkedUsers.json");

  private static Map<String, String> links = new HashMap<>();

  public static void load() throws IOException {
    if (!linkFile.exists()) {
      links = new HashMap<>();
      return;
    }

    links = objectMapper.readValue(linkFile, objectMapper.getTypeFactory().constructMapType(HashMap.class, String.class, String.class));
  }

  public static void saveAll() throws IOException {
    objectMapper.writeValue(linkFile, links);
  }

  public static void save(String discordUserID, String minecraftUserName) throws IOException {
    links.put(discordUserID, minecraftUserName);
    saveAll();
  }

  public static String minecraftUserName(String discordUserUUID) {
    return links.get(discordUserUUID);
  }

  public static String getDiscordUserID(String minecraftUserName) {
    return links.entrySet().stream()
      .filter(e -> e.getValue().equals(minecraftUserName))
      .map(Map.Entry::getKey)
      .findFirst()
      .orElse(null);
  }
}
