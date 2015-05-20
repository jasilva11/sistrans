package prodAndes.servlets;

import java.io.IOException;
import java.io.PrintWriter;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




public class ServletRFC12 extends ServletTemplate {
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
		out.println("				<section id=\"intro\" class=\"container\" style=\"alingment:center;\">");
		out.println("					<div class=\"panel-body\">");
		out.println("					<div class=\"row\">");
		out.println("                <div class=\"col-lg-12\">");
		out.println("                    <div class=\"panel panel-default\">");
		out.println("                            <div class=\"row\">");
		out.println("                                <div class=\"col-lg-6\">");
		out.println("                                    <form role=\"form\">	");
		out.println("                                        <div class=\"form-group\">                                            <div id=\"texto\">Intervalo de fechas:</div>");
		out.println("                                            <input type=\"date\" name=\"fechaInicial\">");
		out.println("                                        </div>                                            <input type=\"date\" name=\"fechaFinal\"></input>");
		out.println("                                            <div class=\"button\">");
		out.println("                                                <div onclick=\"RFC12.htm\">Buscar</div> ");
		out.println("                                            </div>");
		out.println("                                        </div>");
		out.println("                                        <div class=\"form-group\">");
		out.println("                                            <div id=\"texto\">Etapas de produccion:</div>");
		out.println("                                            <table id=\"myTable\" cellspacing=\"10\" cellpadding=\"10\" border=\"4\"> ");
		out.println("                                                 <tr> ");
		out.println("                                                    <td align=\"center\">ID</td> ");
		out.println("                                                    <td align=\"center\">Producto</td> ");
		out.println("<td align=\"center\">Nombre</td> ");
		out.println("<td align=\"center\">Tiempo inicio ejecucion</td> ");
		out.println("<td align=\"center\">Tiempo fin ejecucion </td> ");
		out.println("<td align=\"center\">Numero de secuencia</td> ");
		out.println("<td align=\"center\">Completada</td> ");
		out.println("");
		out.println("");
		out.println("                                                 </tr>                                                         </table> ");
		out.println("                                                    </td>                                                  ");
		out.println("                                                </tr>                                            </table>");
		out.println("                                            <div class=\"dataTables_paginate paging_simple_numbers\" id=\"dataTables-example_paginate\">");
		out.println("                                                <ul class=\"pagination\">");
		out.println("                                                    <li class=\"paginate_button previous\" aria-controls=\"dataTables-example\" tabindex=\"0\" id=\"dataTables-example_previous\">");
		out.println("                                                        <a href=\"#\">Previous</a>");
		out.println("                                                    </li>");
		out.println("                                                    <li class=\"paginate_button next disabled\" aria-controls=\"dataTables-example\" tabindex=\"0\" id=\"dataTables-example_next\">");
		out.println("                                                        <a href=\"#\">Next</a>");
		out.println("                                                    </li>");
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

