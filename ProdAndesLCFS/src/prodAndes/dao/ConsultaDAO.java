package prodAndes.dao;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import prodAndes.vos.EstacionProduccion;
import prodAndes.vos.EtapaProduccion;
import prodAndes.vos.MateriaPrima;
import prodAndes.vos.Materia_Componente;
import prodAndes.vos.PedidoCliente;
import prodAndes.vos.Producto;
import prodAndes.vos.ProductoPedido;



public class ConsultaDAO
{
		//----------------------------------------------------
		//Constantes
		//----------------------------------------------------
		/**
		 * ruta donde se encuentra el archivo de conexi�n.
		 */
		private static final String ARCHIVO_CONEXION = "/conexion.properties";
		
	private final static String ADMIN = "Admin";
		
		private final static String CLIENTE = "Cliente";
		
		private final static String PROVEEDOR= "Proveedor";

		private final static String SUPERVISOR= "Supervisor";
		
		private final static String OPERARIO= "Operario";
		
		private final static String GERENTE= "Gerente";
		
		private final static String MATERIA_PRIMA= "Materia prima";
		
		private final static String COMPONENTE= "Componente";
		//----------------------------------------------------
		//Atributos
		//----------------------------------------------------
		/**
		 * conexion con la base de datos
		 */
		public Connection conexion;
		
		private String tipoLogin;
		/**
		 * nombre del usuario para conectarse a la base de datos.
		 */
		private String usuario;
		
		/**
		 * clave de conexi�n a la base de datos.
		 */
		private String clave;
		
		/**
		 * URL al cual se debe conectar para acceder a la base de datos.
		 */
		private String cadenaConexion;
		
		/**
		 * Carrito temporal de compras
		 */
		private ArrayList<ProductoPedido> RF12;
		

		private int paginacion;
		
		
		public ArrayList<ProductoPedido> getRF12() {
			return RF12;
		}

		/**
		 * constructor de la clase. No inicializa ningun atributo.
		 */
		public ConsultaDAO() 
		{		
			
		}
		
		// -------------------------------------------------
	    // M�todos
	    // -------------------------------------------------


		/**
		 * obtiene ls datos necesarios para establecer una conexion
		 * Los datos se obtienen a partir de un archivo properties.
		 * @param path ruta donde se encuentra el archivo properties.
		 */
		public void inicializar()
		{
				cadenaConexion = "jdbc:oracle:thin:@prod.oracle.virtual.uniandes.edu.co:1531:prod";
				usuario = "ISIS2304191510";	
				clave = "dareavying";	
		}

		/**
		 * M�todo que se encarga de crear la conexi�n con el Driver Manager
		 * a partir de los parametros recibidos.
		 * @param url direccion url de la base de datos a la cual se desea conectar
		 * @param usuario nombre del usuario que se va a conectar a la base de datos
		 * @param clave clave de acceso a la base de datos
		 * @throws SQLException si ocurre un error generando la conexi�n con la base de datos.
		 */
	    private void establecerConexion(String url, String usuario, String clave) throws SQLException
	    {
	    	try
	        {
				conexion = DriverManager.getConnection(url,usuario,clave);
	        }
	        catch( SQLException exception )
	        {
	            throw new SQLException( "ERROR: Consulta DAO obteniendo una conexion." );
	        }
	    }
	    
	    /**
	 	 *Cierra la conexi�n activa a la base de datos. Adem�s, con=null.
	     * @param con objeto de conexi�n a la base de datos
	     * @throws SistemaCinesException Si se presentan errores de conexi�n
	     */
	    public void closeConnection(Connection connection) throws Exception {        
			try {
				connection.close();
				connection = null;
			} catch (SQLException exception) {
				throw new Exception("ERROR: Consulta DAO: closeConnection() = cerrando una conexion.");
			}
	    } 
	    
	    // ---------------------------------------------------
	    // M�todos asociados a los casos de uso: Consulta
	    // ---------------------------------------------------
	    public void iniciarPaginacion()
	    {
	    	paginacion=1;
	    }
	    
	    public int siguientePaginacion()
	    {
	    	paginacion +=20;
	    	return paginacion;
	    }
	    
	    public void anteriorPaginacion()
	    {
	    	paginacion -= 20;
	    }
	    
	    public String logIN(int id) throws Exception
		{
	    	PreparedStatement prepStmt = null;
			try
			{
				establecerConexion(cadenaConexion, usuario, clave);
				prepStmt = conexion.prepareStatement("select * from clientes where ID_REPRESENTANTE="+id);			
				ResultSet rs = prepStmt.executeQuery();
				rs.next();
				if(rs.getRow()==0)
				{
					PreparedStatement prepStmt2 = conexion.prepareStatement("select * from administradores where ID="+id);			
					ResultSet rs2 = prepStmt2.executeQuery();
					rs2.next();
					if(rs2.getRow()==0)
					{
						PreparedStatement prepStmt3 = conexion.prepareStatement("select * from gerentes where ID="+id);			
						ResultSet rs3 = prepStmt3.executeQuery();
						rs3.next();
						if(rs3.getRow()==0)
						{
							PreparedStatement prepStmt4 = conexion.prepareStatement("select * from operarios where IDENTIFICACION="+id);			
							ResultSet rs4 = prepStmt4.executeQuery();
							rs4.next();
							if(rs4.getRow()==0)
							{
								PreparedStatement prepStmt5 = conexion.prepareStatement("select * from proveedores where ID_REPRESENTANTE="+id);			
								ResultSet rs5 = prepStmt5.executeQuery();
								rs5.next();
								if(rs5.getRow()==0)
								{
									PreparedStatement prepStmt6 = conexion.prepareStatement("select * from supervisor where ID="+id);			
									ResultSet rs6 = prepStmt6.executeQuery();
									rs6.next();
									if(rs6.getRow()==0)
									{
										return "";
									}
									else
									{
										tipoLogin=SUPERVISOR;
									}
								}
								else
								{
									tipoLogin=PROVEEDOR;
								}
							}
							else
							{
								tipoLogin=OPERARIO;
							}
							
						}
						else
						{
							tipoLogin=GERENTE;
						}
					}
					else
					{
						tipoLogin=ADMIN;
					}
				}
				else
				{
					tipoLogin= CLIENTE;
				}
			}
			catch (SQLException e) 
			{
				e.printStackTrace();
				throw new Exception("ERROR = ConsultaDAO: loadRowsBy(..) Agregando parametros y executando el statement!!!");
			}
			finally 
			{
				if (prepStmt != null) 
				{
					try 
					{
						prepStmt.close();
					}
					catch (SQLException exception) 
					{
						
						throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexi�n.");
					}
				}
				closeConnection(conexion);
			}		
			return tipoLogin;
		}
	    public ArrayList<Producto> darProductos() throws Exception
	    {
	    	PreparedStatement prepStmt = null;
	    	
	    	ArrayList<Producto> productos = new ArrayList<Producto>();
	    	
			try {
				establecerConexion(cadenaConexion, usuario, clave);
				prepStmt = conexion.prepareStatement("SELECT * FROM PRODUCTO");
				
				ResultSet rs = prepStmt.executeQuery();
				
				while(rs.next()){
					
					String nombre = rs.getString("NOMBRE");
					int id = rs.getInt("ID_PRODUCTO");
					int costoVenta = rs.getInt("COSTO_VENTA");
					Producto producto= new Producto(id, nombre, costoVenta);
					productos.add(producto);
							
				}
			
			} catch (SQLException e) {
				e.printStackTrace();
				throw new Exception("ERROR = ConsultaDAO: loadRowsBy(..) Agregando parametros y executando el statement!!!");
			}finally 
			{
				if (prepStmt != null) 
				{
					try {
						prepStmt.close();
					} catch (SQLException exception) {
						
						throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexi�n.");
					}
				}
				closeConnection(conexion);
			}		
			return productos;
	    }
	    
	    /**
	     * 
	     * @param idUsuarioLogueado 
	     * @return
	     * @throws Exception 
	     */
		public ArrayList<PedidoCliente> darPedidosClientePorEntregar(int idUsuarioLogueado) throws Exception 
		{
				PreparedStatement prepStmt = null;
				PreparedStatement prepStmt0 = null;
				ArrayList<PedidoCliente> PedidoClientes = new ArrayList<PedidoCliente>();
	    	
			try {
				establecerConexion(cadenaConexion, usuario, clave);
				prepStmt = conexion.prepareStatement("SELECT * FROM Pedido_Cliente WHERE estado = 'T' AND ID_CLIENTE = " + idUsuarioLogueado);
				ResultSet rs = prepStmt.executeQuery();
				while(rs.next()){
					
					int id = rs.getInt("ID_PEDIDO");
					int id_cliente = rs.getInt("ID_ClIENTE");
					String fechaEntrega = rs.getString("FECHA_ENTREGA");
					char estado = rs.getString("ESTADO").charAt(0);
					String fechaEsperada = rs.getString("FECHA_ESPERADA");
					prepStmt0 = conexion.prepareStatement("SELECT PRODUCTO.ID_PRODUCTO, VOLUMEN, NOMBRE FROM PRODUCTOSPEDIDOS INNER JOIN PRODUCTO ON PRODUCTO.ID_PRODUCTO= PRODUCTOSPEDIDOS.ID_PRODUCTO WHERE ID_PEDIDO = " + id );
					ResultSet rs0= prepStmt0.executeQuery();
					ArrayList<ProductoPedido> productos = new ArrayList<ProductoPedido>();
					while(rs0.next())
					{
						ProductoPedido p1 = new ProductoPedido(rs0.getInt("VOLUMEN"), rs0.getInt("ID_PRODUCTO"));
						productos.add(p1);
					}
					PedidoCliente PedidoCliente= new PedidoCliente(id_cliente, estado, fechaEntrega, fechaEsperada, productos, id);
					PedidoClientes.add(PedidoCliente);
							
				}
			
			} catch (SQLException e) {
				e.printStackTrace();
				throw new Exception("ERROR = ConsultaDAO: loadRowsBy(..) Agregando parametros y executando el statement!!!");
			}finally 
			{
				if (prepStmt != null) 
				{
					try {
						prepStmt.close();
					} catch (SQLException exception) {
						
						throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexi�n.");
					}
				}
				closeConnection(conexion);
			}		
			return PedidoClientes;
		}
	    public void inicRF12()
	    {
	    	RF12 = new ArrayList<ProductoPedido>();
	    }
	    
