package databaseTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import databaseTests.CajaNegra.ModificarUsuario;
import databaseTests.Volumen.AñadirLibros;
import databaseTests.Volumen.AñadirUsuarios;
import databaseTests.Volumen.ModificarLibros;



@RunWith(Suite.class)
@SuiteClasses({AñadirUsuarios.class,AñadirLibros.class,ModificarLibros.class,ModificarUsuario.class})
public class PruebasVolumen {}
