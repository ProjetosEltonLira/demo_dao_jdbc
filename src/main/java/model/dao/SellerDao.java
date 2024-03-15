package model.dao;

import model.entities.Department;
import model.entities.Seller;

import java.sql.SQLException;
import java.util.List;

public interface SellerDao {

    public void insert(Seller seller) ;
    public void update(Seller seller);
    public void deleteById(Integer id);
    public Seller findById(Integer id);
    List<Seller> findAll();
    List<Seller> findByDepartment(Department department);
}
