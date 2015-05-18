
package prodAndes.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import prodAndes.fachado.prodAndes;


/**
 * Clase abstacta que implementa un Servlet.
 */
public abstract class ServletTemplate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * constructor de la clase. Llama al constructor de 
     * su padre.
     */
    public ServletTemplate() {
        super();

    }

	/**
	 * Recibe la solicitud y la herramienta de respuesta a las solicitudes
	 * hechas por los metodos get. Invoca el metodo procesarPedido.
	 * @param request pedido del cliente
	 * @param response respuesta del servlet
	 * @throws IOException Excepcion de error al escribir la respuesta
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		procesarPedido(request, response);
	}

	/**
	 * Recibe la solicitud y la herramienta de respuesta a las solicitudes
	 * hechas por los m�todos post. Invoca el m�todo procesarPedido.
	 * @param request pedido del cliente
	 * @param response respuesta del servlet
	 * @throws IOException Excepci�n de error al escribir la respuesta
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{

		procesarPedido(request, response);
	}
	
	/**
     * Procesa el pedido de igual manera para todos
     * @param request Pedido del cliente
     * @param response Respuesta del servlet
     * @throws IOException Excepci�n de error al escribir la respuesta
     */
	
    private void procesarPedido( HttpServletRequest request, HttpServletResponse response ) throws IOException
    {
    	//TODO Si hay otras fachadas, ellas tambien deben inicializar la ruta.
    	prodAndes.getInstance().inicializarRuta("/Applications/jboss-4.2.2.GA/server/default/data/prodAndes");
        //
        // Comienza con el Header del template
        imprimirHeader( request, response );
        //
        // Escribe el contenido de la p�gina
        escribirContenido( request, response );
        //
        // Termina con el footer del template
        imprimirFooter( response );

    }

    /**
     * Escribe la cabecera de la p�gina web
     * @param request pedido del cliente
     * @param response respuesta del servlet
     * @throws IOException Excepci�n de error al recibir la respuesta
     */
	private void imprimirHeader(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		 //
        // Saca el printer de la repuesta
        PrintWriter out = response.getWriter( );
        //
        // Imprime el header
        out.println("<!DOCTYPE HTML>");
        out.println("<!--");
        out.println("	Dopetrope by HTML5 UP");
        out.println("	html5up.net | @n33co");
        out.println("	Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)");
        out.println("-->");
        out.println("<html>");
        out.println("	<head>");
        out.println("		<title>Registrar pedido</title>");
        out.println("		<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\" />");
        out.println("		<meta name=\"description\" content=\"\" />");
        out.println("		<meta name=\"keywords\" content=\"\" />");
        out.println("		<!--[if lte IE 8]><script src=\"css/ie/html5shiv.js\"></script><![endif]-->");
        out.println("		<script src=\"js/jquery.min.js\"></script>");
        out.println("		<script src=\"js/jquery.dropotron.min.js\"></script>");
        out.println("		<script src=\"js/skel.min.js\"></script>");
        out.println("		<script src=\"js/skel-layers.min.js\"></script>");
        out.println("		<script src=\"js/init.js\"></script>");
        out.println("		<noscript>");
        out.println("			<link rel=\"stylesheet\" href=\"css/skel.css\" />");
        out.println("			<link rel=\"stylesheet\" href=\"css/style.css\" />");
        out.println("			<link rel=\"stylesheet\" href=\"css/style-desktop.css\" />");
        out.println("		</noscript>");
        out.println("		<!--[if lte IE 8]><link rel=\"stylesheet\" href=\"css/ie/v8.css\" /><![endif]-->");
        out.println("	</head>");
        out.println("	<body class=\"homepage\">");
        out.println("			<div id=\"header-wrapper\">");
        out.println("				<div id=\"header\">");
        out.println("					");
        out.println("					<!-- Logo -->");
        out.println("						<h1>ProdAndes</h1>");
        out.println("					");
        out.println("					<!-- Nav -->");
        out.println("						<nav id=\"nav\">");
        out.println("							<ul>");
        out.println("							<li><a href=\"index.html\">Home</a></li>");
        out.println("								<li>");
        out.println("									<a href=\"\">Etapa de producci�n</a>");
        out.println("									<ul>");
        out.println("										<li><a href=\"RF10.htm\">Registrar ejecuci�n </a></li>");
        out.println("										<li>");
        out.println("											<a href=\"\">Ejemplo</a>");
        out.println("											<ul>");
        out.println("												<li><a href=\"#\">BLABLA</a></li>");
        out.println("											</ul>");
        out.println("										</li>");
        out.println("									</ul>");
        out.println("								</li>");
        out.println("								<li><a href=\"\">Material</a>");
        out.println("									<ul>");
        out.println("										<li><a href=\"RFC1.htm\">Consultar existencia</a></li>");
        out.println("										<li><a href=\"#\">Buscar</a></li>");
        out.println("										<li><a href=\"RF11.htm\">Registrar llegada</a></li>");
        out.println("									</ul>");
        out.println("								</li>");
        out.println("								<li><a href=\"\">Estacion de produccion</a>");
        out.println("									<ul>");
        out.println("										<li><a href=\"RF17.htm\">Cambiar de estado</a></li>");
        out.println("									</ul>");
        out.println("								</li>");
        out.println("								<li><a href=\"\">Pedido</a>");
        out.println("									<ul>");
        out.println("										<li><a href=\"RFC5.htm\">Buscar</a></li>");
        out.println("										<li><a href=\"RFC10.htm\">Buscar por precio</a></li>");
        out.println("										<li><a href=\"RFC11.htm\">Buscar por material</a></li>");
        out.println("										<li><a href=\"RF12.htm\">Registrar</a></li>");
        out.println("										<li><a href=\"RF14.htm\">Registrar entrega</a></li>");
        out.println("									</ul>");
        out.println("                                <li><a href=\"LOGIN.htm\">Ingresar</a></li>");
        out.println("								</li>");
        out.println("							</ul>");
        out.println("						</nav>");
        out.println("				</div>");		
	}

	private void imprimirFooter(HttpServletResponse response) throws IOException
	{
		
        // Saca el writer de la respuesta
        PrintWriter out = response.getWriter( );
        //
        // Imprime el footer
        out.println("			<div id=\"footer-wrapper\">");
        out.println("				<section id=\"footer\" class=\"container\">");
        out.println("					<div class=\"row\">");
        out.println("						<div class=\"8u\">");
        out.println("							<section>");
        out.println("								<header>");
        out.println("									<h2>Blandit nisl adipiscing</h2>");
        out.println("								</header>");
        out.println("								<ul class=\"dates\">");
        out.println("									<li>");
        out.println("										<span class=\"date\">Jan <strong>27</strong></span>");
        out.println("										<h3><a href=\"#\">EJEMPLO</a></h3>");
        out.println("										<p>NEWS</p>");
        out.println("									</li>");
        out.println("								</ul>");
        out.println("							</section>");
        out.println("						</div>");
        out.println("					</div>");
        out.println("					<div class=\"row\">");
        out.println("						<div class=\"4u\">");
        out.println("							<section>");
        out.println("								<header>");
        out.println("									<h2>Desarrolladores</h2>");
        out.println("								</header>");
        out.println("								<ul class=\"contact\">");
        out.println("									<li>");
        out.println("										<h3>By:</h3>");
        out.println("										<p><a href=\"#\">Felipe Sabogal & Laura Cortes</a></p>");
        out.println("									</li>");
        out.println("								</ul>");
        out.println("							</section>");
        out.println("						</div>");
        out.println("					</div>");
        out.println("				</section>");
        out.println("			</div>");
        out.println("	</body>");
        out.println("</html>");
	}
	
    /**
     * Imprime un mensaje de error
     * @param respuesta Respuesta al cliente
     * @param titulo T�tulo del error
     * @param mensaje Mensaje del error
     */
    protected void imprimirMensajeError( PrintWriter respuesta, String titulo, String mensaje )
    {
        respuesta.println( "                      <p class=\"error\"><b>Ha ocurrido un error!:<br>" );
        respuesta.println( "                      </b>" + titulo + "</p><p>" + mensaje + ". </p>" );
        respuesta.println( "                      <p>Intente la " );
        respuesta.println( "                      operaci�n nuevamente. Si el problema persiste, contacte " );
        respuesta.println( "                      al administrador del sistema.</p>" );
        respuesta.println( "                      <p><a href=\"index.html\">Volver a la p�gina principal</a>" );
    }

    /**
     * Imprime un mensaje de error
     * @param respuesta Respuesta al cliente
     * @param titulo T�tulo del error
     * @param exception Excepci�n de error
     * @param mensaje Mensaje del error
     */
    protected void imprimirMensajeError( PrintWriter respuesta, String titulo, String mensaje, Exception exception )
    {
        respuesta.println( "                      <p class=\"error\"><b>Ha ocurrido un error!:<br>" );
        respuesta.println( "                      </b>" + titulo + "</p><p>" + mensaje + ". Mas Informaci�n:<br>" );
        exception.printStackTrace( respuesta );
        respuesta.println( "</p>" );
        respuesta.println( "                      <p>Intente la " );
        respuesta.println( "                      operaci�n nuevamente. Si el problema persiste, contacte " );
        respuesta.println( "                      al administrador del sistema.</p>" );
        respuesta.println( "                      <p><a href=\"index.htm\">Volver a la p�gina principal</a>" );
    }

    /**
     * Imprime un mensaje de �xito
     * @param respuesta Respuesta al cliente
     * @param titulo T�tulo del mensaje
     * @param mensaje Contenido del mensaje
     */
    protected void imprimirMensajeOk( PrintWriter respuesta, String titulo, String mensaje )
    {
        respuesta.println( "                      <p class=\"ok\"><b>Operaci�n exitosa:<br>" );
        respuesta.println( "                      </b>" + titulo + "</p><p>" + mensaje + ". </p>" );
        respuesta.println( "                      <p><a href=\"index.htm\">Volver a la p�gina principal</a>" );
    }

	
	// -----------------------------------------------------------------
    // M�todos Abstractos
    // -----------------------------------------------------------------
    /**
     * Escribe el contenido de la p�gina
     * @param request Pedido del cliente
     * @param response Respuesta
     * @throws IOException Excepci�n de error al escribir la respuesta
     */
    public abstract void escribirContenido( HttpServletRequest request, HttpServletResponse response ) throws IOException;

}
