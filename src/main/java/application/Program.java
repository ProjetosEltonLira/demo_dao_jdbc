package application;

import model.entities.Department;
import model.entities.Seller;

import java.util.Date;

public class Program {
    public static void main(String[] args){

        Department departament = new Department(1,"Livros");
        Seller seller = new Seller(1,"Bob","Bob@lee.com.br",new Date(),3000.00,departament);
        System.out.println(seller);
    }
}
