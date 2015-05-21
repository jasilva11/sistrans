package test;

import java.sql.SQLException;
import java.util.ArrayList;

import Prodandes.dao.consultaDAO;
import Prodandes.vod.Cliente;
import junit.framework.TestCase;

public class Test extends TestCase
{

	consultaDAO x = null;

	public void setupEscenario1()
	{
		x = new consultaDAO();
		x.inicializar();
		
	}


	/*
	 * Se agrega un cliente que no es factible por que no cumple con la condicion de la PK de usuarios
	 * 	
	 */
	public void testAgregarUsuarioRepetidoLogin()
	{
		setupEscenario1();
		String asd = ("INSERT INTO USUARIOS (ROL_EN_SISTEMA, DIRECCIONELECTRONICA,LOGIN,PALABRACLAVE,IDENTIFICACION) VALUES('OPERARIO','ASDASD','VIRGEN','PAILA', 4792613001 )");
		String bien = "";
		try
		{
			x.agREGAR(asd);
		}
		catch (Exception e)
		{
			bien = e.getMessage();
			// TODO: handle exception
		}
		assert ( "ORA-00001: unique constraint (ISIS2304091510.USUARIOS_PK) violated".equals(	bien) );

	}
	public void testAgregarUsuarioSinFK()
	{
		setupEscenario1();
		String asd = ("INSERT INTO USUARIOS (ROL_EN_SISTEMA, DIRECCIONELECTRONICA,LOGIN,PALABRACLAVE,IDENTIFICACION) VALUES('OPERARIO','Sabogay@','Felipe','PAILA', 1230000 )");
		String bien = "";
		try
		{
			x.agREGAR(asd);
		}
		catch (Exception e)
		{
			bien = e.getMessage();
			// TODO: handle exception
		}
		assertTrue(!"ORA-02291: integrity constraint (ISIS2304091510.FK_USUARIOIDENTIFICACION) violated - parent key not found".equals(	bien) );

	}
	public void testAgregarUsuarioLoginRepetido()
	{
		setupEscenario1();

		String asd = ("INSERT INTO USUARIOS(ROL_EN_SISTEMA, DIRECCIONELECTRONICA, LOGIN, PALABRACLAVE, IDENTIFICACION) VALUES ('OPERARIO', 'ASDAS@ASD', 'JESUS', 'piala', '123123123');");
		String bien = "";
		try
		{
			x.agREGAR(asd);
		}
		catch (Exception e)
		{
			bien = e.getMessage();
			System.out.print(bien);
			assertNotNull(bien);
			// TODO: handle exception
		}
	}

	public void testAgregarEmpresaRepetida()
	{
		setupEscenario1();

		String papitas =("INSERT INTO EMPRESA (ID, INGRESOS_MENSUALES, INGRESOS_ANUALES, DEUDAS_) VALUES ('12333', '20123', '1010', '123')");
		// TODO: handle exception
		try
		{
			x.agREGAR(papitas);
		}
		catch (Exception e)
		{
			String bien = e.getMessage();
			assertNotNull(bien);


		}
	}

	public void testAgregarEmpresaCK()
	{
		setupEscenario1();

		String papitas =("INSERT INTO EMPRESA (ID, INGRESOS_MENSUALES, INGRESOS_ANUALES, DEUDAS_) VALUES (1233, -20123, 1010, 123)");
		// TODO: handle exception
		try
		{
			x.agREGAR(papitas);
		}
		catch (Exception e)
		{
			String bien = e.getMessage();
			assertNotNull(bien);


		}
	}

	public void testAgregarMaterialesRepetidoPK()
	{
		setupEscenario1();

		String papitas =("INSERT INTO MATERIALES (NOMBRE, COSTO, TIPO, UNIDAD_MEDIDA, CANTIDAD_INICIAL, CANTIDAD_RESERVADA) VALUES ('GOLD', 415, 'METAL', 'M',578,4)");
		// TODO: handle exception
		try
		{
			x.agREGAR(papitas);
		}
		catch (Exception e)
		{
			String bien = e.getMessage();
			assertNotNull(bien);


		}
	}


	public void testAgregarMaterialesCKRARO()
	{
		setupEscenario1();

		String papitas =("INSERT INTO MATERIALES (NOMBRE, COSTO, TIPO, UNIDAD_MEDIDA, CANTIDAD_INICIAL, CANTIDAD_RESERVADA) VALUES ('MAN', -1, 'METAL', 'M',578,4)");
		// TODO: handle exception
		try
		{
			x.agREGAR(papitas);
		}
		catch (Exception e)
		{
			String bien = e.getMessage();

			assertNotNull(bien);

		}
	}


