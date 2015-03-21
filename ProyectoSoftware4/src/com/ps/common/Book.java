package com.ps.common;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Book {

	private String titel;
	private String autor;

	private BufferedImage cover;

	public Book(String titel, String autor, String path) {
		this.titel = titel;
		this.autor = autor;
		try {
			cover = ImageIO.read(getClass()
					.getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getTitel() {
		return titel;
	}

	public String getAutor() {
		return autor;

	}

	public BufferedImage getCover() {
		return cover;
	}
}