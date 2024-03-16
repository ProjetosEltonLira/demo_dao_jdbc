package application;

import db.Database;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.dao.implementacao.DepartmentDaoJDBC;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Program2Deparment {
    public static void main(String[] args)  {

        Scanner scanner = new Scanner(System.in);

        DepartmentDao departmentDao = new DepartmentDaoJDBC(Database.getConexao()); //Funciona também
        //DepartmentDao departmentDao = DaoFactory.createDepartmentDAO(); //retorna a conexão com o banco de dados

        System.out.println("=== TESTE 1 : department insert ===");
        Department departamento = new Department(2,"Livraria");
        departmentDao.insert(departamento);
        System.out.println("Inserido! Nº id " + departamento.getId());

        System.out.println("\n=== TESTE 2 : department update ===");
        departamento = departmentDao.findById(1);
        departamento.setName("Financeiro");
        departmentDao.update(departamento);
        System.out.println("Atualização completa");

        System.out.println("\n=== TESTE 3 : department findById ===");
        departamento = departmentDao.findById(3);
        System.out.println(departamento);

        System.out.println("\n=== TESTE 4 : department findAll ===");
        List<Department> departmentList = departmentDao.findAll();
        departmentList.forEach(System.out::println);

        System.out.println("\n=== TESTE 5 : department Delete ===");
        System.out.println("Escreva um Id para o teste de deletar: ");
        int id = scanner.nextInt();
        departmentDao.deleteById(id);
        System.out.println("Departamento deletado");
        scanner.close();

        System.out.println("\n=== TESTE 4 : department findAll ===");
        departmentList = departmentDao.findAll();
        departmentList.forEach(System.out::println);
















    }
}

