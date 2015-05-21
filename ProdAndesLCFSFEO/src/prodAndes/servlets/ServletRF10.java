package prodAndes.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import prodAndes.fachado.prodAndes;

public class ServletRF10 extends ServletTemplate {
	// -----------------------------------------------------------------
    // Metodos
    // -----------------------------------------------------------------

    /**
	 * serial
	 */
	private static final long serialVersionUID = 1L;

    
    public void escribirContenido( HttpServletRequest request, HttpServletResponse response ) throws IOException
    {
    	if(!prodAndes.getInstance().empezoRF10())
    	{
    		prodAndes.getInstance().empezarRF10();
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
	    	out.println("                                            <label>id del Pedido:</label>");
	    	out.println("                                            <input type=\"number\" name=\"idPedido\"> </input>");
	    	out.println("                						</div>");
	    	out.println("                                        <div class=\"form-group\">");
	    	out.println("                                            <label>id del producto:</label>");
	    	out.println("                                            <input type=\"number\" name=\"idProducto\"> </input>");
	    	out.println("                						</div>");
	    	out.println("                                       <div class=\"form-group\">");
	    	out.println("                                        	<label>nombre de la etapa</label>");
	    	out.println("    	                                    <input type=\"text\" name=\"nomEtapa\"></input>");
	    	out.println("                                       <div class=\"form-group\">");
	    	out.println("                                        <div class=\"form-group\">");
	    	out.println("                                            <label>Numero de secuencia de la etapa:</label>");
	    	out.println("                                            <input type=\"number\" name=\"numEtapa\"></input>");
	    	out.println("                						</div>");
			out.println("                                    <form>");
	    	out.println("                                        <button onclick=>Registrar ejecuci�n</button>");
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
    	else
    	{
    		boolean resp=false;
    		String error="otro";
			int prueba=0;

			try {
				resp = prodAndes.getInstance().RF10(request.getParameter("idPedido"), request.getParameter("idProducto"), request.getParameter("nomEtapa"), Integer.parseInt(request.getParameter("numEtapa")),prueba);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				error=e.getMessage();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				error=e.getMessage();
			}
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
	    	out.println("                                            <label>id del Pedido:</label>");
	    	out.println("                                            <input type=\"number\" name=\"idPedido\"> </input>");
	    	out.println("                						</div>");
	    	out.println("                                        <div class=\"form-group\">");
	    	out.println("                                            <label>id del producto:</label>");
	    	out.println("                                            <input type=\"number\" name=\"idProducto\"> </input>");
	    	out.println("                						</div>");
	    	out.println("                                       <div class=\"form-group\">");
	    	out.println("                                        	<label>nombre de la etapa</label>");
	    	out.println("    	                                    <input type=\"text\" name=\"nomEtapa\"></input>");
	    	out.println("                                       <div class=\"form-group\">");
	    	out.println("                                        <div class=\"form-group\">");
	    	out.println("                                            <label>Numero de secuencia de la etapa:</label>");
	    	out.println("                                            <input type=\"number\" name=\"numEtapa\"></input>");
	    	out.println("                						</div>");
			out.println("                                    <form>");
	    	out.println("                                        <button onclick=>Registrar ejecuci�n</button>");
	    	out.println("                                    </div>");
	    	out.println("                                        <div class=\"form-group\">");
	    	if(resp)
	    	{
	    		out.println("                                            <label>Ejecucion registrada correctamente</label>");
	    	}
	    	else
	    	{
	    		out.println("                                            <label>Error: Ejecucion no registrada:" + error + prueba + "</label>");
	    	}
	    	out.println("                						</div>");
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
}
