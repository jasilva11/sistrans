package Prodandes.servlets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.system.server.ServerConfig;
import org.jboss.system.server.ServerConfigLocator;


import Prodandes.fachada.Prodandes;

public class ConsultarExistenciaUnMaterial extends HttpServlet
{	
	public final static String RUTA_ARCHIVO_SERIALIZADO = "/prodAndes.data";

	// -----------------------------------------------------------------
	// Métodos
	// -----------------------------------------------------------------

	/**
	 * Inicialización del Servlet
	 */
	public void init( ) throws ServletException
	{
	}

	public void destroy( )
	{
		System.out.println("Destruyendo instancia");
		try
		{
			if ( Prodandes.darInstancia( ) != null ) 
			{
				ServerConfig config = ServerConfigLocator.locate( );
				File dataDir = config.getServerDataDir();
				File tmp = new File( dataDir + RUTA_ARCHIVO_SERIALIZADO );
				System.out.println("Nombre=" + tmp.getName( ));
				System.out.println("Path=" + tmp.getPath( ));
				System.out.println("Abs. Path=" + tmp.getAbsolutePath( ));

				FileOutputStream fos = new FileOutputStream( tmp );
				ObjectOutputStream oos = new ObjectOutputStream( fos );
				oos.writeObject( Prodandes.darInstancia( ) );
				oos.close();
				fos.close();
				System.out.println("Album Serializado");
			}
		}
		catch (Exception e)
		{
			System.out.println("Error de persistencia en Servidor");
		}
	}

	private void procesarSolicitud( HttpServletRequest request, HttpServletResponse response ) throws IOException
	{
		
		imprimirEncabezado(response);

    }
	


	/**
	 * Maneja un pedido GET de un cliente
	 * @param request Pedido del cliente
	 * @param response Respuesta
	 */
	protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		// Maneja el GET y el POST de la misma manera
		procesarSolicitud( request, response );
	}

	/**
	 * Maneja un pedido POST de un cliente
	 * @param request Pedido del cliente
	 * @param response Respuesta
	 */
	protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		// Maneja el GET y el POST de la misma manera
		procesarSolicitud( request, response );
	}

	/**
	 * Imprime el encabezado con el diseño de la página
	 * @param response Respuesta
	 * @throws IOException Excepción al imprimir en el resultado
	 */
	private void imprimirEncabezado( HttpServletResponse response ) throws IOException
	{
		// Obtiene el flujo de escritura de la respuesta
		PrintWriter out = response.getWriter( );

		// Imprime el encabezado
		out.println("<head>");
		out.println("<meta charset=\"utf-8\" />");
		out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=0\" />");
		out.println("<title>ProdAndes</title>");
		out.println("<link rel=\"shortcut icon\" type=\"image/x-icon\" href=\"css/images/icon.png\" />");
		out.println("<link rel=\"stylesheet\" href=\"css/style.css\" type=\"text/css\" media=\"all\" />");
		out.println("<link href='http://fonts.googleapis.com/css?family=Coda' rel='stylesheet' type='text/css' />");
		out.println("<link href='http://fonts.googleapis.com/css?family=Jura:400,500,600,300' rel='stylesheet' type='text/css' />");
		out.println("<script src=\"js/jquery-1.8.0.min.js\" type=\"text/javascript\"></script>");
		out.println("<script src=\"js/jquery.touchwipe.1.1.1.js\" type=\"text/javascript\"></script>");
		out.println("<script src=\"js/jquery.carouFredSel-5.5.0-packed.js\" type=\"text/javascript\"></script>");
		out.println("	<!--[if lt IE 9]>");
		out.println("<script src=\"js/modernizr.custom.js\"></script>");
		out.println("<![endif]-->");
		out.println("<script src=\"js/functions.js\" type=\"text/javascript\"></script>");
		out.println("<header class=\"header\">");
		out.println("<div class=\"shell\">");
		out.println("<div class=\"header-top\">");
		out.println("<h1 id=\"logo\"><a href=\"#\">Prodandes</a></h1>");
		out.println("<nav id=\"navigation\">");
		out.println("	<a href=\"#\" class=\"nav-btn\">Home<span></span></a>");
		out.println("	<ul>");
		out.println("<li><a href=\"home.html\">Inicio</a></li>");
		out.println("<li class=\"active home\"><a href=\"#\">Buscar</a></li>");
		out.println("<li><a href=\"#\">Projects</a></li>");
		out.println("<li><a href=\"#\">Solutions</a></li>");
		out.println("<li><a href=\"#\">Jobs</a></li>");
		out.println("<li><a href=\"#\">Blog</a></li>");
		out.println("<li><a href=\"#\">Contacts</a></li>");
		out.println("</ul>");
		out.println("</nav>");
		out.println("<div class=\"cl\">&nbsp;</div>");
		out.println("</div>");
		out.println("</head>");
		out.println("");
		out.println( "<p><img src=\"imagenes/7E0F6B7EF.png\" width=\"375\" height=\"210\" /></p>" );
		out.println("<body>");
		out.println("<blockquote>");
		out.println("  <blockquote>");
		out.println("    <blockquote>");
		out.println("      <blockquote>");
		out.println("        <blockquote>");
		out.println("          <blockquote>");
		out.println("            <h1>Consultar existencia Material</h1>");
		out.println("            <p>.</p>");
		out.println("          </blockquote>");
		out.println("        </blockquote>");
		out.println("      </blockquote>");
		out.println("    </blockquote>");
		out.println("  </blockquote>");
		out.println("</blockquote>");
		out.println("<form id=\"form1\" name=\"form1\" method=\"post\" action=\"registrarCliente.htm\">");
		out.println("  <p>");
		out.println("    <input type=\"submit\" name=\"button\" id=\"button\" value=\"Registrar Cliente\" />");
		out.println("  </p>");
		out.println("  <p>");
		out.println("      <input type=\"checkbox\" name=\"option1\" value=\"rango\" />");
		out.println("  </p>");
		out.println("  <p>");
		out.println("      <input type=\"checkbox\" name=\"option2\" value=\"etapa\" />");
		out.println("  </p>");
		out.println("  <p>");
		out.println("      <input type=\"checkbox\" name=\"option3\" value=\"producto\" />");
		out.println("  </p>");
		out.println("</form>");
		out.println("<p>&nbsp;</p>");
		out.println("</body>");
		out.println("</html>");
	}
	
	
	 /**
     * Imprime un mensaje de error
     * @param respuesta Respuesta al cliente
     * @param titulo Título del error
     * @param mensaje Mensaje del error
     */
    private void imprimirMensajeError( PrintWriter respuesta, String titulo, String mensaje )
    {
    	respuesta.println( "<p><img src=\"imagenes/Untitled-1342.png\" width=\"375\" height=\"210\" /></p>" );
    	respuesta.println( "                      <p class=\"error\"><b>Ha ocurrido un error!:<br>" );
        respuesta.println( "                      </b>" + titulo + "</p><p>" + mensaje + ". </p>" );
        respuesta.println( "                      <p>Intente la " );
        respuesta.println( "                      operación nuevamente. Si el problema persiste, contacte" );
        respuesta.println( "                      al administrador del sistema.</p>" );
        respuesta.println( "<p><img src=\"imagenes/error.JPG\" width=\"375\" height=\"210\" /></p>" );
        respuesta.println( "</body>" );
		respuesta.println( "</html>" );
    }
}