	    public ArrayList<PedidoCliente> darPedidos() throws Exception
	    {
	    	PreparedStatement prep= null;
	    	PreparedStatement prepStmt = null;
	    	
	    	ArrayList<PedidoCliente> pedidos = new ArrayList<PedidoCliente>();
	    	
			try {establecerConexion(cadenaConexion, usuario, clave);
				prep = conexion.prepareStatement("SET TRANSACTION ISOLATION LEVEL READ COMMITTED");
				prep.executeQuery();
				
				prepStmt = conexion.prepareStatement("SELECT * FROM PEDIDO_cliente");
				
				ResultSet rs = prepStmt.executeQuery();
				
				while(rs.next())
				{
					
					int idPedido = rs.getInt("ID_PEDIDO");
					PreparedStatement prepStmt2 = conexion.prepareStatement("SELECT * FROM PRODUCTOSPEDIDOS WHERE ID_PEDIDO="+idPedido);
					ResultSet rs2 = prepStmt2.executeQuery();
					ArrayList<ProductoPedido> productos= new ArrayList<ProductoPedido>();
					while(rs2.next())
					{
						int idProducto = rs2.getInt("ID_PRODUCTO");
						int volumen = rs2.getInt("VOLUMEN");
						
						ProductoPedido act = new ProductoPedido(volumen, idProducto);
						productos.add(act);
					}
					int idCliente = rs.getInt("ID_CLIENTE");
					Date fechaEntrega= rs.getDate("FECHA_ENTREGA");
					
					Date fechaEsperada= rs.getDate("FECHA_ESPERADA");
					System.out.println(fechaEsperada);
					SimpleDateFormat fechaHora = new SimpleDateFormat("dd/MM/YYYY");
					String esperadaConvertida = fechaHora.format(fechaEsperada);
					System.out.println(esperadaConvertida);
					String entregaConvertida="";
					if(fechaEntrega!=null)
					{
						entregaConvertida = fechaHora.format(fechaEntrega);
					}
					String estado = rs.getString("ESTADO");
					char c= estado.charAt(0);

					PedidoCliente r= new PedidoCliente(idCliente, c, entregaConvertida, esperadaConvertida, productos, idPedido);
					pedidos.add(r);
				}
			
			} catch (SQLException e) {
				e.printStackTrace();
				throw new Exception("ERROR = ConsultaDAO: loadRowsBy(..) Agregando parametros y executando el statement!!!");
			}finally 
			{
				if (prepStmt != null) 
				{
					try {
						prepStmt.close();
					} catch (SQLException exception) {
						
						throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexi�n.");
					}
				}
				closeConnection(conexion);
			}		
			return pedidos;
	    }
	    
	    public ArrayList<PedidoCliente> getRFC5(String filtrar, String info) throws Exception
	    {
	    	ArrayList<PedidoCliente> resp = new ArrayList<PedidoCliente>();
	    	
	    	if(filtrar.equals("ID_CLIENTE"))
	    	{
	    		PreparedStatement prep=null;
	    		PreparedStatement prepStmt=null;
	    		try {
					prep = conexion.prepareStatement("SET TRANSACTION ISOLATION LEVEL READ COMMITTED");
					prep.executeQuery();
					establecerConexion(cadenaConexion, usuario, clave);
					prepStmt = conexion.prepareStatement("SELECT * FROM PEDIDO_cliente WHERE ID_CLIENTE="+info);
					
					ResultSet rs = prepStmt.executeQuery();
					
					while(rs.next())
					{
						
						int idPedido = rs.getInt("ID_PEDIDO");
						PreparedStatement prepStmt2 = conexion.prepareStatement("SELECT * FROM PRODUCTOSPEDIDOS WHERE ID_PEDIDO="+idPedido);
						ResultSet rs2 = prepStmt2.executeQuery();
						ArrayList<ProductoPedido> productos= new ArrayList<ProductoPedido>();
						while(rs2.next())
						{
							int idProducto = rs2.getInt("ID_PRODUCTO");
							int volumen = rs2.getInt("VOLUMEN");
							
							ProductoPedido act = new ProductoPedido(volumen, idProducto);
							productos.add(act);
						}
						int idCliente = rs.getInt("ID_CLIENTE");
						Date fechaEntrega= rs.getDate("FECHA_ENTREGA");
						Date fechaEsperada= rs.getDate("FECHA_ESPERADA");
						DateFormat fechaHora = new SimpleDateFormat("dd/MM/YYYY");
						String esperadaConvertida = fechaHora.format(fechaEsperada);
						String entregaConvertida="";
						if(fechaEntrega!=null)
						{
							entregaConvertida = fechaHora.format(fechaEntrega);
						}
						String estado = rs.getString("ESTADO");
						char c= estado.charAt(0);

						PedidoCliente r= new PedidoCliente(idCliente, c, entregaConvertida, esperadaConvertida, productos, idPedido);
						resp.add(r);
					}
				
				} catch (SQLException e) {
					e.printStackTrace();
					throw new Exception("ERROR = ConsultaDAO: loadRowsBy(..) Agregando parametros y executando el statement!!!");
				}finally 
				{
					if (prepStmt != null) 
					{
						try {
							prepStmt.close();
						} catch (SQLException exception) {
							
							throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexi�n.");
						}
					}
					closeConnection(conexion);
				}
	    	}
	    	else if(filtrar.equals("ID_PRODUCTO"))
	    	{
	    		PreparedStatement prepStmt=null;
	    		try {
					establecerConexion(cadenaConexion, usuario, clave);
					prepStmt = conexion.prepareStatement("SELECT * FROM PEDIDO_CLIENTE WHERE ID_PEDIDO IN (SELECT ID_PEDIDO FROM PRODUCTOSPEDIDOS WHERE ID_PRODUCTO="+info+")");
					
					ResultSet rs = prepStmt.executeQuery();
					
					while(rs.next())
					{
						
						int idPedido = rs.getInt("ID_PEDIDO");
						PreparedStatement prepStmt2 = conexion.prepareStatement("SELECT * FROM PRODUCTOSPEDIDOS WHERE ID_PEDIDO="+idPedido);
						ResultSet rs2 = prepStmt2.executeQuery();
						ArrayList<ProductoPedido> productos= new ArrayList<ProductoPedido>();
						while(rs2.next())
						{
							int idProducto = rs2.getInt("ID_PRODUCTO");
							int volumen = rs2.getInt("VOLUMEN");
							
							ProductoPedido act = new ProductoPedido(volumen, idProducto);
							productos.add(act);
						}
						int idCliente = rs.getInt("ID_CLIENTE");
						Date fechaEntrega= rs.getDate("FECHA_ENTREGA");
						Date fechaEsperada= rs.getDate("FECHA_ESPERADA");
						DateFormat fechaHora = new SimpleDateFormat("dd/MM/YYYY");
						String esperadaConvertida = fechaHora.format(fechaEsperada);
						String entregaConvertida="";
						if(fechaEntrega!=null)
						{
							entregaConvertida = fechaHora.format(fechaEntrega);
						}
						String estado = rs.getString("ESTADO");
						char c= estado.charAt(0);

						PedidoCliente r= new PedidoCliente(idCliente, c, entregaConvertida, esperadaConvertida, productos, idPedido);
						resp.add(r);
					}
				
				} catch (SQLException e) {
					e.printStackTrace();
					throw new Exception("ERROR = ConsultaDAO: loadRowsBy(..) Agregando parametros y executando el statement!!!");
				}finally 
				{
					if (prepStmt != null) 
					{
						try {
							prepStmt.close();
						} catch (SQLException exception) {
							
							throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexi�n.");
						}
					}
					closeConnection(conexion);
				}
	    	}
	    	else if(filtrar.equals("ESTADO"))
	    	{
	    		PreparedStatement prepStmt=null;
	    		try {
					establecerConexion(cadenaConexion, usuario, clave);
					prepStmt = conexion.prepareStatement("SELECT * FROM PEDIDO_CLIENTE WHERE ESTADO='"+info+"'");
					
					ResultSet rs = prepStmt.executeQuery();
					
					while(rs.next())
					{
						
						int idPedido = rs.getInt("ID_PEDIDO");
						PreparedStatement prepStmt2 = conexion.prepareStatement("SELECT * FROM PRODUCTOSPEDIDOS WHERE ID_PEDIDO="+idPedido);
						ResultSet rs2 = prepStmt2.executeQuery();
						ArrayList<ProductoPedido> productos= new ArrayList<ProductoPedido>();
						while(rs2.next())
						{
							int idProducto = rs2.getInt("ID_PRODUCTO");
							int volumen = rs2.getInt("VOLUMEN");
							
							ProductoPedido act = new ProductoPedido(volumen, idProducto);
							productos.add(act);
						}
						int idCliente = rs.getInt("ID_CLIENTE");
						Date fechaEntrega= rs.getDate("FECHA_ENTREGA");
						Date fechaEsperada= rs.getDate("FECHA_ESPERADA");
						DateFormat fechaHora = new SimpleDateFormat("dd/MM/YYYY");
						String esperadaConvertida = fechaHora.format(fechaEsperada);
						String entregaConvertida="";
						if(fechaEntrega!=null)
						{
							entregaConvertida = fechaHora.format(fechaEntrega);
						}
						String estado = rs.getString("ESTADO");
						char c= estado.charAt(0);

						PedidoCliente r= new PedidoCliente(idCliente, c, entregaConvertida, esperadaConvertida, productos, idPedido);
						resp.add(r);
					}
				
				} catch (SQLException e) {
					e.printStackTrace();
					throw new Exception("ERROR = ConsultaDAO: loadRowsBy(..) Agregando parametros y executando el statement!!!");
				}finally 
				{
					if (prepStmt != null) 
					{
						try {
							prepStmt.close();
						} catch (SQLException exception) {
							
							throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexi�n.");
						}
					}
					closeConnection(conexion);
				}
	    	}
	    	else
	    	{
	    		PreparedStatement prepStmt=null;
	    		try {
					establecerConexion(cadenaConexion, usuario, clave);
					prepStmt = conexion.prepareStatement("SELECT * FROM PEDIDO_CLIENTE WHERE FECHA_ENTREGA='"+info+"'");
					
					ResultSet rs = prepStmt.executeQuery();
					
					while(rs.next())
					{
						
						int idPedido = rs.getInt("ID_PEDIDO");
						PreparedStatement prepStmt2 = conexion.prepareStatement("SELECT * FROM PRODUCTOSPEDIDOS WHERE ID_PEDIDO="+idPedido);
						ResultSet rs2 = prepStmt2.executeQuery();
						ArrayList<ProductoPedido> productos= new ArrayList<ProductoPedido>();
						while(rs2.next())
						{
							int idProducto = rs2.getInt("ID_PRODUCTO");
							int volumen = rs2.getInt("VOLUMEN");
							
							ProductoPedido act = new ProductoPedido(volumen, idProducto);
							productos.add(act);
						}
						int idCliente = rs.getInt("ID_CLIENTE");
						Date fechaEntrega= rs.getDate("FECHA_ENTREGA");
						System.out.println(fechaEntrega);
						Date fechaEsperada= rs.getDate("FECHA_ESPERADA");
						DateFormat fechaHora = new SimpleDateFormat("dd/MM/YYYY");
						String esperadaConvertida = fechaHora.format(fechaEsperada);
						String entregaConvertida="";
						if(fechaEntrega!=null)
						{
							entregaConvertida = fechaHora.format(fechaEntrega);
						}
						String estado = rs.getString("ESTADO");
						char c= estado.charAt(0);

						PedidoCliente r= new PedidoCliente(idCliente, c, entregaConvertida, esperadaConvertida, productos, idPedido);
						resp.add(r);
					}
				
				} catch (SQLException e) {
					e.printStackTrace();
					throw new Exception("ERROR = ConsultaDAO: loadRowsBy(..) Agregando parametros y executando el statement!!!");
				}finally 
				{
					if (prepStmt != null) 
					{
						try {
							prepStmt.close();
						} catch (SQLException exception) {
							
							throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexi�n.");
						}
					}
					closeConnection(conexion);
				}
	    	}
	    	return resp;
	    }
	    
