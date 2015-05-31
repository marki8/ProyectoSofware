package databaseTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import databaseTests.CajaNegra.AñadirLibro;
import databaseTests.CajaNegra.AñadirUsuario;
import databaseTests.CajaNegra.BorrarLibro;
import databaseTests.CajaNegra.ModificarLibro;
import databaseTests.CajaNegra.ModificarUsuario;


@RunWith(Suite.class)
@SuiteClasses({AñadirLibro.class,AñadirUsuario.class,BorrarLibro.class,ModificarLibro.class,ModificarUsuario.class})
public class PruebasCajaNegra {}
