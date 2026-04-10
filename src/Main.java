import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String user = "RIBERA";
        String password = "ribera";
        int opcion = 0;
        int artistas = 0;
        int concierto = 0;
        int entada = 0;
        Scanner sc = new Scanner(System.in);
        //Creamos el switch principal
        do {
            System.out.println("MENU GESTION");
            System.out.println("1. Menu Artistas");
            System.out.println("2. Menu Conciertos");
            System.out.println("3. Menu Entradas");
            System.out.println("4. Salir");
            System.out.println("Ingrese una de las opciones:");
            opcion = sc.nextInt();
            switch (opcion) {
                case 1:

                    //creamos el switch para el menu de artistas
                    do {
                        System.out.println("1. Añade un nuevo artista:");
                        System.out.println("2. Eliimina un artista:");
                        System.out.println("3. Listar artistas:");
                        System.out.println("4. Salir");
                        System.out.println("Eliga una de las opciones:");
                        artistas = sc.nextInt();
                        sc.nextLine();
                        switch (artistas) {
                            //insertar nuevo artista
                            case 1:
                                System.out.println("Ingrese el nombre del artista:");
                                String insertarArtista = sc.nextLine();
                                System.out.println("Ingrese el genero musical del artista:");
                                String insertarGenero = sc.nextLine();
                                System.out.println("Ingrese la nacionalidad del artista:");
                                String insertarNacionalidad = sc.nextLine();
                                try (Connection conn = DriverManager.getConnection(url, user, password);
                                     Statement statement = conn.createStatement()) {
                                    String sql = "INSERT INTO Artista2 (nombre, generomusical, paisorigen) VALUES (?, ?, ?)";
                                    PreparedStatement preparedStatement = conn.prepareStatement(sql);

                                    preparedStatement.setString(1, insertarArtista);
                                    preparedStatement.setString(2, insertarGenero);
                                    preparedStatement.setString(3, insertarNacionalidad);
                                    preparedStatement.executeUpdate();
                                    System.out.println("Artista insertado exitosamente");
                                } catch (SQLException e) {
                                    System.out.println("Error al insertar el artista");
                                }
                                break;
                            //eliminar artista existente (usamos la id)
                            case 2:
                                System.out.println("Ingrese el id del artista:");
                                String eliminarArtista = sc.nextLine();
                                try (Connection conn = DriverManager.getConnection(url,user,password)){
                                    String sql="DELETE FROM Artista2 WHERE id=?";
                                    PreparedStatement preparedStatement = conn.prepareStatement(sql);

                                    preparedStatement.setString(1,eliminarArtista);

                                    preparedStatement.executeUpdate();
                                    System.out.println("Artista eliminado exitosamente");
                                }catch (SQLException e){
                                    System.out.println("ERROR: "+e.getMessage());
                                }
                                break;
                                //Mostramos todos los artistas actuales
                            case 3:
                                try (Connection conn = DriverManager.getConnection(url,user,password);
                                     Statement statement = conn.createStatement()){
                                    String sql="SELECT * FROM Artista2";
                                    ResultSet resultSet = statement.executeQuery(sql);

                                    while(resultSet.next()){
                                        int Mostrarid=resultSet.getInt("id");
                                        String MostarArtista=resultSet.getString("nombre");
                                        String MostarGenero =resultSet.getString("generomusical");
                                        String MostarNacionalidad=resultSet.getString("paisorigen");
                                        System.out.println("ID: "+Mostrarid+"| Artista: "+MostarArtista+"| Genero: "+MostarGenero+"| Nacionalidad: "+MostarNacionalidad);
                                    }
                                }catch (SQLException e){
                                    System.out.println("ERROR: "+e.getMessage());
                                }
                                break;
                            case 4:
                                System.out.println("Volviendo atras...");
                                break;
                            default:
                                System.out.println("Opcion erronea");
                                break;
                        }
                    }while (artistas != 4) ;

                        break;
                case 2:
                    //creamos switch para el menu concierto
                    do {
                        System.out.println("1. Añade un nuevo concierto:");
                        System.out.println("2. Eliimina un concierto:");
                        System.out.println("3. Listar conciertos:");
                        System.out.println("4. Salir");
                        System.out.println("Eliga una de las opciones:");
                        concierto = sc.nextInt();
                        sc.nextLine();
                        switch (concierto) {
                            //Insertamos un nuevo concierto (elegimos el artista mediante su id)
                            case 1:
                                System.out.println("¿Quien canta?");
                                int CantaArtista = sc.nextInt();
                                sc.nextLine();
                                System.out.println("¿Cuando?(DD/MM/YYYY)");
                                String AñadirDia = sc.nextLine();
                                System.out.println("¿Donde?");
                                String AñadirLugar = sc.nextLine();
                                System.out.println("¿Precio de la entrada?");
                                double PrecioEntrada = sc.nextDouble();
                                try (Connection conn = DriverManager.getConnection(url, user, password);
                                     Statement statement = conn.createStatement()) {
                                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                    LocalDate now = LocalDate.parse(AñadirDia,dtf);
                                    Date fecha=Date.valueOf(now);
                                    String sql = "INSERT INTO concierto (artista_id, fecha, lugar, precioentrada) VALUES (?, ?, ?,?)";
                                    PreparedStatement preparedStatement = conn.prepareStatement(sql);

                                    preparedStatement.setInt(1, CantaArtista);
                                    preparedStatement.setDate(2, fecha);
                                    preparedStatement.setString(3, AñadirLugar);
                                    preparedStatement.setDouble(4, PrecioEntrada);
                                    preparedStatement.executeUpdate();
                                    System.out.println("Concierto insertado exitosamente");
                                } catch (SQLException e) {
                                    System.out.println("Error al insertar el concierto");
                                }
                                break;
                                //Eliminamos el concierto (usamos la id)
                            case 2:
                                System.out.println("Ingrese el id del concierto:");
                                String eliminarConcierto = sc.nextLine();
                                try (Connection conn = DriverManager.getConnection(url,user,password)){
                                    String sql="DELETE FROM concierto WHERE id=?";
                                    PreparedStatement preparedStatement = conn.prepareStatement(sql);

                                    preparedStatement.setString(1, eliminarConcierto);

                                    preparedStatement.executeUpdate();
                                    System.out.println("Concierto eliminado exitosamente");
                                }catch (SQLException e){
                                    System.out.println("ERROR: "+e.getMessage());
                                }
                                break;
                                //Mostramos todos los conciertos actuales
                            case 3:
                                try (Connection conn = DriverManager.getConnection(url,user,password);
                                     Statement statement = conn.createStatement()){
                                    String sql="SELECT * FROM Concierto NATURAL JOIN artista2";
                                    ResultSet resultSet = statement.executeQuery(sql);

                                    while(resultSet.next()){
                                        int Mostrarid=resultSet.getInt("id");
                                        String MostarCanta=resultSet.getString("nombre");
                                        Date MostarDia =resultSet.getDate("fecha");
                                        String MostarLugar=resultSet.getString("lugar");
                                        Double MostrarPrecioEntrada = resultSet.getDouble("precioentrada");
                                        System.out.println("ID: "+Mostrarid+"| Artista: "+MostarCanta+"| Fecha: "+MostarDia+"| Lugar: "+MostarLugar+"| PrecioEntrada: "+MostrarPrecioEntrada);
                                    }
                                }catch (SQLException e){
                                    System.out.println("ERROR: "+e.getMessage());
                                }
                                break;
                            case 4:
                                System.out.println("Volviendo atras...");
                                break;
                            default:
                                System.out.println("Opcion erronea");
                                break;
                        }
                    }while (concierto != 4);

                    break;
                case 3:
                    //Insertamos switch para el menu de las entradas
                    do {
                        System.out.println("1. Añade una nueva entrada:");
                        System.out.println("2. Listar entrada:");
                        System.out.println("3. Salir");
                        System.out.println("Eliga una de las opciones:");
                        entada = sc.nextInt();
                        sc.nextLine();
                        switch (entada) {
                            case 1:
                                System.out.println("¿A cual concierto?");
                                int ElegirConcierto = sc.nextInt();
                                sc.nextLine();
                                System.out.println("¿A que nombre?");
                                String AñadirComprador = sc.nextLine();
                                System.out.println("¿Cuantas?");
                                int AñadirCantidad = sc.nextInt();
                                try (Connection conn = DriverManager.getConnection(url, user, password);
                                     Statement statement = conn.createStatement()) {
                                    LocalDate now = LocalDate.now();
                                    Date fecha=Date.valueOf(now);
                                    String sql = "INSERT INTO entrada (concierto_id, comprador, cantidad, fechacompra) VALUES (?, ?, ?,?)";
                                    PreparedStatement preparedStatement = conn.prepareStatement(sql);

                                    preparedStatement.setInt(1, ElegirConcierto);
                                    preparedStatement.setString(2, AñadirComprador);
                                    preparedStatement.setInt(3, AñadirCantidad);
                                    preparedStatement.setDate(4, fecha);
                                    preparedStatement.executeUpdate();
                                    System.out.println("Concierto insertado exitosamente");
                                } catch (SQLException e) {
                                    System.out.println("Error al insertar el concierto");
                                }
                                break;
                            case 2:
                                try (Connection conn = DriverManager.getConnection(url,user,password);
                                     Statement statement = conn.createStatement()){
                                    String sql="SELECT * FROM entrada";
                                    ResultSet resultSet = statement.executeQuery(sql);

                                    while(resultSet.next()){
                                        int Mostrarid=resultSet.getInt("id");
                                        int Eleccion =resultSet.getInt("concierto_id");
                                        String MostarComprador =resultSet.getString("comprador");
                                        int MostarCuanto =resultSet.getInt("cantidad");
                                        Date MostrarFechaCompra = resultSet.getDate("fechacompra");
                                        System.out.println("ID: "+Mostrarid+"| Concierto: "+ Eleccion +"| Comprador: "+ MostarComprador +"| cantidad: "+ MostarCuanto +"| FechaCompra: "+ MostrarFechaCompra);
                                    }
                                }catch (SQLException e){
                                    System.out.println("ERROR: "+e.getMessage());
                                }
                                break;
                            case 3:
                                System.out.println("Volviendo atras...");
                                break;
                            default:
                                System.out.println("Opcion erronea");
                                break;
                        }
                    }while (entada != 3);
                    break;
                case 4:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opcion erronea");
                    break;
            }
        } while (opcion != 4);
    }
}