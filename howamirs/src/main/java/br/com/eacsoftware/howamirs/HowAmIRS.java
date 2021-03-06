package br.com.eacsoftware.howamirs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import br.com.eacsoftware.howamiws.beans.Registro;
import br.com.eacsoftware.howamiws.dao.ParametrosDAO;

@Path("/howamirs")
public class HowAmIRS {

	@GET
	@Path("/echo/{input}")
	@Produces("text/plain")
	public String ping(@PathParam("input") String input) {
		return input;
	}

	@GET
	@Path("/registrosDiaModulo/{input}")
	@Produces(MediaType.APPLICATION_JSON)
	public String registrosDiaModulo(@PathParam("input") String input) {
		// RespostaListaParametrosModulo resposta = new
		// RespostaListaParametrosModulo();
		// resposta.setSucesso(false);
		// funcionario = "1";
		List<Registro> regs = null;
		Connection con;
		try {
			try {
				con = conectaBanco();
				regs = ParametrosDAO.listaRgistrosDiaModulo(con, input);
				con.close();
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Parametros parametros = ParametrosDAO.listaParametrosModulo(con,
			// modulo, processo, terminal, atividade);
			// resposta.setSucesso(true);
			// resposta.setParametros(parametros);
			// JAXBContext context =
			// JAXBContext.newInstance("br.com.eacsoftware.saciws.tipos");
			// Marshaller marshaller = context.createMarshaller();
			// marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			// StringWriter sw = new StringWriter();
			// marshaller.marshal(resposta, sw);
			// instancia um objeto da classe Gson
			Gson gson = new Gson();
			// pega os dados do filme, converte para JSON e armazena em string
			String regsString = gson.toJson(regs);

			return regsString;
		} catch (SQLException e) {
			// return e.getMessage();
		}
		// return regs.toString();
		return "Teste executado:  " + input;
	}

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/jsonBean")
	public Response modifyJson(JsonBean input) {
		input.setVal2(input.getVal1());
		return Response.ok().entity(input).build();
	}

	private Connection conectaBanco() throws NamingException, SQLException {
		// Context ctx;
		// ctx = new InitialContext();
		// DataSource ds = (DataSource)
		// ctx.lookup("java:comp/env/jdbc/howamieac");
		// return ds.getConnection();
		// TODO Auto-generated catch block
		try {
			Connection con = null;
			// Statement stmt = null;
			Class.forName("com.mysql.jdbc.Driver");
			String DB_URL = "jdbc:mysql://localhost/sqldados";
			String USER = "root";
			String PASS = "root";
			System.out.println("Connecting to database...");
			con = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Creating statement...");
			// stmt = con.createStatement();
			System.out.println("Conectando ao banco...");
			System.out.println("Conectado.");
			return con;
		} catch (ClassNotFoundException ex) {
			System.out.println("Classe não encontrada, adicione o driver nas bibliotecas.");
		} catch (SQLException e) {
			System.out.println(e);
			throw new RuntimeException(e);
		}
		return null;
	}
}
