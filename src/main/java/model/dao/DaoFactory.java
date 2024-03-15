package model.dao;
import db.Database;
import model.dao.implementacao.SellerDaoJDBC;

public class DaoFactory {

    //Retorna a conexão com o banco de dados
    public static SellerDao createSellerDAO(){
        return new SellerDaoJDBC(Database.getConexao());
    }
}