	public void testEtapapk()
	{
		setupEscenario1();

		String papitas = "INSERT INTO ETAPAS (PERSONAL_REQUERIDO, NUMEROSECUENCIA, NOMBRE, IDENTIFICADOR)values ( '123' ,'  secuencia ', ' nombre', '    ',  fechaIncio , ' fechaFin ');";
		try
		{
			x.agREGAR(papitas);
		}
		catch (Exception e)
		{
			String bien = e.getMessage();
			assertNotNull(bien);


		}
	}


	public void testCapacidadProduccionpk()
	{
		setupEscenario1();

		String papitas = "INSERT INTO CAPACIDADDEPRODUCCION (TIEMPO, NUMPRODUCTOSPRODUCIDOS, CANTIDAD_MATERIA_PRIMA, CODIGO_ESTACION_PROD)values ( '123' ,'  123 ', ' 123', ' 7');";
		try
		{
			x.agREGAR(papitas);
		}
		catch (Exception e)
		{
			String bien = e.getMessage();
			assertNotNull(bien);


		}
	}




	public void testCapacidadProduccionFK()
	{
		setupEscenario1();

		String papitas = "INSERT INTO CAPACIDADDEPRODUCCION (TIEMPO, NUMPRODUCTOSPRODUCIDOS, CANTIDAD_MATERIA_PRIMA, CODIGO_ESTACION_PROD)values ( '123' ,'  123 ', ' 123', ' 123123123132');";
		try
		{
			x.agREGAR(papitas);
		}
		catch (Exception e)
		{
			String bien = e.getMessage();
			assertNotNull(bien);


		}
	}

	public void testCapacidadProduccioncK()
	{
		setupEscenario1();

		String papitas = "INSERT INTO CAPACIDADDEPRODUCCION (TIEMPO, NUMPRODUCTOSPRODUCIDOS, CANTIDAD_MATERIA_PRIMA, CODIGO_ESTACION_PROD)values ( '123' ,'   ', ' 123', ' 123123123132');";
		try
		{
			x.agREGAR(papitas);
		}
		catch (Exception e)
		{
			String bien = e.getMessage();
			assertNotNull(bien);


		}
	}

	public void testClienteFK()
	{
		setupEscenario1();

		String papitas = "INSERT INTO CLIENTES (IDENTIFICACION, DEUDAS, NUMEROREGISTRO, ANTIGUEDAD, NOM_RP_LEGAL, PERSONANATURAL)values ( '123' ,'  -123 ', ' 123', ' 12', 'CHUCK', 'SI');";
		try
		{
			x.agREGAR(papitas);
		}
		catch (Exception e)
		{
			String bien = e.getMessage();
			assertNotNull(bien);


		}
	}

	public void testClienteCK()
	{
		setupEscenario1();

		String papitas = "INSERT INTO CLIENTES (IDENTIFICACION, DEUDAS, NUMEROREGISTRO, ANTIGUEDAD, NOM_RP_LEGAL, PERSONANATURAL)values ( '123' ,'  -123 ', ' 123', ' 12', 'CHUCK', 'TALVEZ');";
		try
		{
			x.agREGAR(papitas);
		}
		catch (Exception e)
		{
			String bien = e.getMessage();
			assertNotNull(bien);


		}
	}

	public void testPERSONAPK()
	{
		setupEscenario1();

		String papitas = "INSERT INTO CLIENTES (IDENTIFICACION, NOMBRE, CODIGO_POSTAL, DIRRECION, TELEFONO, TIPO_ID)values ( '123' ,'JESUS', ' ASD123', 'CARRERA12', 60222, 'CC');";
		try
		{
			x.agREGAR(papitas);
		}
		catch (Exception e)
		{
			String bien = e.getMessage();
			assertNotNull(bien);


		}
	}
	public void testPERSONACK()
	{
		setupEscenario1();

		String papitas = "INSERT INTO CLIENTES (IDENTIFICACION, NOMBRE, CODIGO_POSTAL, DIRRECION, TELEFONO, TIPO_ID)values ( '15484424554' ,'JESUS', ' ASD123', 'CARRERA12', 60222, 'NO C');";
		try
		{
			x.agREGAR(papitas);
		}
		catch (Exception e)
		{
			String bien = e.getMessage();
			assertNotNull(bien);


		}
	}

	public void testPRODUCTOPK()
	{
		setupEscenario1();

		String papitas = "INSERT INTO CLIENTES (NOMBRE_PRODUCTO, COSTO_VENTA, UNIDADES_PRODUCCION, ID_ETAPA, TERMINADO, MATERIAL)values ( '15484424554' ,'JESUS', ' ASD123', 'CARRERA12', 60222, 'NO C');";
		try
		{
			x.agREGAR(papitas);
		}
		catch (Exception e)
		{
			String bien = e.getMessage();
			assertNotNull(bien);


		}
	}
}
