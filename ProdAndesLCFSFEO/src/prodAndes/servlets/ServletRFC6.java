package prodAndes.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import prodAndes.fachado.prodAndes;
import prodAndes.vos.MateriaPrima;
import prodAndes.vos.PedidoCliente;


public class ServletRFC6 extends ServletTemplate {
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
		String estado = request.getParameter("estado");
		System.out.println(estado);
		if(estado==null)
		{
			out.println("				<section id=\"intro\" class=\"container\" style=\"alingment:center;\">");
			out.println("					<div class=\"panel-body\">");
			out.println("					<div class=\"row\">");
			out.println("                <div class=\"col-lg-12\">");
			out.println("                    <div class=\"panel panel-default\">");
			out.println("                            <div class=\"row\">");
			out.println("                                <div class=\"col-lg-6\">");
			out.println("                                    <form role=\"form\">	");
			out.println("                                        <div class=\"form-group\">");
			out.println("    	                               <input type=\"hidden\" name=\"estado\" value=\"filtrar\"/>");
			out.println("                                            <select name=\"tipo\">");
			out.println("                                                <option value=\"ID_CLIENTE\">Cliente</option>");
			out.println("                                                <option value=\"ID_PRODUCTO\">Producto</option>");
			out.println("                                                <option value=\"ESTADO\">Estado</option>");
			out.println("                                                <option value=\"FECHA_ENTREGA\">Fecha entrega</option>");
			out.println("                                            </select>");
			out.println("                                            <input name=\"info\"></input>");
			out.println("                                            <div class=\"button\">");
			out.println("                                                <div onclick=\"RFC5.htm\">Filtrar</div> ");
			out.println("                                            </div>");
			out.println("                                        </div>");
			out.println("                                        </input>");
			out.println("                                        <div class=\"form-group\">");
			out.println("                                            <div id=\"texto\">Pedidos:</div>");
			out.println("                                            <table id=\"myTable\" cellspacing=\"10\" cellpadding=\"10\" border=\"4\"> ");
			out.println("                                                 <tr> ");
			out.println("                                                    <td align=\"center\">ID</td> ");
			out.println("                                                    <td align=\"center\">Estado</td> ");
			out.println("                                                    <td align=\"center\">Fecha entrega</td> ");
			out.println("                                                    <td align=\"center\">Fecha esperada</td> ");
			out.println("                                                    <td align=\"center\">ID Clliente</td> ");
			out.println("                                                    <td align=\"center\">Producto</td> ");
			out.println("                                                 </tr> ");
			out.println("                                                <tr>");
			out.println("                                                    <td></td>");
			out.println("                                                    <td></td>");
			out.println("                                                    <td></td>");
			out.println("                                                    <td></td>");
			out.println("                                                    <td></td>");
			out.println("                                                    <td>");
			out.println("                                                        <table cellspacing=\"10\" cellpadding=\"10\" border=\"4\"> ");
			out.println("                                                       <tr> ");
			out.println("                                                            <td align=\"center\">ID producto</td> ");
			out.println("                                                            <td align=\"center\">Cantidad</td> ");
			out.println("                                                       </tr> ");
			out.println("                                                        </table> ");
			out.println("                                                    </td>");
			out.println("                                                </tr>");

			ArrayList<PedidoCliente> pedidos= prodAndes.getInstance().darPedidos();
			for(int i=0; i< pedidos.size();i++)
			{
				out.println("				                        <tr>");
				out.println("					                        <th>"+pedidos.get(i).getIdPedido()+"</th>");
				out.println("                                            <th>"+pedidos.get(i).getEstado()+"</th>  ");
				out.println("                                            <th>"+pedidos.get(i).getFechaEntrega()+"</th>  ");
				out.println("                                            <th>"+pedidos.get(i).getFechaEsperada()+"</th>  ");
				out.println("                                            <th>"+pedidos.get(i).getId_cliente()+"</th>  ");
				for(int j=0;j<pedidos.get(i).getProductos().size();j++)
				{
					out.println("<th>");
					out.println("				                        <tr>");
					out.println("					                        <th>"+pedidos.get(i).getProductos().get(j).getId_producto()+"</th>");
					out.println("                                            <th>"+pedidos.get(i).getProductos().get(j).getCantidad()+"</th>  ");
					out.println("				                        </tr>");
					out.println("</th>");
				}
				out.println("				                        </tr>");
			}

			out.println("                                            </table>");
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
			String filtro = request.getParameter("tipo");
			String info = request.getParameter("info");
			out.println("				<section id=\"intro\" class=\"container\" style=\"alingment:center;\">");
			out.println("					<div class=\"panel-body\">");
			out.println("					<div class=\"row\">");
			out.println("                <div class=\"col-lg-12\">");
			out.println("                    <div class=\"panel panel-default\">");
			out.println("                            <div class=\"row\">");
			out.println("                                <div class=\"col-lg-6\">");
			out.println("                                    <form role=\"form\">	");
			out.println("                                        <div class=\"form-group\">");
			out.println("    	                               <input type=\"hidden\" name=\"estado\" value=\"filtrar\"/>");
			out.println("                                            <select name=\"tipo\">");
			out.println("                                                <option value=\"ID_CLIENTE\">Cliente</option>");
			out.println("                                                <option value=\"ID_PRODUCTO\">Producto</option>");
			out.println("                                                <option value=\"ESTADO\">Estado</option>");
			out.println("                                                <option value=\"FECHA_ENTREGA\">Fecha entrega</option>");
			out.println("                                            </select>");
			out.println("                                            <input name=\"info\"></input>");
			out.println("                                            <div class=\"button\">");
			out.println("                                                <div onclick=\"RF12.htm\">Filtrar</div> ");
			out.println("                                            </div>");
			out.println("                                        </div>");
			out.println("                                        <div class=\"form-group\">");
			out.println("                                            <div id=\"texto\">Pedidos:</div>");
			out.println("                                            <table id=\"myTable\" cellspacing=\"10\" cellpadding=\"10\" border=\"4\"> ");
			out.println("                                                 <tr> ");
			out.println("                                                    <td align=\"center\">ID</td> ");
			out.println("                                                    <td align=\"center\">Estado</td> ");
			out.println("                                                    <td align=\"center\">Fecha entrega</td> ");
			out.println("                                                    <td align=\"center\">Fecha esperada</td> ");
			out.println("                                                    <td align=\"center\">ID Clliente</td> ");
			out.println("                                                    <td align=\"center\">Producto</td> ");
			out.println("                                                 </tr> ");
			out.println("                                                <tr>");
			out.println("                                                    <td></td>");
			out.println("                                                    <td></td>");
			out.println("                                                    <td></td>");
			out.println("                                                    <td></td>");
			out.println("                                                    <td></td>");
			out.println("                                                    <td>");
			out.println("                                                        <table cellspacing=\"10\" cellpadding=\"10\" border=\"4\"> ");
			out.println("                                                       <tr> ");
			out.println("                                                            <td align=\"center\">ID producto</td> ");
			out.println("                                                            <td align=\"center\">Cantidad</td> ");
			out.println("                                                       </tr> ");
			out.println("                                                        </table> ");
			out.println("                                                    </td>");
			out.println("                                                </tr>");

			ArrayList<PedidoCliente> pedidos= prodAndes.getInstance().getRFC5(filtro, info);
			for(int i=0; i< pedidos.size();i++)
			{
				out.println("				                        <tr>");
				out.println("					                        <th>"+pedidos.get(i).getIdPedido()+"</th>");
				out.println("                                            <th>"+pedidos.get(i).getEstado()+"</th>  ");
				out.println("                                            <th>"+pedidos.get(i).getFechaEntrega()+"</th>  ");
				out.println("                                            <th>"+pedidos.get(i).getFechaEsperada()+"</th>  ");
				out.println("                                            <th>"+pedidos.get(i).getId_cliente()+"</th>  ");
				for(int j=0;j<pedidos.get(i).getProductos().size();j++)
				{
					out.println("<th>");
					out.println("				                        <tr>");
					out.println("					                        <th>"+pedidos.get(i).getProductos().get(j).getId_producto()+"</th>");
					out.println("                                           <th>"+pedidos.get(i).getProductos().get(j).getCantidad()+"</th>  ");
					out.println("				                        </tr>");
					out.println("</th>");
				}
				out.println("				                        </tr>");
			}

			out.println("                                            </table>");
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