	    public ArrayList<EstacionProduccion> darEstacionesActivadas() throws Exception
	    {
	    	PreparedStatement prepStmt = null;
	    	
	    	ArrayList<EstacionProduccion> pedidos = new ArrayList<EstacionProduccion>();
	    	
			try {
				establecerConexion(cadenaConexion, usuario, clave);
				prepStmt = conexion.prepareStatement("SELECT * FROM ESTACION_PRODUCCION WHERE ESTADO='T'");
				
				ResultSet rs = prepStmt.executeQuery();
				
				while(rs.next()){
					//TODO
					int codigo = rs.getInt("CODIGO");
					int capacidadHora = rs.getInt("CAPACIDAD_HORA");

					char estado = rs.getString("ESTADO").charAt(0);
					EstacionProduccion r = new EstacionProduccion(codigo, capacidadHora, estado);
					pedidos.add(r);
				}
			
			} catch (SQLException e) {
				e.printStackTrace();
				throw new Exception("ERROR = ConsultaDAO: loadRowsBy(..) Agregando parametros y executando el statement!!!");
			}finally 
			{
				if (prepStmt != null) 
				{
					try {
						prepStmt.close();
					} catch (SQLException exception) {
						
						throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexi�n.");
					}
				}
				closeConnection(conexion);
			}		
			return pedidos;
	    }
	    
	    public ArrayList<EstacionProduccion> darEstacionesDesActivadas() throws Exception
	    {
	    	PreparedStatement prepStmt = null;
	    	
	    	ArrayList<EstacionProduccion> pedidos = new ArrayList<EstacionProduccion>();
	    	
			try {
				establecerConexion(cadenaConexion, usuario, clave);
				prepStmt = conexion.prepareStatement("SELECT * FROM ESTACION_PRODUCCION WHERE ESTADO='F'");
				
				ResultSet rs = prepStmt.executeQuery();
				
				while(rs.next()){
					//TODO
					int codigo = rs.getInt("CODIGO");
					int capacidadHora = rs.getInt("CAPACIDAD_HORA");
					
					char estado = rs.getString("ESTADO").charAt(0);
					EstacionProduccion r = new EstacionProduccion(codigo, capacidadHora,  estado);
					pedidos.add(r);
				}
			
			} catch (SQLException e) {
				e.printStackTrace();
				throw new Exception("ERROR = ConsultaDAO: loadRowsBy(..) Agregando parametros y executando el statement!!!");
			}finally 
			{
				if (prepStmt != null) 
				{
					try {
						prepStmt.close();
					} catch (SQLException exception) {
						
						throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexi�n.");
					}
				}
				closeConnection(conexion);
			}		
			return pedidos;
	    }
	    
	    public ArrayList<ProductoPedido> agregarCarrito(int idProducto, int cantidad)
	    {
	    	ProductoPedido nuevo= new ProductoPedido(cantidad, idProducto);
	    	RF12.add(nuevo);
	    	return RF12;
	    }
	    
	    public PedidoCliente terminarRF12(String fechaEsperada) throws Exception
	    {
	    	PreparedStatement prepStm = null;
	    	PreparedStatement prepStmt = null;
	    	PreparedStatement prepStmt2=null;
	    	PreparedStatement prepStmt3=null;
	    	PreparedStatement prepStmt4=null;
	    	PreparedStatement prepStmt5=null;
	    	PreparedStatement prepStmt6=null;
	    	PedidoCliente resp = null;
	    	boolean posible = true;
			try {
				establecerConexion(cadenaConexion, usuario, clave);
				for(int i = 0; i<RF12.size()&&posible;i++)
				{
					prepStmt = conexion.prepareStatement("SELECT CANTIDAD, CANTIDADRESERVADA, NECESITADA FROM COMPONENTE INNER JOIN ( SELECT ID_COMPONENTE, CANTIDAD AS NECESITADA FROM NECESITACOMPONENTE WHERE ID_ETAPA IN"+
							"(SELECT ID FROM ETAPA_PRODUCCION WHERE ID_PROCESO IN ( SELECT ID_PROCESO FROM PROCESO_PRODUCCIOn WHERE ID_PRODUCTO="+RF12.get(i).getId_producto()+"))) ON ID=ID_COMPONENTE");				
					ResultSet rs = prepStmt.executeQuery();
					while(rs.next())
					{
						int cantidad= rs.getInt("CANTIDAD");
						int cantidadReservada = rs.getInt("CANTIDADRESERVADA");
						int necesitada= rs.getInt("NECESITADA")*RF12.get(i).getCantidad();
					
						if(necesitada>(cantidad-cantidadReservada))
							posible=false;
						
					}
				}
				for(int i = 0; i<RF12.size()&&posible;i++)
				{
					prepStmt2 = conexion.prepareStatement("SELECT CANTIDAD, CANTIDADRESERVADA, NECESITADA FROM MATERIA_PRIMA INNER JOIN( SELECT ID_MATERIA, CANTIDAD AS NECESITADA FROM NECESITAmaterial "
							+ "WHERE ID_ETAPA IN (SELECT ID FROM ETAPA_PRODUCCION WHERE  ID_PROCESO IN ( SELECT ID_PROCESO FROM PROCESO_PRODUCCION WHERE ID_PRODUCTO="+RF12.get(i).getId_producto()+"))) ON ID=ID_MATERIA");				
					ResultSet rs = prepStmt2.executeQuery();
					while(rs.next())
					{
						int cantidad= rs.getInt("CANTIDAD");
						int cantidadReservada = rs.getInt("CANTIDADRESERVADA");
						int necesitada= rs.getInt("NECESITADA")*RF12.get(i).getCantidad();
						
						if(necesitada>(cantidad-cantidadReservada))
							posible=false;			
					}		
				}
				if(posible)
				{
					
					char T='T';
				
					int idPedido= 0;
					prepStm = conexion.prepareStatement("select max(id_pedido) from pedido_cliente");				
					ResultSet rs3 =prepStm.executeQuery();
					while(rs3.next())
					{
						idPedido= rs3.getInt("MAX(ID_PEDIDO)")+1;
					}
					String[] s= fechaEsperada.split("-");
		    		String fecha=s[2]+"/"+s[1]+"/"+s[0];
		    		ArrayList<ProductoPedido> pro= new ArrayList<ProductoPedido>();
		    		for(int i=0;i<RF12.size();i++)
		    		{
		    			pro.add(RF12.get(i));
		    		}
					resp = new PedidoCliente(10, T, null, fecha,pro,idPedido);
					prepStmt3 = conexion.prepareStatement("INSERT INTO PEDIDO_CLIENTE (ID_PEDIDO,ID_CLIENTE,FECHA_ESPERADA,ESTADO) VALUES ("+idPedido+",10,TO_DATE('"+fecha+"','DD/MM/YYYY'),'T')");				
					prepStmt3.executeQuery();
					;
					for(int i=0; i<RF12.size();i++)
					{
						prepStmt4 = conexion.prepareStatement("INSERT INTO PRODUCTOSPEDIDOS (ID_PEDIDO,ID_PRODUCTO,VOLUMEN) VALUES ("+idPedido+","+RF12.get(i).getId_producto()+","+RF12.get(i).getCantidad()+")");
						prepStmt4.executeQuery();
						
						prepStmt5 = conexion.prepareStatement("UPDATE COMPONENTE SET CANTIDADRESERVADA =(SELECT CANTIDAD FROM NECESITACOMPONENTE WHERE ID_ETAPA IN(SELECT ID FROM ETAPA_PRODUCCION WHERE ID_PROCESO IN (SELECT ID_PROCESO FROM PROCESO_PRODUCCION WHERE ID_PRODUCTO="+RF12.get(i).getId_producto()+"))AND ID_COMPONENTE=ID)WHERE ID IN (SELECT ID_COMPONENTE FROM NECESITACOMPONENTE WHERE ID_ETAPA IN(SELECT ID FROM ETAPA_PRODUCCION WHERE ID_PROCESO IN (SELECT ID_PROCESO FROM PROCESO_PRODUCCION WHERE ID_PRODUCTO="+RF12.get(i).getId_producto()+")))");
						prepStmt5.executeQuery();
						
						prepStmt6 = conexion.prepareStatement("UPDATE MATERIA_PRIMA SET CANTIDADRESERVADA =(SELECT CANTIDAD FROM NECESITAMATERIAL WHERE ID_ETAPA IN(SELECT ID FROM ETAPA_PRODUCCION WHERE ID_PROCESO IN (SELECT ID_PROCESO FROM PROCESO_PRODUCCION WHERE ID_PRODUCTO="+RF12.get(i).getId_producto()+"))AND ID_MATERIA=ID) WHERE ID IN (SELECT ID_MATERIA FROM NECESITAMATERIAL WHERE ID_ETAPA IN(SELECT ID FROM ETAPA_PRODUCCION WHERE ID_PROCESO IN (SELECT ID_PROCESO FROM PROCESO_PRODUCCION WHERE ID_PRODUCTO="+RF12.get(i).getId_producto()+")))");
						prepStmt6.executeQuery();
						
					}	
				}
				RF12.clear();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new Exception("ERROR = ConsultaDAO: loadRowsBy(..) Agregando parametros y executando el statement!!!");
			}finally 
			{
				if (prepStmt != null) 
				{
					try {
						prepStmt.close();
					} catch (SQLException exception) {
						
						throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexi�n.");
					}
				}
				closeConnection(conexion);
			}	
			System.out.println(resp.getProductos().size());
			return resp;
	    }
	    
