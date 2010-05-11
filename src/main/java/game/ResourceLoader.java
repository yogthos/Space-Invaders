package game;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class ResourceLoader implements ImageObserver {

	
	private Map<String,BufferedImage> images = new HashMap<String,BufferedImage>();
	private Map<String,AudioClip> sounds = new HashMap<String,AudioClip>();

	private static ResourceLoader instance = new ResourceLoader();
	

	private ResourceLoader() {}
	
	public static ResourceLoader getInstance() {
		return instance;
	}
	
	public void cleanup() {
		for (AudioClip sound : sounds.values()) {
			sound.stop();
		}
		
	}
	public AudioClip getSound(String name) {
		AudioClip sound = sounds.get(name);
		if (null != sound)
			return sound;
		
		URL url = null;
		try {
			url = getClass().getClassLoader().getResource("res/" + name);
			sound = Applet.newAudioClip(url);
			sounds.put(name,sound);			
		} catch (Exception e) {
			System.err.println("Cound not locate sound " + name + ": " + e.getMessage());			
		}		
		
		return sound;
	}
	
	/**
	 * creates a compatible image in memory, faster than using the original image format
	 * @param width image width
	 * @param height image height
	 * @param transparency type of transparency
	 * @return a compatible BufferedImage
	 */
	public static BufferedImage createCompatible(int width, int height,
			int transparency) {
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		BufferedImage compatible = gc.createCompatibleImage(width, height,
				transparency);
		return compatible;		
	}

	/**
	 * check if image is cached, if not, load it
	 * @param name
	 * @return
	 */
	public BufferedImage getSprite(String name) {
		BufferedImage image = images.get(name);
		if (null != image)
			return image;
		
		URL url = null;
		try {
			url = getClass().getClassLoader().getResource("res/" + name);
			image = ImageIO.read(url);
			//store a compatible image instead of the original format
			BufferedImage compatible = createCompatible(image.getWidth(), image.getHeight(), Transparency.BITMASK);
			compatible.getGraphics().drawImage(image, 0,0,this);

			images.put(name,compatible);			
		} catch (Exception e) {
			System.err.println("Cound not locate image " + name + ": " + e.getMessage());			
		}		
		
		return image;
	}
	
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		return (infoflags & (ALLBITS|ABORT)) == 0;
	}

}
