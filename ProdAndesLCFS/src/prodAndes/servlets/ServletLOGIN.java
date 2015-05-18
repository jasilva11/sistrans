package prodAndes.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import prodAndes.fachado.prodAndes;


public class ServletLOGIN extends ServletTemplate {
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
		String h=request.getParameter("motivo");
		if(h==null)
		{
			out.println("				<section id=\"intro\" class=\"container\" style=\"alingment:center;\">");
			out.println("					<div class=\"panel-body\">");
			out.println("					<div class=\"row\">");
			out.println("                <div class=\"col-lg-12\">");
			out.println("                    <div class=\"panel panel-default\">");
			out.println("                            <div class=\"row\">");
			out.println("                                <div class=\"col-lg-6\">");
			out.println("                                    <form role=\"form\">");
			out.println("    	                               <input type=\"hidden\" name=\"motivo\" value=\"sigue\">");
			out.println("                                    		<div class=\"form-group\">");
			out.println("                                        <div id=\"texto\">Codigo: </div>");
			out.println("                                        	<input name=\"id\" type=\"number\" value=\"0\"></input>");
			out.println("    	                                    </div>                         ");
			out.println("                                    <div class=\"form-group\">");
			out.println("                                        <button onclick=\"LOGIN.htm\">Ingresar</button>");
			out.println("                                    </div>");
			out.println("                					</form>");
			out.println("                         		</div>");
			out.println("               				</div>");
			out.println("               			</div>");
			out.println("               		</div>");
			out.println("               		</div>");
			out.println("               		</div>");
			out.println("           		</section>");
			out.println("			");
		}
		else
		{
			int id=Integer.valueOf(request.getParameter("id"));
			String resp= prodAndes.getInstance().logIN(id);
			if(!resp.equals(""))
			{
			out.println("				<section id=\"intro\" class=\"container\" style=\"alingment:center;\">");
			out.println("					<div class=\"panel-body\">");
			out.println("					<div class=\"row\">");
			out.println("                <div class=\"col-lg-12\">");
			out.println("                    <div class=\"panel panel-default\">");
			out.println("                            <div class=\"row\">");
			out.println("                                <div class=\"col-lg-6\">");
			out.println("                                    <form role=\"form\">");
			out.println("                                        <div id=\"texto\">Usted ha ingresado como:"+resp+" </div>");
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
				out.println("				<section id=\"intro\" class=\"container\" style=\"alingment:center;\">");
				out.println("					<div class=\"panel-body\">");
				out.println("					<div class=\"row\">");
				out.println("                <div class=\"col-lg-12\">");
				out.println("                    <div class=\"panel panel-default\">");
				out.println("                            <div class=\"row\">");
				out.println("                                <div class=\"col-lg-6\">");
				out.println("                                    <form role=\"form\">");
				out.println("                                        <div id=\"texto\">No se logró ingresar correctamente </div>");
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

