package model.dao;
import model.dao.implementacao.SellerDaoJDBC;

public class DaoFactory {

    public static SellerDao createSellerDAO(){
        return new SellerDaoJDBC();
    }
}
