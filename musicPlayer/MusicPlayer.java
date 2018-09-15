package musicPlayer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class MusicPlayer implements Runnable{
	static FileInputStream fis;
	static BufferedInputStream bis;
	static Player player;
	static File file;
	static String currentSongPath;
	static Thread currentThread;  //aktif thread 
	
	public static void start(Thread x, Runnable r) {
		try {
			x.start();  //müzik ilk kez başlatılır
			setCurrentThread(x);  //aktif thread ayarlama
		}catch(IllegalThreadStateException ex) {
			System.out.println("Thread zaten çalıştı");
		}
		if(!currentThread.isAlive()) {  //Eğer aktif thread durmuşşa if bloğu okunur
		x = new Thread(r);  //
		setCurrentThread(x);  //aktif thread ayarlama
		currentThread.start();
		
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public static void resume() {
		currentThread.resume();
	}
	
	@SuppressWarnings("deprecation")
	public static void pause() {
		currentThread.suspend();
	}
	
	@SuppressWarnings("deprecation")
	public static void stop() {
		currentThread.stop();
	}
	
	public static void setCurrentThread(Thread x) {  //Aktif olan thread i ayarlama
		currentThread = x;
	}
	
	@Override
	public void run() {
		
		try {
			file = new File(currentSongPath);
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			try {
				player = new Player(bis);
				GUI.setStateLabel(file.getName());
				player.play();
			} catch (JavaLayerException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}
}
