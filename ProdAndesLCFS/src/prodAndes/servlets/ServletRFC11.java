package prodAndes.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import prodAndes.fachado.prodAndes;
import prodAndes.vos.PedidoCliente;



public class ServletRFC11 extends ServletTemplate {
	// -----------------------------------------------------------------
	// Metodos
	// -----------------------------------------------------------------

	/**
	 * serial
	 */
	private static final long serialVersionUID = 1L;

	private final static String MATERIA_PRIMA= "Materia prima";

	private final static String COMPONENTE= "Componente";

	private String tipo;

	private int id;

	public void escribirContenido( HttpServletRequest request, HttpServletResponse response ) throws IOException
	{
		PrintWriter out = response.getWriter( );
		String estado = request.getParameter("estado");
		
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
			out.println("    	                               <input type=\"hidden\" name=\"estado\" value=\"buscar\"/>");
			out.println("                                        <div class=\"form-group\">");
			out.println("                                            <select name=\"tipo\">");
			out.println("                                                <option value=\""+MATERIA_PRIMA+"\">Materia Prima</option>");
			out.println("                                                <option value=\""+COMPONENTE+"\">Componente</option>");
			out.println("                                            </select>");
			out.println("                                        </div>");
			out.println("                                        <div class=\"form-group\">");
			out.println("                                            <div id=\"texto\">Identificador:</div>");
			out.println("                                            <input type=\"number\" name=\"id\"></input>");
			out.println("                                            <div class=\"button\">");
			out.println("                                                <button class=\"button\" onclick=\"RFC11.htm\">Buscar</button> ");
			out.println("                                            </div>");
			out.println("                                        </div>");
			out.println("                                        </input>");
			out.println("                                        <div class=\"form-group\">");
			out.println("                                            <div id=\"texto\">Pedidos:</div>");
			out.println("                                            <table id=\"myTable\" cellspacing=\"10\" cellpadding=\"10\" border=\"4\"> ");
			out.println("                                                 <tr> ");
			out.println("                                                    <td align=\"center\">ID</td> ");
			out.println("                                                    <td align=\"center\">Producto</td> ");
			out.println("                                                 </tr> ");
			out.println("                                            </table>");
			out.println("                					</form>");
			out.println("                                    <form role=\"form\">	");
			out.println("                                            <div class=\"dataTables_paginate paging_simple_numbers\" id=\"dataTables-example_paginate\">");
			out.println("                                                <ul class=\"pagination\">");
			out.println("    	                               <input type=\"hidden\" name=\"estado\" value=\"anterior\"/>");
			out.println("                                                        <button class=\"button\" onclick=\"RFC11.htm\">Previous</a>");
			out.println("                                        </input>");
			out.println("                					</form>");
			out.println("                                    <form role=\"form\">	");
			out.println("    	                               <input type=\"hidden\" name=\"estado\" value=\"siguiente\"/>");
			out.println("                                                        <button class=\"button\" onclick=\"RFC11.htm\">Next</button>");
			out.println("                                        </input>");
			out.println("                                                </ul>");
			out.println("                                            </div>");
			out.println("                					</form>");
			out.println("                         		</div>");
			out.println("               				</div>");
			out.println("               			</div>");
			out.println("               		</div>");
			out.println("               		</div>");
			out.println("               		</div>");
			out.println("           		</section>");
		}	
		else if(estado.equals("buscar"))
		{
			tipo = request.getParameter("tipo");
			id = Integer.valueOf(request.getParameter("id"));
			prodAndes.getInstance().iniciarPaginacion();
			ArrayList<PedidoCliente> resp=prodAndes.getInstance().getRFC11(tipo, id);
			out.println("				<section id=\"intro\" class=\"container\" style=\"alingment:center;\">");
			out.println("					<div class=\"panel-body\">");
			out.println("					<div class=\"row\">");
			out.println("                <div class=\"col-lg-12\">");
			out.println("                    <div class=\"panel panel-default\">");
			out.println("                            <div class=\"row\">");
			out.println("                                <div class=\"col-lg-6\">");
			out.println("                                    <form role=\"form\">	");
			out.println("    	                               <input type=\"hidden\" name=\"estado\" value=\"buscar\"/>");
			out.println("                                        <div class=\"form-group\">");
			out.println("                                            <select name=\"tipo\">");
			out.println("                                                <option value=\""+MATERIA_PRIMA+"\">Materia Prima</option>");
			out.println("                                                <option value=\""+COMPONENTE+"\">Componente</option>");
			out.println("                                            </select>");
			out.println("                                        </div>");
			out.println("                                        <div class=\"form-group\">");
			out.println("                                            <div id=\"texto\">Identificador:</div>");
			out.println("                                            <input type=\"number\" name=\"id\"></input>");
			out.println("                                            <div class=\"button\">");
			out.println("                                                <button class=\"button\" onclick=\"RFC11.htm\">Buscar</button> ");
			out.println("                                            </div>");
			out.println("                                        </div>");
			out.println("                                        </input>");
			out.println("                                        <div class=\"form-group\">");
			out.println("                                            <div id=\"texto\">Pedidos:</div>");
			out.println("                                            <table id=\"myTable\" cellspacing=\"10\" cellpadding=\"10\" border=\"4\"> ");
			out.println("                                                 <tr> ");
			out.println("                                                    <td align=\"center\">ID</td> ");
			out.println("                                                    <td align=\"center\">ID Cliente</td> ");
			out.println("                                                    <td align=\"center\">Estado</td> ");
			out.println("                                                    <td align=\"center\">Fecha Esperada</td> ");
			out.println("                                                    <td align=\"center\">Fecha Entrega</td> ");
			out.println("                                                    <td align=\"center\">Fecha Cancelado</td> ");
			out.println("                                                 </tr> ");
			for (int i=0; i< resp.size(); i++)
			{
				out.println("                                                 <tr> ");
				out.println("                                                    <td align=\"center\">"+resp.get(i).getIdPedido()+"</td> ");
				out.println("                                                    <td align=\"center\">"+resp.get(i).getId_cliente()+"</td> ");
				out.println("                                                    <td align=\"center\">"+resp.get(i).getEstado()+"</td> ");
				out.println("                                                    <td align=\"center\">"+resp.get(i).getFechaEsperada()+"</td> ");
				if(resp.get(i).getFechaEntrega().equals(""))
				out.println("                                                    <td align=\"center\">-</td> ");
				else
				out.println("                                                    <td align=\"center\">"+resp.get(i).getFechaEntrega()+"</td> ");
				if(resp.get(i).getFechaCancelado().equals(""))
					out.println("                                                    <td align=\"center\">-</td> ");
				else
					out.println("                                                    <td align=\"center\">"+resp.get(i).getFechaCancelado()+"</td> ");
				out.println("                                                 </tr> ");
			}
			out.println("                                            </table>");
			out.println("                					</form>");
			out.println("                                    <form role=\"form\">	");
			out.println("                                            <div class=\"dataTables_paginate paging_simple_numbers\" id=\"dataTables-example_paginate\">");
			out.println("                                                <ul class=\"pagination\">");
			out.println("    	                               <input type=\"hidden\" name=\"estado\" value=\"anterior\"/>");
			out.println("                                                        <button class=\"button\" onclick=\"RFC11.htm\">Previous</a>");
			out.println("                                        </input>");
			out.println("                					</form>");
			out.println("                                    <form role=\"form\">	");
			out.println("    	                               <input type=\"hidden\" name=\"estado\" value=\"siguiente\"/>");
			out.println("                                                        <button class=\"button\" onclick=\"RFC11.htm\">Next</button>");
			out.println("                                        </input>");
			out.println("                                                </ul>");
			out.println("                                            </div>");
			out.println("                					</form>");
			out.println("                         		</div>");
			out.println("               				</div>");
			out.println("               			</div>");
			out.println("               		</div>");
			out.println("               		</div>");
			out.println("               		</div>");
			out.println("           		</section>");
		}
		else if(estado.equals("siguiente"))
		{
			prodAndes.getInstance().siguientePaginacion();
			ArrayList<PedidoCliente> resp=prodAndes.getInstance().getRFC11(tipo, id);
			out.println("				<section id=\"intro\" class=\"container\" style=\"alingment:center;\">");
			out.println("					<div class=\"panel-body\">");
			out.println("					<div class=\"row\">");
			out.println("                <div class=\"col-lg-12\">");
			out.println("                    <div class=\"panel panel-default\">");
			out.println("                            <div class=\"row\">");
			out.println("                                <div class=\"col-lg-6\">");
			out.println("                                    <form role=\"form\">	");
			out.println("    	                               <input type=\"hidden\" name=\"estado\" value=\"buscar\"/>");
			out.println("                                        <div class=\"form-group\">");
			out.println("                                            <select name=\"tipo\">");
			out.println("                                                <option value=\""+MATERIA_PRIMA+"\">Materia Prima</option>");
			out.println("                                                <option value=\""+COMPONENTE+"\">Componente</option>");
			out.println("                                            </select>");
			out.println("                                        </div>");
			out.println("                                        <div class=\"form-group\">");
			out.println("                                            <div id=\"texto\">Identificador:</div>");
			out.println("                                            <input type=\"number\" name=\"id\"></input>");
			out.println("                                            <div class=\"button\">");
			out.println("                                                <button class=\"button\" onclick=\"RFC11.htm\">Buscar</button> ");
			out.println("                                            </div>");
			out.println("                                        </div>");
			out.println("                                        </input>");
			out.println("                                        <div class=\"form-group\">");
			out.println("                                            <div id=\"texto\">Pedidos:</div>");
			out.println("                                            <table id=\"myTable\" cellspacing=\"10\" cellpadding=\"10\" border=\"4\"> ");
			out.println("                                                 <tr> ");
			out.println("                                                    <td align=\"center\">ID</td> ");
			out.println("                                                    <td align=\"center\">ID Cliente</td> ");
			out.println("                                                    <td align=\"center\">Estado</td> ");
			out.println("                                                    <td align=\"center\">Fecha Esperada</td> ");
			out.println("                                                    <td align=\"center\">Fecha Entrega</td> ");
			out.println("                                                    <td align=\"center\">Fecha Cancelado</td> ");
			out.println("                                                 </tr> ");
			for (int i=0; i< resp.size(); i++)
			{
				out.println("                                                 <tr> ");
				out.println("                                                    <td align=\"center\">"+resp.get(i).getIdPedido()+"</td> ");
				out.println("                                                    <td align=\"center\">"+resp.get(i).getId_cliente()+"</td> ");
				out.println("                                                    <td align=\"center\">"+resp.get(i).getEstado()+"</td> ");
				out.println("                                                    <td align=\"center\">"+resp.get(i).getFechaEsperada()+"</td> ");
				if(resp.get(i).getFechaEntrega().equals(""))
				out.println("                                                    <td align=\"center\">-</td> ");
				else
				out.println("                                                    <td align=\"center\">"+resp.get(i).getFechaEntrega()+"</td> ");
				if(resp.get(i).getFechaCancelado().equals(""))
					out.println("                                                    <td align=\"center\">-</td> ");
				else
					out.println("                                                    <td align=\"center\">"+resp.get(i).getFechaCancelado()+"</td> ");
				out.println("                                                 </tr> ");
			}
			out.println("                                            </table>");
			out.println("                					</form>");
			out.println("                                    <form role=\"form\">	");
			out.println("                                            <div class=\"dataTables_paginate paging_simple_numbers\" id=\"dataTables-example_paginate\">");
			out.println("                                                <ul class=\"pagination\">");
			out.println("    	                               <input type=\"hidden\" name=\"estado\" value=\"anterior\"/>");
			out.println("                                                        <button class=\"button\" onclick=\"RFC11.htm\">Previous</a>");
			out.println("                                        </input>");
			out.println("                					</form>");
			out.println("                                    <form role=\"form\">	");
			out.println("    	                               <input type=\"hidden\" name=\"estado\" value=\"siguiente\"/>");
			out.println("                                                        <button class=\"button\" onclick=\"RFC11.htm\">Next</button>");
			out.println("                                        </input>");
			out.println("                                                </ul>");
			out.println("                                            </div>");
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
			prodAndes.getInstance().anteriorPaginacion();
			ArrayList<PedidoCliente> resp=prodAndes.getInstance().getRFC10(tipo, id);
			out.println("				<section id=\"intro\" class=\"container\" style=\"alingment:center;\">");
			out.println("					<div class=\"panel-body\">");
			out.println("					<div class=\"row\">");
			out.println("                <div class=\"col-lg-12\">");
			out.println("                    <div class=\"panel panel-default\">");
			out.println("                            <div class=\"row\">");
			out.println("                                <div class=\"col-lg-6\">");
			out.println("                                    <form role=\"form\">	");
			out.println("    	                               <input type=\"hidden\" name=\"estado\" value=\"buscar\"/>");
			out.println("                                        <div class=\"form-group\">");
			out.println("                                            <select name=\"tipo\">");
			out.println("                                                <option value=\""+MATERIA_PRIMA+"\">Materia Prima</option>");
			out.println("                                                <option value=\""+COMPONENTE+"\">Componente</option>");
			out.println("                                            </select>");
			out.println("                                        </div>");
			out.println("                                        <div class=\"form-group\">");
			out.println("                                            <div id=\"texto\">Identificador:</div>");
			out.println("                                            <input type=\"number\" name=\"id\"></input>");
			out.println("                                            <div class=\"button\">");
			out.println("                                                <button class=\"button\" onclick=\"RFC11.htm\">Buscar</button> ");
			out.println("                                            </div>");
			out.println("                                        </div>");
			out.println("                                        </input>");
			out.println("                                        <div class=\"form-group\">");
			out.println("                                            <div id=\"texto\">Pedidos:</div>");
			out.println("                                            <table id=\"myTable\" cellspacing=\"10\" cellpadding=\"10\" border=\"4\"> ");
			out.println("                                                 <tr> ");
			out.println("                                                    <td align=\"center\">ID</td> ");
			out.println("                                                    <td align=\"center\">ID Cliente</td> ");
			out.println("                                                    <td align=\"center\">Estado</td> ");
			out.println("                                                    <td align=\"center\">Fecha Esperada</td> ");
			out.println("                                                    <td align=\"center\">Fecha Entrega</td> ");
			out.println("                                                    <td align=\"center\">Fecha Cancelado</td> ");
			out.println("                                                 </tr> ");
			for (int i=0; i< resp.size(); i++)
			{
				out.println("                                                 <tr> ");
				out.println("                                                    <td align=\"center\">"+resp.get(i).getIdPedido()+"</td> ");
				out.println("                                                    <td align=\"center\">"+resp.get(i).getId_cliente()+"</td> ");
				out.println("                                                    <td align=\"center\">"+resp.get(i).getEstado()+"</td> ");
				out.println("                                                    <td align=\"center\">"+resp.get(i).getFechaEsperada()+"</td> ");
				if(resp.get(i).getFechaEntrega().equals(""))
				out.println("                                                    <td align=\"center\">-</td> ");
				else
				out.println("                                                    <td align=\"center\">"+resp.get(i).getFechaEntrega()+"</td> ");
				if(resp.get(i).getFechaCancelado().equals(""))
					out.println("                                                    <td align=\"center\">-</td> ");
				else
					out.println("                                                    <td align=\"center\">"+resp.get(i).getFechaCancelado()+"</td> ");
				out.println("                                                 </tr> ");
			}
			out.println("                                            </table>");
			out.println("                					</form>");
			out.println("                                    <form role=\"form\">	");
			out.println("                                            <div class=\"dataTables_paginate paging_simple_numbers\" id=\"dataTables-example_paginate\">");
			out.println("                                                <ul class=\"pagination\">");
			out.println("    	                               <input type=\"hidden\" name=\"estado\" value=\"anterior\"/>");
			out.println("                                                        <button class=\"button\" onclick=\"RFC11.htm\">Previous</a>");
			out.println("                                        </input>");
			out.println("                					</form>");
			out.println("                                    <form role=\"form\">	");
			out.println("    	                               <input type=\"hidden\" name=\"estado\" value=\"siguiente\"/>");
			out.println("                                                        <button class=\"button\" onclick=\"RFC11.htm\">Next</button>");
			out.println("                                        </input>");
			out.println("                                                </ul>");
			out.println("                                            </div>");
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

