package br.com.akross.akrossapi.utils;

import jakarta.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import org.apache.commons.io.FileUtils;

public class Util {

  private static final String BASE_PATH = "src/main/resources/photos/";

  private Util() {
  }

  public static void convertBase64toImage(@NotNull String base64, @NotNull String path)
    throws IOException {

    byte[] decodedBytes = Base64.getDecoder().decode(base64);
    final String fullPath = BASE_PATH.concat(path);
    FileUtils.writeByteArrayToFile(new File(fullPath), decodedBytes);
  }

  public static String getExtension(@NotNull String contentType) {
    return ".".concat(contentType.split("/")[1]);
  }
}
