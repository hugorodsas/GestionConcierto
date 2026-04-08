import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String url="jdbc:oracle:thin:@localhost:1521:xe";
        String user="RIBERA";
        String password="ribera";
        int opcion=0;
        int artistas=0;
        int concierto=0;
        int entada=0;
        Scanner sc=new Scanner(System.in);
        do{
            System.out.println("MENU CRUD");
            System.out.println("1. Menu Artistas");
            System.out.println("2. Menu Conciertos");
            System.out.println("3. Menu Entradas");
            System.out.println("4. Salir");
            System.out.println("Ingrese una de las opciones:");
            opcion=sc.nextInt();
            switch (opcion){
                case 1:
                    do{
                        System.out.println("1. Añade un nuevo artista:");
                        System.out.println("2. Eliimina un artista:");
                        System.out.println("3. Listar artistas:");
                        System.out.println("4. Salir");
                        System.out.println("Eliga una de las opciones:");
                        switch(artistas){
                            case 1:
                                System.out.println("Ingrese el nombre del artista:");
                                String artista=sc.nextLine();
                                try (Connection conn = DriverManager.getConnection(url,user,password);
                                     Statement statement = conn.createStatement()){
                                    String sql="INSERT INTO empleado2 (id, nombre, salario) VALUES (?, ?, ?)";
                                    PreparedStatement preparedStatement = conn.prepareStatement(sql);

                                    preparedStatement.setInt(1,Insertarid);
                                    preparedStatement.setString(2, Insetarnombre);
                                    preparedStatement.setDouble(3, Insertarsalario);
                                    preparedStatement.executeUpdate();
                                    System.out.println("Empleado insertado exitosamente");
                        }
                    }while(artistas!=4);

                    break;
                case 2:
                    try (Connection conn = DriverManager.getConnection(url,user,password);
                         Statement statement = conn.createStatement()){
                        String sql="SELECT * FROM empleado2";
                        ResultSet resultSet = statement.executeQuery(sql);

                        while(resultSet.next()){
                            int Mostrarid=resultSet.getInt("id");
                            String Mostarnombre=resultSet.getString("nombre");
                            double Mostarsalario =resultSet.getDouble("salario");
                            System.out.println("ID: "+Mostrarid+" Nombre: "+Mostarnombre+" Salario: "+ Mostarsalario);
                        }
                    }catch (SQLException e){
                        System.out.println("ERROR: "+e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("Ingrese el id del empleado:");
                    int Actualizarid=sc.nextInt();
                    System.out.println("Ingrese el salario del empleado:");
                    double ActualizarSalario=sc.nextDouble();

                    try (Connection conn = DriverManager.getConnection(url,user,password)){
                        String sql="UPDATE empleado2 SET SALARIO=? WHERE id=?";
                        PreparedStatement preparedStatement = conn.prepareStatement(sql);

                        preparedStatement.setInt(2,Actualizarid);
                        preparedStatement.setDouble(1, ActualizarSalario);
                        preparedStatement.executeUpdate();
                        System.out.println("Empleado actualizado");
                    }catch (SQLException e){
                        System.out.println("ERROR: "+e.getMessage());
                    }
                    break;
                case 4:
                    System.out.println("Ingrese el id del empleado:");
                    int Eliminarid=sc.nextInt();
                    try (Connection conn = DriverManager.getConnection(url,user,password)){
                        String sql="DELETE FROM empleado2 WHERE id=?";
                        PreparedStatement preparedStatement = conn.prepareStatement(sql);

                        preparedStatement.setInt(1,Eliminarid);

                        int Eliminado=preparedStatement.executeUpdate();
                        System.out.println("Filas eliminadas: "+Eliminado);
                    }catch (SQLException e){
                        System.out.println("ERROR: "+e.getMessage());
                    }
                    break;
                case 5:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opcion incorrecta");
                    break;
            }
        }while(opcion!=4);
    }
}