	    public ArrayList<Materia_Componente> RF14(String fechaEntrega, int id) throws Exception
	    {
	    	PreparedStatement prepStmt = null;
	    	PreparedStatement prepStmt1 = null;
	    	PreparedStatement prepStmt2 = null;
	    	ArrayList<Materia_Componente> resp = new ArrayList<Materia_Componente>();
	    	try 
	    	{
	    		establecerConexion(cadenaConexion, usuario, clave);
	    		String[] s= fechaEntrega.split("-");
	    		String fecha=s[2]+"/"+s[1]+"/"+s[0];
	    		System.out.println(fecha);
				prepStmt = conexion.prepareStatement("UPDATE PEDIDO_CLIENTE SET FECHA_ENTREGA =TO_DATE('"+fecha+"','DD/MM/YYYY'),ESTADO='E' WHERE ID_PEDIDO="+id);
				prepStmt.executeQuery();
				prepStmt1 = conexion.prepareStatement("UPDATE COMPONENTE SET CANTIDADRESERVADA =CANTIDADRESERVADA-(SELECT CANTIDAD FROM NECESITACOMPONENTE WHERE ID_ETAPA IN"+
						"(SELECT ID FROM ETAPA_PRODUCCION WHERE ID_PROCESO IN (SELECT ID_PROCESO FROM PROCESO_PRODUCCION WHERE ID_PRODUCTO="+id+"))"+
						"AND ID_COMPONENTE=ID), CANTIDAD = CANTIDAD-(SELECT CANTIDAD FROM NECESITACOMPONENTE WHERE ID_ETAPA IN"+
						"(SELECT ID FROM ETAPA_PRODUCCION WHERE ID_PROCESO IN (SELECT ID_PROCESO FROM PROCESO_PRODUCCION WHERE ID_PRODUCTO="+id+")) AND ID_COMPONENTE=ID)"+
						"WHERE ID IN (SELECT ID_COMPONENTE FROM NECESITACOMPONENTE WHERE ID_ETAPA IN"+
						"(SELECT ID FROM ETAPA_PRODUCCION WHERE ID_PROCESO IN (SELECT ID_PROCESO FROM PROCESO_PRODUCCION WHERE ID_PRODUCTO="+id+")))");
				prepStmt1.executeQuery();
				prepStmt2 = conexion.prepareStatement("SELECT * FROM COMPONENTE WHERE ID IN (SELECT ID_COMPONENTE FROM NECESITACOMPONENTE WHERE ID_ETAPA IN"+
				"(SELECT ID FROM ETAPA_PRODUCCION WHERE ID_PROCESO IN (SELECT ID_PROCESO FROM PROCESO_PRODUCCION WHERE ID_PRODUCTO="+id+")))");
				ResultSet rs = prepStmt2.executeQuery();
				while(rs.next())
				{
					String nombre = rs.getString("NOMBRE");
					String unidadMedida = rs.getString("UNIDAD_MEDIDA");
					int id2= rs.getInt("ID");
					int cantidad= rs.getInt("CANTIDAD");
					int cantidadReservada = rs.getInt("CANTIDADRESERVADA");
					
					Materia_Componente ag = new Materia_Componente(id2, nombre, unidadMedida, cantidad, cantidadReservada);
					resp.add(ag);
				}
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}				
	    	finally 
			{
				if (prepStmt != null) 
				{
					try {
						prepStmt.close();
					} catch (SQLException exception) {
						
						throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexi�n.");
					}
				}
				closeConnection(conexion);
			}	
	    	return resp;
	    }
	    
	    public boolean rf10(String idPedido, String idProducto, String nombreEtapa, int numSecuencia,int prueba) throws SQLException 
	    {
	    	boolean resp=false;
	    	PreparedStatement prepStmt = null;
	    	establecerConexion(cadenaConexion, usuario, clave);
	    	prepStmt = conexion.prepareStatement("SELECT nombre, numeroSecuencia, id,id_proceso "+ 
	    			"FROM (SELECT id_pedido AS id_ped FROM PEDIDO_CLIENTE WHERE id_Pedido="+idPedido+") "+ 
	    			"INNER JOIN (SELECT id_Producto AS id_prod,id_Pedido FROM PRODUCTOSPEDIDOS ) ON id_Ped=id_Pedido "+ 
	    			"INNER JOIN (SELECT id_producto AS idp FROM PRODUCTO WHERE id_producto="+idProducto+" ) ON id_Prod= idp "+ 
	    			"INNER JOIN (SELECT id_proceso As id_proc,id_producto FROM PROCESO_PRODUCCION) ON idp=id_producto "+ 
	    			"INNER JOIN (SELECT * FROM etapa_Produccion WHERE nombre='"+nombreEtapa+"' AND numerosecuencia="+numSecuencia+") ON id_proc=id_Proceso");
	    	ResultSet rs = prepStmt.executeQuery();
	    	if(rs.next())
	    	{
	    		PreparedStatement prepStmt1= conexion.prepareStatement("UPDATE etapa_produccion SET completada= 'T' WHERE id="+ rs.getInt("id")); 
	    		int numSecuenciaNew=numSecuencia+1;
	    		PreparedStatement prepStmt2= conexion.prepareStatement("UPDATE proceso_produccion SET numero_etapa_actual = " + numSecuenciaNew  + "WHERE id_proceso="+ rs.getInt("id_proceso"));
	    		prepStmt1.executeQuery();
	    		prepStmt2.executeQuery();
	    		resp=true;
	    	}
	    	return resp;
	    }

		public boolean rf11(int idPedido) throws SQLException
		{
	    	establecerConexion(cadenaConexion, usuario, clave);
			PreparedStatement prepStmt = conexion.prepareStatement("SElECT * FROM ELEMENTOS_PEDIDO_PROVEEDOR WHERE ID_PEDIDO_PROVEEDOR = " + idPedido );
			ResultSet r0 = prepStmt.executeQuery();
			if(r0.next())
			{
				if(r0.getInt("ID_MATERIA") == 0)
				{
					PreparedStatement prepStmti= conexion.prepareStatement("SELECT CANTIDAD FROM COMPONENTE WHERE ID =" + r0.getInt("ID_COMPONENTE"));
					ResultSet ri = prepStmti.executeQuery();
					if(ri.next())
					{
						int cantidad = ri.getInt("CANTIDAD") + r0.getInt("CANTIDAD");
						PreparedStatement prepStmt1 = conexion.prepareStatement("UPDATE COMPONENTE SET CANTIDAD = " + cantidad + "WHERE ID = " + r0.getInt("ID_COMPONENTE"));
						prepStmt1.executeQuery();
						PreparedStatement prepStmt2 = conexion.prepareStatement("DELETE FROM PEDIDO_PROVEEDOR WHERE ID_PEDIDO_PROVEEDOR="+r0.getInt("ID_PEDIDO_PROVEEDOR"));
						PreparedStatement prepStmt3 = conexion.prepareStatement("DELETE FROM ELEMENTOS_PEDIDO_PROVEEDOR WHERE ID_PEDIDO_PROVEEDOR="+r0.getInt("ID_PEDIDO_PROVEEDOR"));
						prepStmt3.executeQuery();
						prepStmt2.executeQuery();
						return true;
					}
					else
					{
						return false;
					}

				}
				else if(r0.getInt("ID_COMPONENTE") == 0)
				{
					PreparedStatement prepStmti= conexion.prepareStatement("SELECT CANTIDAD FROM MATERIA_PRIMA WHERE ID =" + r0.getInt("ID_MATERIA"));
					ResultSet ri = prepStmti.executeQuery();
					if(ri.next())
					{
						int cantidad = ri.getInt("CANTIDAD") + r0.getInt("CANTIDAD");
						PreparedStatement prepStmt1 = conexion.prepareStatement("UPDATE MATERIA_PRIMA SET CANTIDAD = " + cantidad + "WHERE ID = " + r0.getInt("ID_MATERIA"));
						prepStmt1.executeQuery();
						PreparedStatement prepStmt2 = conexion.prepareStatement("DELETE FROM PEDIDO_PROVEEDOR WHERE ID_PEDIDO_PROVEEDOR="+r0.getInt("ID_PEDIDO_PROVEEDOR"));
						PreparedStatement prepStmt3 = conexion.prepareStatement("DELETE FROM ELEMENTOS_PEDIDO_PROVEEDOR WHERE ID_PEDIDO_PROVEEDOR="+r0.getInt("ID_PEDIDO_PROVEEDOR"));
						prepStmt3.executeQuery();
						prepStmt2.executeQuery();
						return true;
					}
					else
					{
						return false;
					}

				}
				else
				{
					boolean resp= false;
					PreparedStatement prepStmtic= conexion.prepareStatement("SELECT CANTIDAD FROM COMPONENTE WHERE ID = " + r0.getInt("ID_COMPONENTE"));
					ResultSet ric = prepStmtic.executeQuery();
					if(ric.next())
					{					
						int cantidadc = ric.getInt("CANTIDAD") + r0.getInt("CANTIDAD");
						PreparedStatement prepStmt1c = conexion.prepareStatement("UPDATE COMPONENTE SET CANTIDAD = " + cantidadc + "WHERE ID = " + r0.getInt("ID_COMPONENTE"));
						prepStmt1c.executeQuery();
						resp = true;
					}
					PreparedStatement prepStmtim= conexion.prepareStatement("SELECT CANTIDAD FROM MATERIA_PRIMA WHERE ID = " + r0.getInt("ID_MATERIA"));
					ResultSet rim = prepStmtim.executeQuery();
					if(rim.next())
					{
						int cantidad = rim.getInt("CANTIDAD") + r0.getInt("CANTIDAD");
						PreparedStatement prepStmt1m = conexion.prepareStatement("UPDATE MATERIA_PRIMA SET CANTIDAD = " + cantidad + "WHERE ID = " + r0.getInt("ID_MATERIA"));
						prepStmt1m.executeQuery();
						PreparedStatement prepStmt2m = conexion.prepareStatement("DELETE FROM PEDIDO_PROVEEDOR WHERE ID_PEDIDO_PROVEEDOR="+r0.getInt("ID_PEDIDO_PROVEEDOR"));
						PreparedStatement prepStmt3m = conexion.prepareStatement("DELETE FROM ELEMENTOS_PEDIDO_PROVEEDOR WHERE ID_PEDIDO_PROVEEDOR="+r0.getInt("ID_PEDIDO_PROVEEDOR"));
						prepStmt3m.executeQuery();
						prepStmt2m.executeQuery();
						resp = true;
					}
					return resp;

				}
			}
			else
			{
				return false;
			}
				
		 
		}

