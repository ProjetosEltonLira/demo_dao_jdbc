package model.dao.implementacao;

import db.Database;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SellerDaoJDBC implements SellerDao {

    private Connection conexao;

    public SellerDaoJDBC(Connection conexao) {
        this.conexao = conexao;
    }

    @Override
    public void insert(Seller seller) {

    }

    @Override
    public void update(Seller seller) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement statement = null;
        ResultSet result = null;
        try {
            statement = conexao.prepareStatement(
                    "SELECT seller.*," +
                            "department.Name as DepName " +
                            "FROM seller INNER JOIN department " +
                            "ON seller.DepartmentId = department.Id " +
                            "WHERE seller.Id = ?");
            statement.setInt(1, id);
            result = statement.executeQuery();

            //encontrou registro
            if (result.next()) {
                Department department = new Department(
                        result.getInt("DepartmentId"),
                        result.getString("DepName"));
                //department.setId(result.getInt("DepartmentId"));
                //department.setId(result.getString("DepName"));
                Seller seller = new Seller(
                        result.getInt("Id"),
                        result.getString("Name"),
                        result.getString("Email"),
                        result.getDate("BirthDate"),
                        result.getDouble("BaseSalary"),
                        department);
                return seller;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            Database.closeStatement(statement);
            Database.closeResultSet(result);
        }


    }

    @Override
    public List<Seller> findAll() {
        return null;
    }
}
