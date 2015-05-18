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
import prodAndes.vos.EtapaProduccion;



public class ServletRFC8 extends ServletTemplate {
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
			out.println("    	                                 <input type=\"hidden\" name=\"estado\" value=\"filtrar\"/>");
			out.println("                                          	 <label>Criterio de busqueda:</label>");
			out.println("                                            <select name=\"filtro\">");
			out.println("                                                <option value=\"ID_MATERIAL\">Por material</option>");
			out.println("                                                <option value=\"ID_PEDIDO\">Por pedido</option>");
			out.println("                                                <option value=\"CANTIDAD_MATERIAL\">Por cantidad</option>>");
			out.println("                                            </select>");
			out.println("                                        </div>");
			out.println("                                            <input name=\"info\"></input>");
			out.println("                                            <div class=\"button\">");
			out.println("                                                <button class=\"button\" onclick=\"RFC7.htm\">Consultar</button> ");
			out.println("                                            </div>");
			out.println("                                        </div>");
			out.println("                                        <div class=\"form-group\">");
			out.println("                                          <label>Tipo de material (Criterio de busqueda por material):</label>");
			out.println("                                            <select name=\"tipo\">");
			out.println("                                                <option value=\"ID_MATERIA\">materia prima</option>");
			out.println("                                                <option value=\"ID_COMPONENTE\">componente</option>");
			out.println("                                            </select>");
			out.println("                                        </div>");
			out.println("                                        <div class=\"col-lg-6\">");
			out.println("                                          	 <label>tiempo Minimo ejecucion:</label>");
			out.println("                                            <input type=\"Date\" name=\"tiempoMin\"></input>");
			out.println("                                        </div>");
			out.println("                                        <div class=\"form-group\">");
			out.println("                                          <label>tiempo Maximo ejecucion:</label>");
			out.println("                                            <input type=\"Date\" name=\"tiempoMax\"></input>");
			out.println("                                        </div>");
			out.println("                                        <div class=\"form-group\">");
			out.println("                                          <label>Cant min material (Criterio de busqueda por cantidad):</label>");
			out.println("                                            <input type=\"number\" name=\"cantMin\"></input>");
			out.println("                                        </div>");
			out.println("                                        <div class=\"form-group\">");
			out.println("                                          <label>Cant max material (Criterio de busqueda por cantidad):</label>");
			out.println("                                            <input type=\"number\" name=\"cantMax\"></input>");
			out.println("                                        </div>");
			out.println("                                        </input>");
			out.println("                                        <div class=\"form-group\">");
			out.println("                                            <div id=\"texto\">Etapas de produccion:</div>");
			out.println("                                            <table id=\"myTable\" cellspacing=\"10\" cellpadding=\"10\" border=\"4\"> ");
			out.println("                                                 <tr> ");
			out.println("                                                    <td align=\"center\">ID</td> ");
			out.println("                                                    <td align=\"center\">Nombre	</td> ");
			out.println("                                                    <td align=\"center\">ID proceso	</td> ");
			out.println("                                                    <td align=\"center\">ID producto stock		</td> ");
			out.println("                                                    <td align=\"center\">ID producto stock necesitado	 </td> ");
			out.println("                                                    <td align=\"center\">Numero de secuencia</td> ");
			out.println("                                                    <td align=\"center\">Tiempo inicio ejecucion</td> ");
			out.println("                                                    <td align=\"center\">Tiempo fin  ejecucion</td> ");
			out.println("                                                    <td align=\"center\">Completada</td> ");
			out.println("                                                 </tr> ");
			out.println("                                                <tr>");
			out.println("                                                    <td></td>");
			out.println("                                                    <td></td>");
			out.println("                                                    <td></td>");
			out.println("                                                    <td></td>");
			out.println("                                                    <td></td>");
			out.println("                                                </tr>");
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
			final String OLD_FORMAT = "yyyy-MM-dd";
			final String NEW_FORMAT = "dd/MM/yyyy";
			String filtro = request.getParameter("filtro");
			String info = request.getParameter("info");
			String tiempoMin = request.getParameter("tiempoMin");
			String tiempoMax = request.getParameter("tiempoMax");
			String tipoMaterial=request.getParameter("tipo");

			SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
			Date d=null;
			Date d0=null;
			try {
				d =  sdf.parse(tiempoMin);
				d0 =  sdf.parse(tiempoMax);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				out.println("                                          <label>No se pudieron agregar las etapas buscadas: " + e.getMessage() + "</label>");
			}
			sdf.applyPattern(NEW_FORMAT);
			tiempoMin = sdf.format(d);
			tiempoMax = sdf.format(d0);
			int cantMinMaterial=0;
			int cantMaxMaterial=0;
			if(filtro.equals("CANTIDAD_MATERIAL"))
			{
				cantMinMaterial= Integer.parseInt(request.getParameter("cantMin"));
				cantMaxMaterial= Integer.parseInt(request.getParameter("cantMax"));
			}
			