		public ArrayList getRFC1(String tipo, int rangoi, int rangof,Date fechaS, Date fechaE) 
		{
			ArrayList resp=new ArrayList();
			try
			{
				if(tipo=="Materia Prima")
				{
					PreparedStatement prepStmt = conexion.prepareStatement("SELECT * FROM materia_prima INNER JOIN compracomponentemateria ON id_materia=id WHERE cantidadInicial>" + rangoi + " AND  cantidadInicial<" + rangof + "AND fechasolicitud="+fechaS+" AND fechaentrega="+fechaE );
					ResultSet r0 = prepStmt.executeQuery();
					while(r0.next())
					{
						resp.add(new MateriaPrima(r0.getString("NOMBRE"), r0.getString("UNIDAD_MEDIDA"), r0.getString("ID"), r0.getInt("CANTIDAD"), r0.getInt("CANTIDADRESERVADA")));
					}					
				}
				else if(tipo=="Componente")
				{
					PreparedStatement prepStmt = conexion.prepareStatement("SELECT * FROM componente INNER JOIN compracomponentemateria ON id_materia=id WHERE cantidadInicial>" + rangoi + " AND  cantidadInicial<" + rangof + "AND fechasolicitud="+fechaS+" AND fechaentrega="+fechaE);
					ResultSet r0 = prepStmt.executeQuery();
					while(r0.next())
					{
						resp.add(new MateriaPrima(r0.getString("NOMBRE"), r0.getString("UNIDAD_MEDIDA"), r0.getString("ID"), r0.getInt("CANTIDAD"), r0.getInt("CANTIDADRESERVADA")));
					}
				}
				return resp;
			}
			catch(SQLException e)
			{
				return resp;
			}
		}

		public ArrayList<PedidoCliente> getRFC10(String tipo, int costoVenta)
		{
			ArrayList<PedidoCliente> resp=new ArrayList<PedidoCliente>();
			try
			{
				establecerConexion(cadenaConexion, usuario, clave);
				if(tipo.equals(MATERIA_PRIMA))
				{
					int f = paginacion+20;
					PreparedStatement prepStmt = conexion.prepareStatement("SELECT ID_PEDIDO, ID_CLIENTE, FECHA_ENTREGA,FECHA_ESPERADA,ESTADO,FECHA_PEDIDO,FECHA_CANCELADO FROM("
							+"SELECT ID_PEDIDO, ID_CLIENTE, FECHA_ENTREGA,FECHA_ESPERADA,ESTADO,FECHA_PEDIDO,FECHA_CANCELADO, rownum r FROM PEDIDO_CLIENTE NATURAL JOIN("
							+"SELECT ID_PEDIDO FROM PRODUCTOSPEDIDOS NATURAL JOIN("
							+"SELECT* FROM PRODUCTO F NATURAL JOIN("
							+"SELECT DISTINCT ID_PROCESO, ID_PRODUCTO FROM PROCESO_PRODUCCION NATURAL JOIN("
							+"SELECT ID_ETAPA, ID_PROCESO FROM ETAPA_PRODUCCION INNER JOIN NECESITAMATERIAL ON ID_ETAPA=ID))"
							+"WHERE COSTO_VENTA>"+costoVenta+"))  ORDER BY ID_PEDIDO) WHERE r>="+paginacion+"AND r<"+f);
					ResultSet r0 = prepStmt.executeQuery();
					while(r0.next())
					{
						char estado=  r0.getString("ESTADO").charAt(0);
						Date fechaEntrega= r0.getDate("FECHA_ENTREGA");
						Date fechaEsperada= r0.getDate("FECHA_ESPERADA");
						DateFormat fechaHora = new SimpleDateFormat("dd/MM/YYYY");
						String esperadaConvertida = fechaHora.format(fechaEsperada);
						String entregaConvertida="";
						if(fechaEntrega!=null)
						{
							entregaConvertida = fechaHora.format(fechaEntrega);
						}
						PedidoCliente actual= new PedidoCliente(r0.getInt("ID_CLIENTE"), estado, entregaConvertida, esperadaConvertida, null, r0.getInt("ID_PEDIDO"));
						Date fechaCancelado= r0.getDate("FECHA_CANCELADO");
						Date fechaPedido= r0.getDate("FECHA_PEDIDO");
						String canceladoConvertida="";
						String pedidoConvertida ="";
						if(fechaCancelado!=null)
						{
							canceladoConvertida = fechaHora.format(fechaCancelado);
						}
						if(fechaPedido!=null)
						{
							pedidoConvertida = fechaHora.format(fechaPedido);
						}
						actual.setFechaCancelado( canceladoConvertida);
						actual.setFechaPedido( pedidoConvertida);
						resp.add(actual);
					}
					return resp;
				}
				else if(tipo.equals(COMPONENTE))
				{
					int f = paginacion+20;
					PreparedStatement prepStmt = conexion.prepareStatement("SELECT ID_PEDIDO, ID_CLIENTE, FECHA_ENTREGA,FECHA_ESPERADA,ESTADO,FECHA_PEDIDO,FECHA_CANCELADO FROM("
							+"SELECT ID_PEDIDO, ID_CLIENTE, FECHA_ENTREGA,FECHA_ESPERADA,ESTADO,FECHA_PEDIDO,FECHA_CANCELADO, rownum r FROM PEDIDO_CLIENTE NATURAL JOIN("
							+"SELECT ID_PEDIDO FROM PRODUCTOSPEDIDOS NATURAL JOIN("
							+"SELECT* FROM PRODUCTO F NATURAL JOIN("
							+"SELECT DISTINCT ID_PROCESO, ID_PRODUCTO FROM PROCESO_PRODUCCION NATURAL JOIN("
							+"SELECT ID_ETAPA, ID_PROCESO FROM ETAPA_PRODUCCION INNER JOIN NECESITACOMPONENTE ON ID_ETAPA=ID))"
							+"WHERE COSTO_VENTA>"+costoVenta+"))  ORDER BY ID_PEDIDO) WHERE r>="+paginacion+"AND r<"+f);
					ResultSet r0 = prepStmt.executeQuery();
					while(r0.next())
					{
						char estado=  r0.getString("ESTADO").charAt(0);
						Date fechaEntrega= r0.getDate("FECHA_ENTREGA");
						Date fechaEsperada= r0.getDate("FECHA_ESPERADA");
						DateFormat fechaHora = new SimpleDateFormat("dd/MM/YYYY");
						String esperadaConvertida = fechaHora.format(fechaEsperada);
						String entregaConvertida="";
						if(fechaEntrega!=null)
						{
							entregaConvertida = fechaHora.format(fechaEntrega);
						}
						PedidoCliente actual= new PedidoCliente(r0.getInt("ID_CLIENTE"), estado, entregaConvertida, esperadaConvertida, null, r0.getInt("ID_PEDIDO"));
						Date fechaCancelado= r0.getDate("FECHA_CANCELADO");
						Date fechaPedido= r0.getDate("FECHA_PEDIDO");
						String canceladoConvertida="";
						String pedidoConvertida ="";
						if(fechaCancelado!=null)
						{
							canceladoConvertida = fechaHora.format(fechaCancelado);
						}
						if(fechaPedido!=null)
						{
							pedidoConvertida = fechaHora.format(fechaPedido);
						}
						actual.setFechaCancelado( canceladoConvertida);
						actual.setFechaPedido( pedidoConvertida);
						resp.add(actual);
					}
				}
				return resp;
			}
			catch(SQLException e)
			{
				return resp;
			}
		}
		
