package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.dao.implementacao.SellerDaoJDBC;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Program {
    public static void main(String[] args)  {


        Scanner scanner = new Scanner(System.in);
        //Department departament = new Department(2,null);
        Department departament = new Department(2,"Livraria");



        //SellerDao sellerDao = new SellerDaoJDBC(Database.getConexao()); //Funciona também
        SellerDao sellerDao = DaoFactory.createSellerDAO(); //retorna a conexão com o banco de dados

        System.out.println("=== TESTE 1 : seller findById ===");
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);


        System.out.println("\n=== TESTE 2 : seller findByDepartment ===");
        List<Seller> list = sellerDao.findByDepartment(departament);
        list.forEach(System.out::println);

        System.out.println("\n=== TESTE 3 : seller findAll ===");
        list = sellerDao.findAll();
        list.forEach(System.out::println);

        System.out.println("\n=== TESTE 4 : seller insert ===");
        seller = new Seller(null,"Bob","Bob@lee.com.br",new Date(),3000.00,departament);
        sellerDao.insert(seller);
        System.out.println("Inserido! Nº id " + seller.getId());


        System.out.println("\n=== TESTE 5 : seller update ===");
        seller = sellerDao.findById(1);
        seller.setName("Martha");
        sellerDao.update(seller);
        System.out.println("Atualização completa");

        System.out.println("\n=== TESTE 6 : seller Delete ===");
        System.out.println("Escreva um Id para o teste de deletar: ");
        int id = scanner.nextInt();
        sellerDao.deleteById(id);
        System.out.println("Seller deletado");
        scanner.close();

    }
}

