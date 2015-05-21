package prodAndes.servlets;
            
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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

public class ServletRF13 extends ServletTemplate {
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
		ArrayList<PedidoCliente> pedidos= prodAndes.getInstance().darPedidosPorEntregar();
		if(!prodAndes.getInstance().empezoRF13())
		{
			prodAndes.getInstance().empezarRF13();
			out.println("				<section id=\"intro\" class=\"container\" style=\"alingment:center;\">");
			out.println("					<div class=\"panel-body\">");
			out.println("					<div class=\"row\">");
			out.println("                	<div class=\"col-lg-12\">");
			out.println("                   <div class=\"panel panel-default\">");
			out.println("                   <div class=\"row\">");
			out.println("                   <div class=\"col-lg-6\">");
			out.println("                          <form role=\"form\">	");
			out.println("    	                         <form>");
			out.println("    	                               <input type=\"hidden\" name=\"trabajo\" value=\"sigue\">");
			out.println("										<div class=\"form-group\">");
			out.println("										            <label>ID Pedidos por entregar:</label>");
			out.println("										             <select multiple class=\"form-control\" name=\"pedido\" style=\"width: 250px;\">");
			out.println("										             	<option id=\"\">1</option>");
			System.out.println(pedidos.size());
			for(int i=0;i<pedidos.size();i++)
			{
				
				out.println("                                                   <option value=\""+pedidos.get(i).getIdPedido()+"\">"+pedidos.get(i).getIdPedido()+"</option>");
			}
		    out.println("										             </select>");
		    out.println("									    </div>");
		    out.println("										<div class=\"form-group\">");
		    out.println("										<div class=\"form-group\">");
		    out.println("										     <button onclick=\"RF13.htm\">Ver detalles pedido</button>");
			out.println("                                    </form>");
		    out.println("										</div>          ");                 
		    out.println("										        <table id=\"myTable\">");
		    out.println("							           			 <tr>");
		    out.println("											                <th>Producto</th>");
		    out.println("										                    <th>Cantidad</th>  ");
		    out.println("										         </tr>");
		    out.println("												</table>");
			out.println("                                    <form>");
			out.println("    	                             <input type=\"hidden\" name=\"trabajo\" value=\"termino\"/>");
		    out.println("										        <label>Fecha deseada de entrega:</label>");
		    out.println("												<div class=\"form-group\">");
		    out.println("												<button onclick=\"RF13.htm\">Cancelar Pedido</button>");
		    out.println("												</div>");		
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
				out.println("				<section id=\"intro\" class=\"container\" style=\"alingment:center;\">");
				out.println("					<div class=\"panel-body\">");
				out.println("					<div class=\"row\">");
				out.println("                	<div class=\"col-lg-12\">");
				out.println("                   <div class=\"panel panel-default\">");
				out.println("                   <div class=\"row\">");
				out.println("                   <div class=\"col-lg-6\">");
				out.println("                          <form role=\"form\">	");
				out.println("    	                         <form>");
				out.println("										<div class=\"form-group\">");
				out.println("										            <label>ID Pedidos por entregar:</label>");
				out.println("										             <select multiple class=\"form-control\" name=\"pedido\" style=\"width: 250px;\">");
				out.println("										             	<option id=\"\">1</option>");
				for(int i=0;i<pedidos.size();i++)
				{
					if(pedidos.get(i).getIdPedido() == Integer.parseInt(request.getParameter("pedido")))
					{
						out.println("                                                   <option value=\""+pedidos.get(i).getIdPedido()+"\" selected>"+pedidos.get(i).getIdPedido()+"</option>");
					}
					else
					{
						out.println("                                                   <option value=\""+pedidos.get(i).getIdPedido()+"\">"+pedidos.get(i).getIdPedido()+"</option>");
					}
				}
			    out.println("										             </select>");
			    out.println("									    </div>");
			    out.println("										<div class=\"form-group\">");
			    out.println("										     <button onclick=\"RF13.htm\">Ver detalles pedido</button>");
			    out.println("										</div>          "); 
				out.println("    	                         </form>");
				out.println("                                <form>");
				out.println("									   <label>ID pedido Actual:</label>");
				out.println("    	                               <input type=\"number\" name=\"idpedido\" value="+ request.getParameter("pedido") +">");
				out.println("										        <table id=\"myTable\">");
			    out.println("							           			 <tr>");
			    out.println("											                <th>Producto</th>");
			    out.println("										                    <th>Cantidad</th>  ");
			    out.println("										         </tr>");

				ArrayList<ProductoPedido> r=null;
				PedidoCliente p=null;
				int idPedido= Integer.parseInt(request.getParameter("pedido"));
				for (int i = 0; i < pedidos.size(); i++) {
					if(pedidos.get(i).getIdPedido()==idPedido)
					{
						r=pedidos.get(i).getProductos();
						p=pedidos.get(i);
					}
				}
				for(int i=0;i<r.size();i++)
				{
					out.println("				                        <tr>");
					out.println("					                        <th value=>"+r.get(i).getId_producto()+"</th>");
					out.println("                                           <th>"+r.get(i).getCantidad()+"</th>  ");
					out.println("				                        </tr>");
				}
			    out.println("												</table>");
				out.println("    	                             <input type=\"hidden\" name=\"trabajo\" value=\"termino\"/>");
			    out.println("										        <label>Fecha deseada de entrega: " + p.getFechaEsperada() + "</label>");
			    out.println("												<div class=\"form-group\">");
			    out.println("												<button onclick=\"RF13.htm\">Cancelar Pedido</button>");
			    out.println("												</div>");		
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
				boolean resp=false;
				String res=null;
				try
				{
					System.out.println(request.getParameter("idpedido"));
					resp= prodAndes.getInstance().RF13(Integer.parseInt(request.getParameter("idpedido")));
				}
				catch(Exception e)
				{
					res=e.getMessage();
				}
					out.println("				<section id=\"intro\" class=\"container\" style=\"alingment:center;\">");
					out.println("					<div class=\"panel-body\">");
					out.println("					<div class=\"row\">");
					out.println("                	<div class=\"col-lg-12\">");
					out.println("                   <div class=\"panel panel-default\">");
					out.println("                   <div class=\"row\">");
					out.println("                   <div class=\"col-lg-6\">");
					out.println("                          <form role=\"form\">	");
					out.println("    	                         <form>");
					out.println("    	                               <input type=\"hidden\" name=\"trabajo\" value=\"sigue\">");
					out.println("										<div class=\"form-group\">");
					out.println("										            <label>ID Pedidos por entregar:</label>");
					out.println("										             <select multiple class=\"form-control\" name=\"pedido\" style=\"width: 250px;\">");
					out.println("										             	<option id=\"\">1</option>");
					for(int i=0;i<pedidos.size();i++)
					{out.println("                                              		 <option value=\""+pedidos.get(i).getIdPedido()+"\">"+pedidos.get(i).getIdPedido()+"</option>");}
				    out.println("										             </select>");
				    out.println("									    </div>");
				    out.println("										<div class=\"form-group\">");
				    out.println("										     <button onclick=\"RF13.htm\">Ver detalles pedido</button>");
				    out.println("										</div>          "); 
					out.println("                			</form>");
				    out.println("										        <table id=\"myTable\">");
				    out.println("							           			 <tr>");
				    out.println("											                <th>Producto</th>");
				    out.println("										                    <th>Cantidad</th>  ");
				    out.println("										         </tr>");

					ArrayList<ProductoPedido> r=null;
					PedidoCliente p=null;
					int idPedido= Integer.parseInt(request.getParameter("idpedido"));
					for (int i = 0; i < pedidos.size(); i++) {
						if(pedidos.get(i).getIdPedido()==idPedido)
						{
							r=pedidos.get(i).getProductos();
							p=pedidos.get(i);
						}
					}
					for(int i=0;i<r.size();i++)
					{
						out.println("				                        <tr>");
						out.println("					                        <th value=>"+r.get(i).getId_producto()+"</th>");
						out.println("                                           <th>"+r.get(i).getCantidad()+"</th>  ");
						out.println("				                        </tr>");
					}
				    out.println("												</table>");
					out.println("                                    <form>");
					out.println("    	                             <input type=\"hidden\" name=\"trabajo\" value=\"termino\"/>");
				    out.println("										        <label>Fecha deseada de entrega: " + p.getFechaEsperada() + "</label>");
				    out.println("												<div class=\"form-group\">");
				    out.println("												<button onclick=\"RF13.htm\">Cancelar Pedido</button>");
				    out.println("												</div>");	
				    out.println("												<div class=\"form-group\">");
				    if(resp)
				    {out.println("										        <label>Pedido Cancelado Exitosamente </label>");}
				    else
				    {   out.println("										    <label>El pedido no ha podido ser cancelado: "+res+"</label>");}
				    out.println("												</div>");	
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


			}
		}
	}