		public ArrayList<PedidoCliente> getRFC11(String tipo, int id)
		{
			ArrayList<PedidoCliente> resp=new ArrayList<PedidoCliente>();
			try
			{
				System.out.println("no ha establecido");
				establecerConexion(cadenaConexion, usuario, clave);
				
				if(tipo.equals(MATERIA_PRIMA))
				{
					int f = paginacion+20;
					PreparedStatement prepStmt = conexion.prepareStatement("SELECT UNIQUE ID_PEDIDO,ID_CLIENTE, FECHA_ENTREGA, ESTADO, FECHA_ESPERADA, FECHA_PEDIDO, FECHA_CANCELADO FROM("
							+"SELECT ID_PEDIDO,ID_CLIENTE, FECHA_ENTREGA, ESTADO, FECHA_ESPERADA, FECHA_PEDIDO, FECHA_CANCELADO, ROWNUM r FROM PEDIDO_CLIENTE NATURAL JOIN("
							+"SELECT ID_PEDIDO FROM PRODUCTOSPEDIDOS NATURAL JOIN (SELECT* FROM PRODUCTO F NATURAL JOIN"
							+"(SELECT DISTINCT ID_PROCESO, ID_PRODUCTO FROM PROCESO_PRODUCCION NATURAL JOIN"
							+"(SELECT ID_ETAPA, ID_PROCESO FROM ETAPA_PRODUCCION INNER JOIN (SELECT * FROM NECESITAMATERIAL WHERE ID_MATERIA="+id+") ON ID_ETAPA=ID )"
							+"))) ORDER BY ID_PEDIDO) WHERE r>="+paginacion+" AND r<"+f);
					System.out.println(prepStmt);
					ResultSet r0 = prepStmt.executeQuery();
					while(r0.next())
					{
						char estado=  r0.getString("ESTADO").charAt(0);
						Date fechaEntrega= r0.getDate("FECHA_ENTREGA");
						Date fechaEsperada= r0.getDate("FECHA_ESPERADA");
						DateFormat fechaHora = new SimpleDateFormat("dd/MM/YYYY");
						String esperadaConvertida = fechaHora.format(fechaEsperada);
						String entregaConvertida="";
						if(fechaEntrega!=null)
						{
							entregaConvertida = fechaHora.format(fechaEntrega);
						}
						PedidoCliente actual= new PedidoCliente(r0.getInt("ID_CLIENTE"), estado, entregaConvertida, esperadaConvertida, null, r0.getInt("ID_PEDIDO"));
						Date fechaCancelado= r0.getDate("FECHA_CANCELADO");
						Date fechaPedido= r0.getDate("FECHA_PEDIDO");
						String canceladoConvertida="";
						String pedidoConvertida ="";
						if(fechaCancelado!=null)
						{
							canceladoConvertida = fechaHora.format(fechaCancelado);
						}
						if(fechaPedido!=null)
						{
							pedidoConvertida = fechaHora.format(fechaPedido);
						}
						actual.setFechaCancelado( canceladoConvertida);
						actual.setFechaPedido( pedidoConvertida);
						resp.add(actual);
					}
					return resp;
				}
				else if(tipo.equals(COMPONENTE))
				{
					int f = paginacion+20;
					PreparedStatement prepStmt = conexion.prepareStatement("SELECT UNIQUE ID_PEDIDO,ID_CLIENTE, FECHA_ENTREGA, ESTADO, FECHA_ESPERADA, FECHA_PEDIDO, FECHA_CANCELADO FROM("
							+"SELECT ID_PEDIDO,ID_CLIENTE, FECHA_ENTREGA, ESTADO, FECHA_ESPERADA, FECHA_PEDIDO, FECHA_CANCELADO, ROWNUM r FROM PEDIDO_CLIENTE NATURAL JOIN("
							+"SELECT ID_PEDIDO FROM PRODUCTOSPEDIDOS NATURAL JOIN (SELECT* FROM PRODUCTO F NATURAL JOIN"
							+"(SELECT DISTINCT ID_PROCESO, ID_PRODUCTO FROM PROCESO_PRODUCCION NATURAL JOIN"
							+"(SELECT ID_ETAPA, ID_PROCESO FROM ETAPA_PRODUCCION INNER JOIN (SELECT * FROM NECESITACOMPONENTE WHERE ID_MATERIA="+id+") ON ID_ETAPA=ID )"
							+"))) ORDER BY ID_PEDIDO)WHERE r>="+paginacion+" AND r<"+f);
					ResultSet r0 = prepStmt.executeQuery();
					while(r0.next())
					{
						char estado=  r0.getString("ESTADO").charAt(0);
						Date fechaEntrega= r0.getDate("FECHA_ENTREGA");
						Date fechaEsperada= r0.getDate("FECHA_ESPERADA");
						DateFormat fechaHora = new SimpleDateFormat("dd/MM/YYYY");
						String esperadaConvertida = fechaHora.format(fechaEsperada);
						String entregaConvertida="";
						if(fechaEntrega!=null)
						{
							entregaConvertida = fechaHora.format(fechaEntrega);
						}
						PedidoCliente actual= new PedidoCliente(r0.getInt("ID_CLIENTE"), estado, entregaConvertida, esperadaConvertida, null, r0.getInt("ID_PEDIDO"));
						Date fechaCancelado= r0.getDate("FECHA_CANCELADO");
						Date fechaPedido= r0.getDate("FECHA_PEDIDO");
						String canceladoConvertida="";
						String pedidoConvertida ="";
						if(fechaCancelado!=null)
						{
							canceladoConvertida = fechaHora.format(fechaCancelado);
						}
						if(fechaPedido!=null)
						{
							pedidoConvertida = fechaHora.format(fechaPedido);
						}
						actual.setFechaCancelado( canceladoConvertida);
						actual.setFechaPedido( pedidoConvertida);
						resp.add(actual);
					}
				}

				return resp;
			}
			catch(SQLException e)
			{
				System.out.println(e);
				return resp;
			}
		}
		
		public boolean RF13(int pedido) throws Exception 
		{
	    	establecerConexion(cadenaConexion, usuario, clave);
			PreparedStatement prepStmt0 = conexion.prepareStatement("SELECT ID_PRODUCTO, VOLUMEN ,ID_COMPONENTE, CANT AS NECESITADA FROM ((COMPONENTE INNER JOIN ( SELECT ID_COMPONENTE, CANTIDAD AS CANT, NECESITACOMPONENTE.ID_ETAPA FROM NECESITACOMPONENTE) ON ID=ID_COMPONENTE) INNER JOIN "+
					"(SELECT ID As IDETAPA,ID_PROCESO FROM ETAPA_PRODUCCION) ON idEtapa = ID_ETAPA INNER JOIN ( SELECT ID_PROCESO as IDPROC,ID_PRODUCTO  FROM PROCESO_PRODUCCION) ON ID_PROCESO = IDPROC "+ 
					"INNER JOIN (SELECT ID_PRODUCTO as IDPRODUCTO, VOLUMEN FROM PRODUCTOSPEDIDOS WHERE ID_PEDIDO ="+ pedido + ") ON  ID_PRODUCTO=IDPRODUCTO)" );
			ResultSet r0= prepStmt0.executeQuery();
			PreparedStatement prepStmt1 = conexion.prepareStatement("SELECT ID_PRODUCTO, VOLUMEN,ID_MATERIA, CANT AS NECESITADA FROM ((MATERIA_PRIMA INNER JOIN ( SELECT ID_MATERIA, CANTIDAD AS CANT, NECESITAMATERIAL.ID_ETAPA FROM NECESITAMATERIAL) ON ID=ID_MATERIA) INNER JOIN "+
					"(SELECT ID As IDETAPA,ID_PROCESO FROM ETAPA_PRODUCCION) ON IDETAPA = ID_ETAPA INNER JOIN ( SELECT ID_PROCESO as IDPROC,ID_PRODUCTO  FROM PROCESO_PRODUCCION) ON ID_PROCESO = IDPROC "+ 
					"INNER JOIN (SELECT ID_PRODUCTO as IDPRODUCTO, VOLUMEN FROM PRODUCTOSPEDIDOS WHERE ID_PEDIDO ="+ pedido + ") ON  ID_PRODUCTO=IDPRODUCTO)" );
			ResultSet r1= prepStmt1.executeQuery();


			while(r0.next())
			{
				PreparedStatement prepStmt2 = conexion.prepareStatement("SELECT CANTIDADRESERVADA FROM COMPONENTE WHERE ID = " + r0.getInt("ID_COMPONENTE"));
				ResultSet r2= prepStmt2.executeQuery();

				if(r2.next())
				{
					int nuevaCant= r2.getInt("CANTIDADRESERVADA") - r0.getInt("NECESITADA")*r0.getInt("VOLUMEN");
					PreparedStatement prepStm3 = conexion.prepareStatement("UPDATE COMPONENTE SET CANTIDADRESERVADA = " + nuevaCant + " WHERE ID = " + r0.getInt("ID_COMPONENTE"));
					prepStm3.executeQuery();

				}
			}
			while(r1.next())
			{
				PreparedStatement prepStmt3 = conexion.prepareStatement("SELECT CANTIDADRESERVADA FROM MATERIA_PRIMA WHERE ID = " + r1.getInt("ID_MATERIA"));
				ResultSet r3= prepStmt3.executeQuery();
				if(r3.next())
				{
					int nuevaCant= r3.getInt("CANTIDADRESERVADA") - r1.getInt("NECESITADA")*r1.getInt("VOLUMEN");
					PreparedStatement prepStm4 = conexion.prepareStatement("UPDATE MATERIA_PRIMA SET CANTIDADRESERVADA = " + nuevaCant + " WHERE ID = " + r1.getInt("ID_MATERIA"));
					prepStm4.executeQuery();
					

				}
			}
			PreparedStatement prepStmt = conexion.prepareStatement("UPDATE PEDIDO_CLIENTE SET ESTADO = 'C' WHERE ID_PEDIDO = " + pedido);
			prepStmt.executeQuery();
			closeConnection(conexion);
			System.out.println("TERMINO MOTHERFUCKER!!!!");
			return true;
	
		}
	
