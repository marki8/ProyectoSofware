package com.ps.common;

import com.ps.db.DbConnector;

public class Test {

	private static DbConnector db;

	public static void main(String args) {
		try {
			db = new DbConnector("db_file");
		} catch (Exception e) {
			e.printStackTrace();
		}
		prueba_caja_negra();
		prueba_volumen();
		prueba_sobrecarga();

	}

	/**
	 * PRUEBA UNITARIAS DE CAJA NEGRA
	 */
	private static void prueba_caja_negra() {

		/*
		 * Pruebas Primera Iteraci�n
		 */


		/*
		 * Pruebas Segunda Iteraci�n
		 */
		// RF-4: Buscar los libros por autor, titulo, editorial o puntuaci�n
		// Caso valido b�squeda por autor
		System.out.println(db.getBookAutor(""));
		// Casos no validos b�squeda por autor
		System.err.println(db.getBookAutor(""));
		
		//RF-5: Listar libros comprados

	}

	/**
	 * PRUEBAS DE VOLUMEN
	 */
	private static void prueba_volumen() {

		/*
		 * Pruebas Primera Iteraci�n
		 */

		/*
		 * Pruebas Segunda Iteraci�n
		 */
	}

	/**
	 * PRUEBAS DE SOBRECARGA
	 */
	private static void prueba_sobrecarga() {

		/*
		 * Pruebas Primera Iteraci�n
		 */

		/*
		 * Pruebas Segunda Iteraci�n
		 */

	}
}
