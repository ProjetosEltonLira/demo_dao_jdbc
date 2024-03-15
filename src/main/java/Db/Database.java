package Db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class Database {

    private static Connection conexao = null;

    public static Connection getConexao() {
        if (conexao == null) {
            try {
                Properties propriedade = carregarPropriedades(); //pegou as propriedades
                String url = propriedade.getProperty("dburl"); // URL do banco
                conexao = DriverManager.getConnection(url, propriedade); //Conectar no banco e atribuir à conexao
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
        return conexao;
    }

    public static void closeConexao(){
        if (conexao != null){
            try {
                conexao.close();
            }
            catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    public static Properties carregarPropriedades() {
        try (FileInputStream fileInputStream = new FileInputStream("db.properties")) {
            Properties propriedades = new Properties();
            propriedades.load(fileInputStream);
            return propriedades;

        } catch (IOException e) {
            throw new DbException(e.getMessage());
        }
    }


    public static void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }
}