			out.println("				<section id=\"intro\" class=\"container\" style=\"alingment:center;\">");
			out.println("					<div class=\"panel-body\">");
			out.println("					<div class=\"row\">");
			out.println("                <div class=\"col-lg-12\">");
			out.println("                    <div class=\"panel panel-default\">");
			out.println("                            <div class=\"row\">");
			out.println("                                <div class=\"col-lg-6\">");
			out.println("                                    <form role=\"form\">	");
			out.println("                                        <div class=\"form-group\">");
			out.println("    	                                 <input type=\"hidden\" name=\"estado\" value=\"filtrar\"/>");
			out.println("                                          	 <label>Criterio de busqueda:</label>");
			out.println("                                            <select name=\"filtro\">");
			out.println("                                                <option value=\"ID_MATERIAL\">Por material</option>");
			out.println("                                                <option value=\"ID_PEDIDO\">Por pedido</option>");
			out.println("                                                <option value=\"CANTIDAD_MATERIAL\">Por cantidad</option>>");
			out.println("                                            </select>");
			out.println("                                        </div>");
			out.println("                                            <input name=\"info\"></input>");
			out.println("                                            <div class=\"button\">");
			out.println("                                                <button class=\"button\" onclick=\"RFC7.htm\">Consultar</button> ");
			out.println("                                            </div>");
			out.println("                                        </div>");
			out.println("                                        <div class=\"form-group\">");
			out.println("                                          <label>Tipo de material (Criterio de busqueda por material):</label>");
			out.println("                                            <select name=\"tipo\">");
			out.println("                                                <option value=\"ID_MATERIA\">materia prima</option>");
			out.println("                                                <option value=\"ID_COMPONENTE\">componente</option>");
			out.println("                                            </select>");
			out.println("                                        </div>");
			out.println("                                        <div class=\"col-lg-6\">");
			out.println("                                          	 <label>tiempo Minimo ejecucion:</label>");
			out.println("                                            <input type=\"Date\" name=\"tiempoMin\"></input>");
			out.println("                                        </div>");
			out.println("                                        <div class=\"form-group\">");
			out.println("                                          <label>tiempo Maximo ejecucion:</label>");
			out.println("                                            <input type=\"Date\" name=\"tiempoMax\"></input>");
			out.println("                                        </div>");
			out.println("                                        <div class=\"form-group\">");
			out.println("                                          <label>Cant min material (Criterio de busqueda por cantidad):</label>");
			out.println("                                            <input type=\"number\" name=\"cantMin\"></input>");
			out.println("                                        </div>");
			out.println("                                        <div class=\"form-group\">");
			out.println("                                          <label>Cant max material (Criterio de busqueda por cantidad):</label>");
			out.println("                                            <input type=\"number\" name=\"cantMax\"></input>");
			out.println("                                        </div>");
			out.println("                                        </input>");
			out.println("                                        <div class=\"form-group\">");
			out.println("                                            <div id=\"texto\">Etapas de produccion:</div>");
			out.println("                                            <table id=\"myTable\" cellspacing=\"10\" cellpadding=\"10\" border=\"4\"> ");
			out.println("                                                 <tr> ");
			out.println("                                                    <td align=\"center\">ID</td> ");
			out.println("                                                    <td align=\"center\">Nombre	</td> ");
			out.println("                                                    <td align=\"center\">ID proceso	</td> ");
			out.println("                                                    <td align=\"center\">ID producto stock		</td> ");
			out.println("                                                    <td align=\"center\">ID producto stock necesitado	 </td> ");
			out.println("                                                    <td align=\"center\">Numero de secuencia</td> ");
			out.println("                                                    <td align=\"center\">tiempo inicio ejecucion</td> ");
			out.println("                                                    <td align=\"center\">tiempo fin  ejecucion</td> ");
			out.println("                                                    <td align=\"center\">completada</td> ");
			out.println("                                                 </tr> ");
			out.println("                                                <tr>");
			out.println("                                                    <td></td>");
			out.println("                                                    <td></td>");
			out.println("                                                    <td></td>");
			out.println("                                                    <td></td>");
			out.println("                                                    <td></td>");
			out.println("                                                </tr>");

			ArrayList<EtapaProduccion> etapas;
			try {
				etapas = prodAndes.getInstance().RFC8(filtro,info,tiempoMin, tiempoMax, tipoMaterial, cantMinMaterial,cantMaxMaterial);
				for(int i=0; i< etapas.size();i++)
				{
					out.println("				                        <tr>");
					out.println("					                         <th>"+etapas.get(i).darID()+"</th>");
					out.println("                                            <th>"+etapas.get(i).darNombre()+"</th>  ");
					out.println("                                            <th>"+etapas.get(i).darIdProceso()+"</th>  ");
					out.println("                                            <th>"+etapas.get(i).darIdProdStock()+"</th>  ");
					out.println("                                            <th>"+etapas.get(i).darIdProdStockNecesitado()+"</th>  ");
					out.println("                                            <th>"+etapas.get(i).darNumeroSecuencia()+"</th>  ");
					out.println("                                            <th>"+etapas.get(i).darTiempoInicioEjecucion()+"</th>  ");
					out.println("                                            <th>"+etapas.get(i).darTiempoFinEjecucion()+"</th>  ");
					out.println("                                            <th>"+etapas.get(i).Completada()+"</th>  ");
					out.println("				                        </tr>");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				out.println("                                          <label>No se pudieron agregar las etapas buscadas: " + e.getMessage() + "</label>");
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

