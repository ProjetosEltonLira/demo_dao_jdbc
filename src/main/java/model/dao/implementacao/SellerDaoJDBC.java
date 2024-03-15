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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {

    private Connection conexao;

    public SellerDaoJDBC(Connection conexao) {
        this.conexao = conexao;
    }

    @Override
    public void insert(Seller seller) {
        PreparedStatement statement = null;
        try {
            statement = conexao.prepareStatement("INSERT INTO seller " +
                    " (Name, Email, BirthDate, BaseSalary, DepartmentId) " +
                    " VALUES " +
                    " (?, ?, ?, ?, ?)",statement.RETURN_GENERATED_KEYS);
            statement.setString(1, seller.getName());
            statement.setString(2, seller.getEmail());
            statement.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
            statement.setDouble(4, seller.getBaseSalary());
            statement.setInt(5, seller.getDepartment().getId());

            int linhasAlteradas = statement.executeUpdate();
            if (linhasAlteradas > 0) {
                ResultSet resultSet = statement.getGeneratedKeys(); //Pega o retorno do id e coloca num ResultSet.
                if (resultSet.next()){
                   seller.setId(resultSet.getInt(1));//Coloca o id no objeto seller.
                }
                Database.closeResultSet(resultSet);
            }
            else {
                throw new DbException("Erro inesperado, nenhuma linha foi alterada");
            }
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            Database.closeStatement(statement);
        }
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
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = conexao.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                            +"FROM seller INNER JOIN department "
                            +"ON seller.DepartmentId = department.Id "
                            +"ORDER BY Name");
            resultSet = statement.executeQuery();
            List<Seller> sellerList = new ArrayList<>();

            //Solução um Dificil #1
            Map<Integer, Department> map = new HashMap<>();
            while (resultSet.next()) {
                Department dep = map.get(resultSet.getInt("DepartmentId"));//Existe algum departamento com esse id.
                if (dep == null) {
                    dep = instanciarDepartamento(resultSet);
                    map.put(dep.getId(), dep);
                }
                Seller seller = instanciarSeller(resultSet, dep);
                sellerList.add(seller);

            }
            /*Solução simples #2
            while(resultSet.next()) {
                Seller seller = instanciarSeller(resultSet, department);
                sellerList.add(seller);
            }*/
            return sellerList;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            Database.closeStatement(statement);
            Database.closeResultSet(resultSet);
        }
    }


    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = conexao.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                    +"FROM seller INNER JOIN department "
                    +"ON seller.DepartmentId = department.Id "
                    +"WHERE DepartmentId = ? "
                    +"ORDER BY Name");
            statement.setInt(1, department.getId());
            resultSet = statement.executeQuery();


            List<Seller> sellerList = new ArrayList<>();
            //Solução um Dificil.

            Map<Integer, Department> map = new HashMap<>();
            while (resultSet.next()) {
                Department dep = map.get(resultSet.getInt("DepartmentId"));//Existe algum departamento com esse id.
                if (dep == null) {
                    dep = instanciarDepartamento(resultSet);
                    map.put(dep.getId(), dep);
                }
                Seller seller = instanciarSeller(resultSet, department);
                sellerList.add(seller);

            }
            /*Solução simples #2
            while(resultSet.next()) {
                Seller seller = instanciarSeller(resultSet, department);
                sellerList.add(seller);
            }*/
            return sellerList;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            Database.closeStatement(statement);
            Database.closeResultSet(resultSet);
        }
    }
}