		public boolean RF17Desactivar(int id) throws Exception
		{
			PreparedStatement prep = null;
			PreparedStatement prepStmt = null;
			PreparedStatement prepStmt2 = null;
			PreparedStatement prepStmt3 = null;	
			PreparedStatement prepStmt4 = null;	
			PreparedStatement prepStmt5 = null;	
			PreparedStatement prepStmt6 = null;	
			PreparedStatement prepStmt7 = null;	
			try {
				establecerConexion(cadenaConexion, usuario, clave);
				prep = conexion.prepareStatement("SET TRANSACTION ISOLATION LEVEL SERIALIZABLE");
				prep.executeQuery();
				prepStmt = conexion.prepareStatement("UPDATE ESTACION_PRODUCCION SET ESTADO= 'F' WHERE CODIGO="+id);
				prepStmt.executeQuery();
				
				prepStmt4 = conexion.prepareStatement("SELECT ID_ESTACION FROM (SELECT MIN(COUNT(ID_ESTACION)) AS M FROM ESTACION_ETAPA GROUP BY ID_ESTACION)"+
				"INNER JOIN (SELECT COUNT(ID_ESTACION) AS S, ID_ESTACION FROM ESTACION_ETAPA GROUP BY ID_ESTACION) ON M=S");
				ResultSet rs= prepStmt4.executeQuery();
				prepStmt5 = conexion.prepareStatement("SELECT ID_ETAPA FROM ESTACION_ETAPA WHERE ID_ESTACION="+id);
				ResultSet rs2= prepStmt5.executeQuery();
				while(rs2.next())
				{
					if(rs.next())
					{
						int idEstacion = rs.getInt("ID_ESTACION");
						int idEtapa= rs2.getInt("ID_ETAPA");
						prepStmt6 = conexion.prepareStatement("INSERT INTO ESTACION_ETAPA VALUES ("+idEstacion+","+idEtapa+")");
						prepStmt6.executeQuery();
						prepStmt7 = conexion.prepareStatement("COMMIT");
						prepStmt7.executeQuery();
					}
					else
					{
						rs.beforeFirst();
						rs.next();
						int idEstacion = rs.getInt("ID_ESTACION");
						int idEtapa= rs2.getInt("ID_ETAPA");
						prepStmt6 = conexion.prepareStatement("INSERT INTO ESTACION_ETAPA VALUES ("+idEstacion+","+idEtapa+")");
						prepStmt6.executeQuery();
						prepStmt7 = conexion.prepareStatement("COMMIT");
						prepStmt7.executeQuery();
					}
				}
				prepStmt2 = conexion.prepareStatement("DELETE FROM ESTACION_ETAPA WHERE ID_ESTACION="+id);
				prepStmt2.executeQuery();
				return true;
			
			} catch (SQLException e) {
				e.printStackTrace();
				
				prepStmt3 = conexion.prepareStatement("ROLLBACK");
				prepStmt3.executeQuery();
				throw new Exception("ERROR = ConsultaDAO: loadRowsBy(..) Agregando parametros y executando el statement!!!");
				
			}finally 
			{
				
				if (prepStmt != null) 
				{
					try {
						prepStmt.close();
					} catch (SQLException exception) {
						prepStmt3 = conexion.prepareStatement("ROLLBACK");
						prepStmt3.executeQuery();
						throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexi�n.");
					}
				}
				closeConnection(conexion);
			}	
			
		}
		
		public boolean RF17Activar(int id) throws Exception
		{
			PreparedStatement prep = null;
			PreparedStatement prepStmt = null;
			PreparedStatement prepStmt2 = null;
			PreparedStatement prepStmt3 = null;	
			PreparedStatement prepStmt4 = null;	
			PreparedStatement prepStmt5 = null;	
			PreparedStatement prepStmt6 = null;	
			PreparedStatement prepStmt7 = null;	
			try {
				
				establecerConexion(cadenaConexion, usuario, clave);
				prep = conexion.prepareStatement("SET TRANSACTION ISOLATION LEVEL SERIALIZABLE");
				prep.executeQuery();
				prepStmt = conexion.prepareStatement("UPDATE ESTACION_PRODUCCION SET ESTADO= 'T' WHERE CODIGO="+id);
				prepStmt.executeQuery();
				prepStmt2 = conexion.prepareStatement("DELETE FROM ESTACION_ETAPA WHERE ID_ESTACION="+id);
				prepStmt2.executeQuery();
				prepStmt5 = conexion.prepareStatement("SELECT ID_ETAPA, ID_ESTACION FROM ESTACION_ETAPA WHERE ID_ESTACION IN (SELECT ID_ESTACION FROM"+ 
				"(SELECT MAX(COUNT(ID_ESTACION)) AS M FROM ESTACION_ETAPA GROUP BY ID_ESTACION)"+
				"INNER JOIN (SELECT COUNT(ID_ESTACION) AS S, ID_ESTACION FROM ESTACION_ETAPA GROUP BY ID_ESTACION) ON M=S) ORDER BY ID_ESTACION");
				ResultSet rs2= prepStmt5.executeQuery();
				while(rs2.next())
				{
					int idEtapa= rs2.getInt("ID_ETAPA");
					int idEstacion =rs2.getInt("ID_ESTACION");
					prepStmt6 = conexion.prepareStatement("INSERT INTO ESTACION_ETAPA VALUES ("+id+","+idEtapa+")");
					prepStmt6.executeQuery();
					prepStmt4 = conexion.prepareStatement("DELETE FROM ESTACION_ETAPA WHERE ID_ESTACION="+idEstacion+"AND ID_ETAPA="+idEtapa);
					prepStmt4.executeQuery();
					while(rs2.getInt("ID_ESTACION")==idEstacion)
					{
						rs2.next();
					}
				}
				prepStmt7 = conexion.prepareStatement("COMMIT");
				prepStmt7.executeQuery();
				return true;
			
			} catch (SQLException e) {
				e.printStackTrace();
				
				prepStmt3 = conexion.prepareStatement("ROLLBACK");
				prepStmt3.executeQuery();
				throw new Exception("ERROR = ConsultaDAO: loadRowsBy(..) Agregando parametros y executando el statement!!!");
				
			}finally 
			{
				
				if (prepStmt != null) 
				{
					try {
						prepStmt.close();
					} catch (SQLException exception) {
						prepStmt3 = conexion.prepareStatement("ROLLBACK");
						prepStmt3.executeQuery();
						throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexi�n.");
					}
				}
				closeConnection(conexion);
			}	
			
		}
		

		public ArrayList<EtapaProduccion> RFC8(String filtro, String info,String tiempoMin, String tiempoMax, String tipoMaterial,int cantMinMaterial, int cantMaxMaterial) throws Exception {
			ArrayList<EtapaProduccion> resp= new ArrayList<EtapaProduccion>();
			establecerConexion(cadenaConexion, usuario, clave);
			try
			{

				if(filtro.equals("CANTIDAD_MATERIAL"))
				{

					PreparedStatement prepStmt = conexion.prepareStatement("SELECT ID, NOMBRE, ID_PROCESO, ID_PRODSTOCK, ID_PRODSTOCKNECESITADO, NUMEROSECUENCIA, TIEMPO_INICIO_EJECUCION, TIEMPO_FIN_EJECUCION, COMPLETADA " + 
							"FROM ETAPA_PRODUCCION INNER JOIN((SELECT ID_ETAPA,ID_COMPONENTE, CANTIDAD AS CANTIDAD_COMPONENTE  FROM NECESITACOMPONENTE) NATURAL JOIN(SELECT ID_ETAPA,ID_MATERIA, CANTIDAD AS CANTIDAD_MATERIA FROM  NECESITAMATERIAL)) ON ID=ID_ETAPA " +
							"WHERE TIEMPO_INICIO_EJECUCION >='"+ tiempoMin+"' AND TIEMPO_FIN_EJECUCION <='"+ tiempoMax +"' AND " + cantMinMaterial + " <= (CANTIDAD_MATERIA + CANTIDAD_COMPONENTE) AND "+cantMaxMaterial+" >= (CANTIDAD_MATERIA + CANTIDAD_COMPONENTE)");
					ResultSet rs = prepStmt.executeQuery();	
					while (rs.next())
					{
						EtapaProduccion e= new EtapaProduccion(rs.getInt("NUMEROSECUENCIA"), rs.getString("NOMBRE"), rs.getInt("ID"), rs.getInt("ID_PROCESO"),rs.getInt("ID_PRODSTOCK") , rs.getInt("ID_PRODSTOCKNECESITADO"), rs.getDate("TIEMPO_INICIO_EJECUCION"),  rs.getDate("TIEMPO_FIN_EJECUCION"));
						resp.add(e);
					}

				}
				else if(filtro.equals("POR MATERIAL"))
				{
					if(tipoMaterial.equals("ID_MATERIA"))
					{
						PreparedStatement prepStmtIdx3=conexion.prepareStatement("CREATE INDEX idxIdmater ON NECESITAMATERIAL(ID_MATERIA)");
						prepStmtIdx3.execute();
						PreparedStatement prepStmt0 = conexion.prepareStatement("SELECT ID, NOMBRE, ID_PROCESO, ID_PRODSTOCK, ID_PRODSTOCKNECESITADO, NUMEROSECUENCIA, TIEMPO_INICIO_EJECUCION, TIEMPO_FIN_EJECUCION, COMPLETADA "
								+"FROM ETAPA_PRODUCCION INNER JOIN NECESITAMATERIAL ON ID=ID_ETAPA " +
								"WHERE TIEMPO_INICIO_EJECUCION >='"+ tiempoMin+"' AND TIEMPO_FIN_EJECUCION <='"+ tiempoMax +"' AND ID_MATERIA = "+ info);
						ResultSet rs0 = prepStmt0.executeQuery();	
						while (rs0.next())
						{
							EtapaProduccion e= new EtapaProduccion(rs0.getInt("NUMEROSECUENCIA"), rs0.getString("NOMBRE"), rs0.getInt("ID"), rs0.getInt("ID_PROCESO"),rs0.getInt("ID_PRODSTOCK") , rs0.getInt("ID_PRODSTOCKNECESITADO"), rs0.getDate("TIEMPO_INICIO_EJECUCION"),  rs0.getDate("TIEMPO_FIN_EJECUCION"));
							resp.add(e);
						}
						PreparedStatement prepStmtDropIdx3=conexion.prepareStatement("DROP INDEX idxIdmater");
						prepStmtDropIdx3.execute();

					}
					else
					{
						PreparedStatement prepStmtIdx3=conexion.prepareStatement("CREATE INDEX idxIdcomp ON NECESITACOMPONENTE(ID_COMPONENTE)");
						prepStmtIdx3.execute();
						PreparedStatement prepStmt0 = conexion.prepareStatement("SELECT ID, NOMBRE, ID_PROCESO, ID_PRODSTOCK, ID_PRODSTOCKNECESITADO, NUMEROSECUENCIA, TIEMPO_INICIO_EJECUCION, TIEMPO_FIN_EJECUCION, COMPLETADA "
								+"FROM ETAPA_PRODUCCION INNER JOIN NECESITACOMPONENTE ON ID=ID_ETAPA " +
								"WHERE TIEMPO_INICIO_EJECUCION >='"+ tiempoMin+"' AND TIEMPO_FIN_EJECUCION <='"+ tiempoMax +"' AND ID_COMPONENTE = "+ info);
						ResultSet rs0 = prepStmt0.executeQuery();	
						while (rs0.next())
						{
							EtapaProduccion e= new EtapaProduccion(rs0.getInt("NUMEROSECUENCIA"), rs0.getString("NOMBRE"), rs0.getInt("ID"), rs0.getInt("ID_PROCESO"),rs0.getInt("ID_PRODSTOCK") , rs0.getInt("ID_PRODSTOCKNECESITADO"), rs0.getDate("TIEMPO_INICIO_EJECUCION"),  rs0.getDate("TIEMPO_FIN_EJECUCION"));
							resp.add(e);
						}
						PreparedStatement prepStmtDropIdx3=conexion.prepareStatement("DROP INDEX idxIdcomp");
						prepStmtDropIdx3.execute();
					}

				}
				else
				{
					PreparedStatement prepStmtIdx3=conexion.prepareStatement("CREATE INDEX idxPedido ON PRODUCTOSPEDIDOS(ID_PEDIDO)");
					prepStmtIdx3.execute();
					PreparedStatement prepStmt1 = conexion.prepareStatement("SELECT ID, NOMBRE, ID_PROCESO, ID_PRODSTOCK, ID_PRODSTOCKNECESITADO, NUMEROSECUENCIA, TIEMPO_INICIO_EJECUCION, TIEMPO_FIN_EJECUCION, COMPLETADA " 
							+"FROM ETAPA_PRODUCCION NATURAL JOIN (SELECT ID_PROCESO, ID_PRODUCTO FROM PROCESO_PRODUCCION) NATURAL JOIN (SELECT ID_PRODUCTO FROM PRODUCTO) NATURAL JOIN (SELECT ID_PEDIDO, ID_PRODUCTO FROM  PRODUCTOSPEDIDOS) "
							+"WHERE TIEMPO_INICIO_EJECUCION >='"+ tiempoMin+"' AND TIEMPO_FIN_EJECUCION <='"+ tiempoMax +"' AND ID_PEDIDO ="+ info);
					ResultSet rs1 = prepStmt1.executeQuery();	
					while (rs1.next())
					{
						EtapaProduccion e= new EtapaProduccion(rs1.getInt("NUMEROSECUENCIA"), rs1.getString("NOMBRE"), rs1.getInt("ID"), rs1.getInt("ID_PROCESO"),rs1.getInt("ID_PRODSTOCK") , rs1.getInt("ID_PRODSTOCKNECESITADO"), rs1.getDate("TIEMPO_INICIO_EJECUCION"),  rs1.getDate("TIEMPO_FIN_EJECUCION"));
						resp.add(e);
					}
					PreparedStatement prepStmtDropIdx3=conexion.prepareStatement("DROP INDEX idxPedido");
					prepStmtDropIdx3.execute();
				}

			}
			catch(SQLException e)
			{
				PreparedStatement prepStmt3 = conexion.prepareStatement("ROLLBACK");
				prepStmt3.executeQuery();
				throw new Exception(e.getMessage());

			}
			closeConnection(conexion);
			return resp;
		}

