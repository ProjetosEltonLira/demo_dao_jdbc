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
        ResultSet resultSet = null;
        try {
            statement = conexao.prepareStatement(
                    "SELECT seller.*," +
                            "department.Name as DepName " +
                            "FROM seller INNER JOIN department " +
                            "ON seller.DepartmentId = department.Id " +
                            "WHERE seller.Id = ?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            //encontrou registro
            if (resultSet.next()) {
                Department department = instanciarDepartamento(resultSet);
                Seller seller = instanciarSeller(resultSet, department);
                return seller;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            Database.closeStatement(statement);
            Database.closeResultSet(resultSet);
        }
    }

    private static Seller instanciarSeller(ResultSet result, Department department) throws SQLException {
        return new Seller(result.getInt("Id"),
                result.getString("Name"),
                result.getString("Email"),
                result.getDate("BirthDate"),
                result.getDouble("BaseSalary"),
                department);
    }

    private static Department instanciarDepartamento(ResultSet result) throws SQLException {
        return new Department(result.getInt("DepartmentId"),
                result.getString("DepName"));
    }

    @Override
    public List<Seller> findAll() {
        return null;
    }
}
