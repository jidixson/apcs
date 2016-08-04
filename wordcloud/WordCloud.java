package hashmap;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Random;

import javax.swing.JFrame;

public class WordCloud extends JFrame {
	
	HashMap <String, Integer> frequency;
	
	public WordCloud(HashMap <String, Integer> frequencyTable) {
		super();
		frequency = frequencyTable;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setVisible(true);
	}
	
	// Override the paint method
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		g.setColor(Color.WHITE);
		Random generator = new Random();
		
		// For every key in the hashmap
		for (String key : frequency.keySet()) {
			int x = generator.nextInt(getWidth());
			int y = generator.nextInt(getHeight());
			
			// Get the word frequency
			int value = frequency.get(key);
			if (value >= 10) {
				// Create a font with that frequency value
				Font f = new Font("Courier", 0, 10 + (int) Math.sqrt(value));
				Color c = new Color(50 + generator.nextInt(206), 50 + generator.nextInt(206), 50 + generator.nextInt(206));
				
				// Before drawing, set the font to that font
				g.setColor(c);
				g.setFont(f);
				g.drawString(key, x, y);
			}
		}
		
	}
}
