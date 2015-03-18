package com.ps.gui;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class Map extends Component {
          
	private static final long serialVersionUID = 1L;
	BufferedImage image; // mapa con puntos de coordenadas
    BufferedImage source_image; // mapa original sin puntos coordenadas
	double minlongitude, maxlongitude, minlatitude, maxlatitude;
	
	private int scale = 3;
	
    public void paint(Graphics g) {
        g.drawImage(image, 0, 0, image.getWidth(null) / scale, image.getHeight(null) / scale, null);
        g.drawImage(image, 200, 0, image.getWidth(null) / scale, image.getHeight(null) / scale, null);
    }

	public Map(String filename) {
		try {
			image = ImageIO.read(new File(filename));
			// Hacemos una copia para cuando haya que actualizar el mapa
			source_image = deepCopy(image);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
       
    public Dimension getPreferredSize() {
        if (image == null) {
             return new Dimension(100, 100);
        } else {
           return new Dimension(image.getWidth() / scale, image.getHeight() / scale);
       }
    }
    
	static BufferedImage deepCopy(BufferedImage bi) {
 		ColorModel cm = bi.getColorModel();
 		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
 		WritableRaster raster = bi.copyData(null);
 		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
}
