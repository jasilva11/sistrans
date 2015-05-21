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
import prodAndes.vos.PedidoCliente;

import prodAndes.vos.Producto;
import prodAndes.vos.ProductoPedido;

public class ServletRF18 extends ServletTemplate {
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
		if(prodAndes.getInstance().getRF18()==null)
		{
			prodAndes.getInstance().inicRF18();
			out.println("				<section id=\"intro\" class=\"container\" style=\"alingment:center;\">");
			out.println("					<div class=\"panel-body\">");
			out.println("					<div class=\"row\">");
			out.println("                <div class=\"col-lg-12\">");
			out.println("                    <div class=\"panel panel-default\">");
			out.println("                            <div class=\"row\">");
			out.println("                                <div class=\"col-lg-6\">");
			out.println("                                    <form role=\"form\">	");
			out.println("    	                               <form>");
			out.println("    	                               <input type=\"hidden\" name=\"trabajo\" value=\"sigue\">");
			out.println("                                        <div class=\"form-group\">");
			out.println("                                        <label>Productos:</label>");
			out.println("    	                                   <select multiple class=\"form-control\" name=\"producto\" style=\"width: 250px;\">");

			ArrayList<Producto> productos= prodAndes.getInstance().darProductos();
			for(int i=0;i<productos.size();i++)
			{
				out.println("<option value=\""+productos.get(i).getIdProducto()+"\">"+productos.get(i).getNombre()+"</option>");
			}
			
			out.println("                                            </select>");
			out.println("    	                                   </div>");
			out.println("    	                               <div class=\"form-group\">");
			out.println("    	                               <label>Cantidad:</label>");
			out.println("    	                               <input class=\"form-control\" name=\"cantidad\" required/>");
			out.println("    	                               </div>");		
			out.println("                                     <div class=\"form-group\">");
			out.println("                                        <button onclick=\"RF12.htm\">Agregar a la lista</button>");
			out.println("                                    </div>");
			out.println("                					</form>");
			out.println("                                    <table class=\"table table-striped\" name=\"myTable\">");
			out.println("				                        <tr>");
			out.println("					                        <th>Producto</th>");
			out.println("                                            <th>Cantidad</th>  ");
			out.println("				                        </tr>");
			out.println("                                    </table>");
			out.println("                                    <form>");
			out.println("    	                               <input type=\"hidden\" name=\"trabajo\" value=\"termino\"/>");
			out.println("                                        <div class=\"form-group\">");
			out.println("                                            <label>Fecha deseada de entrega:</label>");
			out.println("                                            <input type=\"date\" name=\"fecha\" required></input>");
			out.println("                						</div>");
			out.println("                                        <button onclick=\"RF18.htm\">Enviar</button>");
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
			
			String h=request.getParameter("trabajo");
			if(h.trim().equals("sigue"))
			{
				int cantidad= Integer.valueOf(request.getParameter("cantidad"));
				int idProducto = Integer.valueOf(request.getParameter("producto"));
				prodAndes.getInstance().agregarCarrito18(idProducto, cantidad);
				out.println("				<section id=\"intro\" class=\"container\" style=\"alingment:center;\">");
				out.println("					<div class=\"panel-body\">");
				out.println("					<div class=\"row\">");
				out.println("                <div class=\"col-lg-12\">");
				out.println("                    <div class=\"panel panel-default\">");
				out.println("                            <div class=\"row\">");
				out.println("                                <div class=\"col-lg-6\">");
				out.println("                                    <form role=\"form\">	");
				out.println("    	                               <form>");
				out.println("    	                               <input type=\"hidden\" name=\"trabajo\" value=\"sigue\">");
				
				out.println("                                        <div class=\"form-group\">");
				out.println("                                        <label>Productos:</label>");
				out.println("    	                                   <select multiple class=\"form-control\" name=\"producto\" style=\"width: 250px;\">");
				ArrayList<Producto> productos= prodAndes.getInstance().darProductos();
				for(int i=0;i<productos.size();i++)
				{
					out.println("<option value=\""+productos.get(i).getIdProducto()+"\">"+productos.get(i).getNombre()+"</option>");
				}
				out.println("                                            </select>");
				out.println("    	                               </div>");
				out.println("    	                               <div class=\"form-group\">");
				out.println("    	                               <label>Cantidad:</label>");
				out.println("    	                               <input class=\"form-control\" name=\"cantidad\" required/>");
				out.println("    	                               </div>");			
				out.println("                                     <div class=\"form-group\">");
				out.println("                                        <button onclick=\"RF12.htm\">Agregar a la lista</button>");
				out.println("                                    </div>");
				out.println("                					</form>");
				out.println("                                    <table class=\"table table-striped\" name=\"myTable\">");
				out.println("				                        <tr>");
				out.println("					                        <th>Producto</th>");
				out.println("                                            <th>Cantidad</th>  ");
				out.println("				                        </tr>");

				ArrayList<ProductoPedido> r=prodAndes.getInstance().getRF18();
				for(int i=0;i<r.size();i++)
				{
					out.println("				                        <tr>");
					out.println("					                        <th value=>"+r.get(i).getId_producto()+"</th>");
					out.println("                                            <th>"+r.get(i).getCantidad()+"</th>  ");
					out.println("				                        </tr>");
				}
				out.println("                                    </table>");
				out.println("                                    <form>");
				out.println("    	                               <input type=\"hidden\" name=\"trabajo\" value=\"termino\"/>");
				out.println("                                        <div class=\"form-group\">");
				out.println("                                            <label>Fecha deseada de entrega:</label>");
				out.println("                                            <input type=\"date\" name=\"fecha\" required></input>");
				out.println("                						</div>");
				out.println("                                        <button onclick=\"RF12.htm\">Enviar</button>");
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
			else if(h.equals("termino"))
			{
				
				PedidoCliente r= prodAndes.getInstance().terminarRF18(request.getParameter("fecha"));
				if(r!=null)
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
				out.println("                                            <label>Fecha deseada de entrega:</label>");
				out.println("                                            <label>"+r.getFechaEsperada()+"</label>");
				out.println("                						</div>");
				out.println("                                        <div class=\"form-group\">");
				out.println("                                            <label>Id pedido:</label>");
				out.println("                                            <label>"+r.getIdPedido()+"</label>");
				out.println("                						</div>");
				out.println("                                        <div class=\"form-group\">");
				out.println("                                        <label>Productos:</label>");
				out.println("                                    <table class=\"table table-striped\" id=\"myTable\">");
				out.println("				                        <tr>");
				out.println("					                        <th>Producto</th>");
				out.println("                                            <th>Cantidad</th>  ");
				out.println("				                        </tr>");
				ArrayList<ProductoPedido> r2=r.getProductos();
				for(int i=0;i<r2.size();i++)
				{
					out.println("				                        <tr>");
					out.println("					                        <th>"+r2.get(i).getId_producto()+"</th>");
					out.println("                                            <th>"+r2.get(i).getCantidad()+"</th>  ");
					out.println("				                        </tr>");
				}
				out.println("                                    </table>");
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
					imprimirMensajeError(out, "No es posible", "No es posible por falta de materiales");
			}
		}
	}
}
