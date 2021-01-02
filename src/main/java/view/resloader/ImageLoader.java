package view.resloader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public final class ImageLoader {

    private ImageLoader() {
        throw new AssertionError("Must not instantiate an element of ImageLoader class!");
    }

    public static BufferedImage readImage(String path) throws IOException {
        try (final InputStream inputStream = ImageLoader.class.getResourceAsStream(String.valueOf(path))) {
            final BufferedImage image = ImageIO.read(inputStream);
            if (image == null) {
                throw new IOException("No registered ImageReader claims to be able to read the resulting stream." +
                        "(ImageIO.read(...) returns null)");
            }
            return image;
        }
    }
    public static ArrayList<BufferedImage> readAnimation(File dir) throws IOException {
        ArrayList<BufferedImage> images = new ArrayList<>();
        for(File imgFile : dir.listFiles()) {
            BufferedImage image = ImageLoader.readImage(imgFile.getPath().substring(18).replaceAll("\\\\","/"));
            images.add(image);
        }
        return images;
    }
}