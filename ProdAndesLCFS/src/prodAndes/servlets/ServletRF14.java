package prodAndes.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import prodAndes.fachado.prodAndes;
import prodAndes.vos.Materia_Componente;
import prodAndes.vos.PedidoCliente;
import prodAndes.vos.ProductoPedido;

public class ServletRF14 extends ServletTemplate {
	// -----------------------------------------------------------------
    // Metodos
    // -----------------------------------------------------------------

    /**
	 * serial
	 */
	private static final long serialVersionUID = 1L;

    /**
     * Escribe el contenido de la pagina
     * @param request Pedido del cliente
     * @param response Respuesta
     * @throws IOException Excepcion de error al escribir la respuesta
     */
    public void escribirContenido( HttpServletRequest request, HttpServletResponse response ) throws IOException
    {
    	String h=request.getParameter("trabajo");
    	PrintWriter out = response.getWriter( );
    	if(h==null){
    	
    	out.println("				<section id=\"intro\" class=\"container\">");
    	out.println("					<div class=\"panel-body\">");
    	out.println("					<div class=\"row\">");
    	out.println("                <div class=\"col-lg-12\">");
    	out.println("                    <div class=\"panel panel-default\">");
    	out.println("                            <div class=\"row\">");
    	out.println("                                <div class=\"col-lg-6\">");
    	out.println("                                    <form role=\"form\">		");
    	out.println("										<div class=\"form-group\" >");
    	out.println("                                            <label>Pedido:</label>");
    	out.println("                                            	<select multiple class=\"form-control\" name=\"pedido\" style=\"width: 250px;\">");
    	ArrayList<PedidoCliente> r= prodAndes.getInstance().darPedidos();
 
    	for(int i=0; i<r.size();i++)
    	{
    		out.println("<option>"+r.get(i).getIdPedido()+"</option>");
    	}
    	out.println("                                            	</select>");
    	out.println("           								</div>");
    	out.println("                						<div class=\"form-group\">");
    	out.println("                                            <label>Fecha de entrega:</label>");
    	out.println("                                            <input type=\"date\" name=\"fecha\" class=\"form-control\" required></input>");
    	out.println("                						</div>");
		out.println("                                    <form>");
		out.println("    	                               <input type=\"hidden\" name=\"trabajo\" value=\"termino\"/>");
		out.println("                                        <button onclick=\"RF14.htm\">Enviar</button>");
		out.println("                                    </form>");
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
    		String fechaEntrega = request.getParameter("fecha");
			int id = Integer.valueOf(request.getParameter("pedido"));
    		ArrayList<Materia_Componente> r=prodAndes.getInstance().terminarRF14(fechaEntrega, id);
        	out.println("				<section id=\"intro\" class=\"container\">");
        	out.println("					<div class=\"panel-body\">");
        	out.println("					<div class=\"row\">");
        	out.println("                <div class=\"col-lg-12\">");
        	out.println("                    <div class=\"panel panel-default\">");
        	out.println("                            <div class=\"row\">");
        	out.println("                                <div class=\"col-lg-6\">");
        	out.println("                                    <form role=\"form\">		");
			out.println("                                    <table class=\"table table-striped\" name=\"myTable\">");
			out.println("				                        <tr>");
			out.println("				                        <tr>");
			out.println("					                        <th>Id</th>");
			out.println("                                            <th>Cantidad</th>  ");
			out.println("                                            <th>Cantidad reservada</th>  ");
			out.println("                                            <th>Nombre</th>  ");
			out.println("                                            <th>Unidad de medida</th>  ");
			out.println("				                        </tr>");

			for(int i=0;i<r.size();i++)
			{
				out.println("				                        <tr>");
				out.println("					                        <th>"+r.get(i).getId()+"</th>");
				out.println("                                            <th>"+r.get(i).getCantidad()+"</th>  ");
				out.println("                                            <th>"+r.get(i).getCantidadReservada()+"</th>  ");
				out.println("                                            <th>"+r.get(i).getNombre()+"</th>  ");
				out.println("                                            <th>"+r.get(i).getUnidadMedida()+"</th>  ");
				out.println("				                        </tr>");
			}
			out.println("                                    </table>");
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
