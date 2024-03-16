package model.dao.implementacao;

import db.Database;
import db.DbException;
import model.dao.DepartmentDao;
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

public class DepartmentDaoJDBC implements DepartmentDao {

    private Connection conexao;

    public DepartmentDaoJDBC(Connection conexao) {
        this.conexao = conexao;
    }

    @Override
    public void insert(Department department) {
        PreparedStatement statement = null;
        try {
            statement = conexao.prepareStatement("INSERT INTO department " +
                    " (Name) " +
                    " VALUES " +
                    " (?)", statement.RETURN_GENERATED_KEYS);
            statement.setString(1, department.getName());

            int linhasAlteradas = statement.executeUpdate();
            if (linhasAlteradas > 0) {
                ResultSet resultSet = statement.getGeneratedKeys(); //Pega o retorno do id e coloca num ResultSet.
                if (resultSet.next()) {
                    department.setId(resultSet.getInt(1));//Coloca o id no objeto seller.
                }
                Database.closeResultSet(resultSet);
            } else {
                throw new DbException("Erro inesperado, nenhuma linha foi alterada");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            Database.closeStatement(statement);
        }
    }

    @Override
    public void update(Department department) {

        PreparedStatement statement = null;
        try {
            statement = conexao.prepareStatement("UPDATE department "
                    + "SET Name = ? "
                    + "WHERE Id = ?");
            statement.setString(1, department.getName());
            statement.setInt(2, department.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            Database.closeStatement(statement);
        }
    }

    @Override
    public void deleteById(Integer id) {

        PreparedStatement statement = null;
        try {
            statement = conexao.prepareStatement("DELETE FROM department " +
                    "WHERE Id = ?");
            statement.setInt(1, id);
            statement.executeUpdate();

            //int linhasAlteradas, pode ser usado para testar se foi deletado algum registro.
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            Database.closeStatement(statement);
        }
    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = conexao.prepareStatement(
                    "SELECT * from department where Id = ?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            //encontrou registro
            if (resultSet.next()) {
                return instanciarDepartamento(resultSet);

            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            Database.closeStatement(statement);
            Database.closeResultSet(resultSet);
        }
    }

    @Override
    public List<Department> findAll() {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = conexao.prepareStatement(
                    "SELECT * FROM department "
                            + "ORDER BY Id");
            resultSet = statement.executeQuery();

            List<Department> departmentList = new ArrayList<>();

            //Solução um Dificil #1
            Map<Integer, Department> map = new HashMap<>();
            while (resultSet.next()) {
                Department department = map.get(resultSet.getInt("Id"));//Existe algum departamento com esse id.
                if (department == null) {
                    department = instanciarDepartamento(resultSet);
                    map.put(department.getId(), department);
                }
                departmentList.add(department);
            }
            /*Solução simples #2
            while(resultSet.next()) {
                Seller seller = instanciarSeller(resultSet, department);
                sellerList.add(seller);
            }*/
            return departmentList;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            Database.closeStatement(statement);
            Database.closeResultSet(resultSet);
        }
    }

    private static Department instanciarDepartamento(ResultSet result) throws SQLException {
        return new Department(result.getInt("Id"),
                result.getString("Name"));
    }

}

