package com.ps.common;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Book {

	private int id;
	private String title;
	private String autor;
	private String path;

	private BufferedImage cover;

	public Book(int id,String title, String autor, String path) {
		
		this.id=id;
		this.title = title;
		this.autor = autor;
		this.path = path;
		try {
			cover = ImageIO.read(getClass()
					.getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getId() {
		return id;
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
}