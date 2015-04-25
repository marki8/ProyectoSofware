package com.ps.common;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Book {

	private String title;
	private String autor;
	private String path;
	private String editorial;
	private int precio;
	private String descripcion;

	

	private BufferedImage cover;

	public Book(String title, String autor, String path, String editorial,int precio,String descripcion) {
		
		this.title = title;
		this.autor = autor;
		this.path = path;
		this.editorial= editorial;
		this.descripcion=descripcion;
		this.precio=precio;
		try {
			cover = ImageIO.read(getClass().getResource(path));
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
	
	public String getEditorial() {
		return editorial;
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

	
}