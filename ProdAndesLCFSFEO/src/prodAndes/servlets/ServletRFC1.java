package prodAndes.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletRFC1 extends ServletTemplate {
	// -----------------------------------------------------------------
    // Metodos
    // -----------------------------------------------------------------

    /**
	 * serial
	 */
	private static final long serialVersionUID = 1L;

    
    public void escribirContenido( HttpServletRequest request, HttpServletResponse response ) throws IOException
    {
    	PrintWriter out = response.getWriter( );
    	out.println("				<section id=\"intro\" class=\"container\" style=\"alingment:center;\">");
    	out.println("					<div class=\"panel-body\">");
    	out.println("					<div class=\"row\">");
    	out.println("                <div class=\"col-lg-12\">");
    	out.println("                    <div class=\"panel panel-default\">");
    	out.println("                            <div class=\"row\">");
    	out.println("                                <div class=\"col-lg-6\">");
    	out.println("                                    <form role=\"form\">	");
    	out.println("                                        <div class=\"form-group\">");
    	out.println("                                            <label>tipo de material:</label>");
    	out.println("												<select>");
    	out.println("														<option value=\"MateriaPrima\">Materia prima</option>");
    	out.println(" 												     	<option value=\"Componente\">Componente</option>");														 
    	out.println("												</select>");
    	out.println("                						</div>");
    	out.println("                                        <div class=\"form-group\">");
    	out.println("                                        <label>rango de existencias</label>");
    	out.println("    	                                    <input type=\"number\"></input>");
    	out.println("    	                                    <input type=\"number\"></input>");
    	out.println("                                        <div class=\"form-group\">");
    	out.println("                                        	<label>fecha de solicitud</label>");
    	out.println("    	                                    <input type=\"date\"></input>");
    	out.println("                						</div>");
    	out.println("                                        <div class=\"form-group\">");
    	out.println("                                        	<label>fecha de entrega</label>");
    	out.println("    	                                    <input type=\"date\"></input>");
    	out.println("                						</div>");
    	out.println("                                    <div class=\"form-group\">");
    	out.println("                                        <button onclick=>Consultar</button>");
    	out.println("                                    </div>");
    	out.println("                					</form>");
    	out.println("                         		</div>");
    	out.println("               				</div>");
    	out.println("               			</div>");
    	out.println("               		</div>");
    	out.println("               		</div>");
    	out.println("               		</div>");
    	out.println("           		</section>");
    }
}
