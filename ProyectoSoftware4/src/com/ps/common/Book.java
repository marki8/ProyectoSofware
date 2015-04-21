package com.ps.common;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Book {

	private String title;
	private String autor;
	private String path;
	private String editorial;

	private BufferedImage cover;

	public Book(String title, String autor, String path, String Editorial) {
		
		this.title = title;
		this.autor = autor;
		this.path = path;
		this.editorial= Editorial;
		try {
			cover = ImageIO.read(getClass()
					.getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getTitle() {
		return title;
	}

	public String getAutor() {
		return autor;

	}
	
	public String getPath() {
		return path;
	}
	
	public BufferedImage getCover() {
		return cover;
	}
	
	public String getEdi() {
		return editorial;
	}
	
}