		public ArrayList<EtapaProduccion> RFC9(String filtro, String info, String tiempoMin, String tiempoMax, String tipoMaterial, int cantMinMaterial, int cantMaxMaterial) throws Exception 
		{
			ArrayList<EtapaProduccion> resp= new ArrayList<EtapaProduccion>();
			establecerConexion(cadenaConexion, usuario, clave);
			try
			{		
				if(filtro.equals("CANTIDAD_MATERIAL"))
				{

					PreparedStatement prepStmt = conexion.prepareStatement("SELECT ID, NOMBRE, ID_PROCESO, ID_PRODSTOCK, ID_PRODSTOCKNECESITADO, NUMEROSECUENCIA, TIEMPO_INICIO_EJECUCION, TIEMPO_FIN_EJECUCION, COMPLETADA " + 
							"FROM ETAPA_PRODUCCION INNER JOIN((SELECT ID_ETAPA,ID_COMPONENTE, CANTIDAD AS CANTIDAD_COMPONENTE  FROM NECESITACOMPONENTE) NATURAL JOIN(SELECT ID_ETAPA,ID_MATERIA, CANTIDAD AS CANTIDAD_MATERIA FROM  NECESITAMATERIAL)) ON ID=ID_ETAPA " +
							"WHERE TIEMPO_INICIO_EJECUCION >='"+ tiempoMin+"' AND TIEMPO_FIN_EJECUCION <='"+ tiempoMax +"' AND " + cantMinMaterial + " > (CANTIDAD_MATERIA + CANTIDAD_COMPONENTE) AND "+cantMaxMaterial+" < (CANTIDAD_MATERIA + CANTIDAD_COMPONENTE)");
					ResultSet rs = prepStmt.executeQuery();	
					while (rs.next())
					{
						EtapaProduccion e= new EtapaProduccion(rs.getInt("NUMEROSECUENCIA"), rs.getString("NOMBRE"), rs.getInt("ID"), rs.getInt("ID_PROCESO"),rs.getInt("ID_PRODSTOCK") , rs.getInt("ID_PRODSTOCKNECESITADO"), rs.getDate("TIEMPO_INICIO_EJECUCION"),  rs.getDate("TIEMPO_FIN_EJECUCION"));
						resp.add(e);
					}

				}
				else if(filtro.equals("POR MATERIAL"))
				{
					if(tipoMaterial.equals("ID_MATERIA"))
					{

						PreparedStatement prepStmt0 = conexion.prepareStatement("SELECT ID, NOMBRE, ID_PROCESO, ID_PRODSTOCK, ID_PRODSTOCKNECESITADO, NUMEROSECUENCIA, TIEMPO_INICIO_EJECUCION, TIEMPO_FIN_EJECUCION, COMPLETADA "
								+"FROM ETAPA_PRODUCCION INNER JOIN NECESITAMATERIAL ON ID=ID_ETAPA " +
								"WHERE TIEMPO_INICIO_EJECUCION >='"+ tiempoMin+"' AND TIEMPO_FIN_EJECUCION <='"+ tiempoMax +"' AND ID_MATERIA != "+ info);
						ResultSet rs0 = prepStmt0.executeQuery();	
						while (rs0.next())
						{
							EtapaProduccion e= new EtapaProduccion(rs0.getInt("NUMEROSECUENCIA"), rs0.getString("NOMBRE"), rs0.getInt("ID"), rs0.getInt("ID_PROCESO"),rs0.getInt("ID_PRODSTOCK") , rs0.getInt("ID_PRODSTOCKNECESITADO"), rs0.getDate("TIEMPO_INICIO_EJECUCION"),  rs0.getDate("TIEMPO_FIN_EJECUCION"));
							resp.add(e);
						}

					}
					else
					{

						PreparedStatement prepStmt0 = conexion.prepareStatement("SELECT ID, NOMBRE, ID_PROCESO, ID_PRODSTOCK, ID_PRODSTOCKNECESITADO, NUMEROSECUENCIA, TIEMPO_INICIO_EJECUCION, TIEMPO_FIN_EJECUCION, COMPLETADA "
								+"FROM ETAPA_PRODUCCION INNER JOIN NECESITACOMPONENTE ON ID=ID_ETAPA " +
								"WHERE TIEMPO_INICIO_EJECUCION >='"+ tiempoMin+"' AND TIEMPO_FIN_EJECUCION <='"+ tiempoMax +"' AND ID_COMPONENTE != "+ info);
						ResultSet rs0 = prepStmt0.executeQuery();	
						while (rs0.next())
						{
							EtapaProduccion e= new EtapaProduccion(rs0.getInt("NUMEROSECUENCIA"), rs0.getString("NOMBRE"), rs0.getInt("ID"), rs0.getInt("ID_PROCESO"),rs0.getInt("ID_PRODSTOCK") , rs0.getInt("ID_PRODSTOCKNECESITADO"), rs0.getDate("TIEMPO_INICIO_EJECUCION"),  rs0.getDate("TIEMPO_FIN_EJECUCION"));
							resp.add(e);
						}

					}

				}
				else
				{
					PreparedStatement prepStmt1 = conexion.prepareStatement("SELECT ID, NOMBRE, ID_PROCESO, ID_PRODSTOCK, ID_PRODSTOCKNECESITADO, NUMEROSECUENCIA, TIEMPO_INICIO_EJECUCION, TIEMPO_FIN_EJECUCION, COMPLETADA " 
							+"FROM ETAPA_PRODUCCION NATURAL JOIN (SELECT ID_PROCESO, ID_PRODUCTO FROM PROCESO_PRODUCCION) NATURAL JOIN (SELECT ID_PRODUCTO FROM PRODUCTO) NATURAL JOIN (SELECT ID_PEDIDO, ID_PRODUCTO FROM  PRODUCTOSPEDIDOS) "
							+"WHERE TIEMPO_INICIO_EJECUCION >='"+ tiempoMin+"' AND TIEMPO_FIN_EJECUCION <='"+ tiempoMax +"' AND ID_PEDIDO !="+ info);
					ResultSet rs1 = prepStmt1.executeQuery();	
					while (rs1.next())
					{
						EtapaProduccion e= new EtapaProduccion(rs1.getInt("NUMEROSECUENCIA"), rs1.getString("NOMBRE"), rs1.getInt("ID"), rs1.getInt("ID_PROCESO"),rs1.getInt("ID_PRODSTOCK") , rs1.getInt("ID_PRODSTOCKNECESITADO"), rs1.getDate("TIEMPO_INICIO_EJECUCION"),  rs1.getDate("TIEMPO_FIN_EJECUCION"));
						resp.add(e);
					}

				}
			}
			catch(SQLException e)
			{
				PreparedStatement prepStmt3 = conexion.prepareStatement("ROLLBACK");
				prepStmt3.executeQuery();
				throw new Exception(e.getMessage());

			}
			closeConnection(conexion);
			return resp;
		}
}
