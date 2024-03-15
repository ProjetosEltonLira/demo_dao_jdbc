package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.dao.implementacao.SellerDaoJDBC;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;

public class Program {
    public static void main(String[] args){

        //Department departament = new Department(1,"Livros");
       //Seller seller = new Seller(1,"Bob","Bob@lee.com.br",new Date(),3000.00,departament);

        System.out.println("=== TESTE 1 : seller findById ===");
        //SellerDao sellerDao = new SellerDaoJDBC(Database.getConexao()); //Funciona também
        SellerDao sellerDao = DaoFactory.createSellerDAO(); //retorna a conexão com o banco de dados

        Seller seller = sellerDao.findById(3);
        System.out.println(seller);
    }
}

