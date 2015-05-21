package prodAndes.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import prodAndes.fachado.prodAndes;

public class ServletRF11 extends ServletTemplate{
	// -----------------------------------------------------------------
    // Metodos
    // -----------------------------------------------------------------

    /**
	 * serial
	 */
	private static final long serialVersionUID = 1L;

    
    public void escribirContenido( HttpServletRequest request, HttpServletResponse response ) throws IOException
    {
    	if(!prodAndes.getInstance().empezoRF11())
    	{
    		prodAndes.getInstance().empezarRF11();
    		PrintWriter out = response.getWriter( );
    		out.println("				<section id=\"intro\" class=\"container\" style=\"alingment:center;\">");
    		out.println("					<div class=\"panel-body\">");
    		out.println("					<div class=\"row\">");
    		out.println("                   <div class=\"col-lg-12\">");
    		out.println("                   <div class=\"panel panel-default\">");
    		out.println("                            <div class=\"row\">");
    		out.println("                                <div class=\"col-lg-6\">");
    		out.println("                                    <form role=\"form\">	");
    		out.println("                                          <div class=\"form-group\">");
    		out.println("                                    	   		<label>id pedido a proveedor:</label>");
    		out.println("                               		   		<input type=\"number\" name=\"idPedido\">");
    		out.println("        						           </div>");
    		out.println("                                		   <div class=\"form-group\">");
    		out.println("                                               <button onclick=>Registrar Llegada</button>");
    		out.println("                                          </div>");
    		out.println("                				     </form>");
    		out.println("                         		 </div>");
    		out.println("               			</div>");
    		out.println("               	</div>");
    		out.println("               	</div>");
    		out.println("               	</div>");
    		out.println("               	</div>");
    		out.println("           	</section>");
    	}
    	else
    	{
    		boolean resp=false;
    		String error="otro ";
			try 
			{
				resp = prodAndes.getInstance().RF11(Integer.parseInt(request.getParameter("idPedido")));
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
    		out.println("                                          <div class=\"form-group\">");
    		out.println("                                    	   		<label>id pedido a proveedor:</label>");
    		out.println("                               		   		<input type=\"number\" name=\"idPedido\">");
    		out.println("        						           </div>");
    		out.println("                               <div class=\"form-group\">");
    		out.println("                               <button onclick=>Registrar Llegada</button>");
    		out.println("                               </div>");
	    	out.println("                                        <div class=\"form-group\">");
	    	if(resp)
	    	{
	    		out.println("                                            <label>Llegada de material registrada correctamente</label>");
	    	}
	    	else
	    	{
	    		out.println("                                            <label>Error: Llegada de material no registrada:" + error + "</label>");
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
