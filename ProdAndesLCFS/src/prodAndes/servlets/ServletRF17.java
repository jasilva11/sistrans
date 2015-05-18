package prodAndes.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import prodAndes.fachado.prodAndes;
import prodAndes.vos.EstacionProduccion;
import prodAndes.vos.PedidoCliente;
import prodAndes.vos.Producto;
import prodAndes.vos.ProductoPedido;

public class ServletRF17 extends ServletTemplate {
	// -----------------------------------------------------------------
	// M�todos
	// -----------------------------------------------------------------

	/**
	 * serial
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Escribe el contenido de la p�gina
	 * @param request Pedido del cliente
	 * @param response Respuesta
	 * @throws IOException Excepci�n de error al escribir la respuesta
	 */
	public void escribirContenido( HttpServletRequest request, HttpServletResponse response ) throws IOException
	{
		PrintWriter out = response.getWriter( );	
		String h=request.getParameter("trabajo");
		if(h==null)
		{
			out.println("				<section id=\"intro\" class=\"container\" style=\"alingment:center;\">");
			out.println("					<div class=\"panel-body\">");
			out.println("					<div class=\"row\">");
			out.println("                <div class=\"col-lg-12\">");
			out.println("                    <div class=\"panel panel-default\">");
			out.println("                            <div class=\"row\">");
			out.println("                                <div class=\"col-lg-6\">");
			out.println("                                    <form role=\"form\">	");
			out.println("    	                               <input type=\"hidden\" name=\"trabajo\" value=\"buscar\">");
			out.println("                                        <div class=\"form-group\">");
			out.println("                                            <label>Buscar estaciones de produccion: </label>");
			out.println("												<select name=\"tipo\">");
			out.println("														<option value=\"activadas\">Activadas</option>");
			out.println(" 												     	<option value=\"desactivadas\">Desactivadas</option>");														 
			out.println("												</select>");
			out.println("                						</div>");
			out.println("                                    <div class=\"form-group\">");
			out.println("                                        <button onclick==\"RF17.htm\">Consultar</button>");
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
		else if(h.equals("buscar"))
		{
			String tipo = request.getParameter("tipo");
			
			out.println("				<section id=\"intro\" class=\"container\" style=\"alingment:center;\">");
			out.println("					<div class=\"panel-body\">");
			out.println("					<div class=\"row\">");
			out.println("                <div class=\"col-lg-12\">");
			out.println("                    <div class=\"panel panel-default\">");
			out.println("                            <div class=\"row\">");
			out.println("                                <div class=\"col-lg-6\">");
			out.println("                                    <form role=\"form\">	");
			out.println("    	                               <input type=\"hidden\" name=\"trabajo\" value=\"sigue\">");
			out.println("                                        <div class=\"form-group\">");
			out.println("                                        <label>Estaciones:</label>");
			
			if(tipo.equals("activadas"))
			{
				ArrayList<EstacionProduccion> estaciones= prodAndes.getInstance().darEstacionesActivadas();
			
				out.println("    	                               <input type=\"hidden\" name=\"h\" value=\"desactivar\">");
				out.println("    	                                   <select multiple class=\"form-control\" name=\"estacion\" style=\"width: 250px;\">");
				for(int i=0;i<estaciones.size();i++)
				{
					out.println("<option>"+estaciones.get(i).getCodigo()+"</option>");
				}
			}
			else
			{
				ArrayList<EstacionProduccion> estaciones= prodAndes.getInstance().darEstacionesDesActivadas();
				out.println("    	                               <input type=\"hidden\" name=\"h\" value=\"activar\">");
				out.println("    	                                   <select multiple class=\"form-control\" name=\"estacion\" style=\"width: 250px;\">");
				for(int i=0;i<estaciones.size();i++)
				{
					out.println("<option>"+estaciones.get(i).getCodigo()+"</option>");
				}
			}
			
			out.println("                                            </select>");
			out.println("    	                                   </div>");	
			out.println("                                     <div class=\"form-group\">");
			out.println("                                        <button onclick=\"RF12.htm\">Cambiar</button>");
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
			String b = request.getParameter("h");
			String estacion = request.getParameter("estacion");
			int id= Integer.valueOf(estacion);
			if(b.equals("desactivar"))
			{
				boolean resp=prodAndes.getInstance().RF17Desactivar(id);
			
				out.println("				<section id=\"intro\" class=\"container\" style=\"alingment:center;\">");
				out.println("					<div class=\"panel-body\">");
				out.println("					<div class=\"row\">");
				out.println("                <div class=\"col-lg-12\">");
				out.println("                    <div class=\"panel panel-default\">");
				out.println("                            <div class=\"row\">");
				out.println("                                <div class=\"col-lg-6\">");
				out.println("                                    <form role=\"form\">");
				if(resp)
				{
					out.println("                                        <div id=\"texto\">Se ha desactivado correctamente la estacion"+estacion+"</div>");
				}
				else
				{
					out.println("                                        <div id=\"texto\">No se ha desactivado correctamente la estacion"+estacion+"</div>");
				}
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
				boolean resp=prodAndes.getInstance().RF17Activar(id);
				
				out.println("				<section id=\"intro\" class=\"container\" style=\"alingment:center;\">");
				out.println("					<div class=\"panel-body\">");
				out.println("					<div class=\"row\">");
				out.println("                <div class=\"col-lg-12\">");
				out.println("                    <div class=\"panel panel-default\">");
				out.println("                            <div class=\"row\">");
				out.println("                                <div class=\"col-lg-6\">");
				out.println("                                    <form role=\"form\">");
				if(resp)
				{
					out.println("                                        <div id=\"texto\">Se ha desactivado correctamente la estacion"+estacion+"</div>");
				}
				else
				{
					out.println("                                        <div id=\"texto\">No se ha desactivado correctamente la estacion"+estacion+"</div>");
				}
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
}

