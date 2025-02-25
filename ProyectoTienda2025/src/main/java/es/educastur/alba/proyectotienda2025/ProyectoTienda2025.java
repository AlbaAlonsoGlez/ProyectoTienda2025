/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package es.educastur.alba.proyectotienda2025;

import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author alu01d
 */
public class ProyectoTienda2025 implements Serializable {
    Scanner sc=new Scanner(System.in);
    private ArrayList<Pedido> pedidos;
    private HashMap <String, Articulo> articulos;
    private HashMap <String, Cliente> clientes;
    
    public ProyectoTienda2025() {
        pedidos = new ArrayList();
        articulos = new HashMap();
        clientes = new HashMap();
    }

    public ArrayList<Pedido> getPedidos() {
        return pedidos;
    }

    public HashMap<String, Articulo> getArticulos() {
        return articulos;
    }

    public HashMap<String, Cliente> getClientes() {
        return clientes;
    }
    
    public static void main(String[] args) {
        ProyectoTienda2025 t= new ProyectoTienda2025();
        t.leerArchivos();
        //t.cargaDatos();
        t.menu();
        t.backup();
        
    }
    
    //<editor-fold defaultstate="collapsed" desc="MENÚS">
     private void menu() {
        Scanner sc= new Scanner(System.in);
        int opcion=0;
        do {
            System.out.println("1. PEDIDOS");
            System.out.println("2. ARTÍCULOS");
            System.out.println("3. CLIENTES");
            System.out.println("4. COPIA DE SEGURIDAD GENERAL");
            System.out.println("9. SALIR");
            opcion = sc.nextInt();
            switch (opcion) {
                case 1: {
                    menuPedidos();
                    break;
                }
                case 2: {
                    menuArticulos();
                    break;
                }
                case 3: {
                    menuClientes();
                    break;
                }
                case 4: {
                    backup();
                    break;
                }
            }
        } while (opcion != 9);
    }
     
    private void menuPedidos() {
        Scanner sc= new Scanner(System.in);
        int opcion=0;
        do {
            System.out.println("1. LISTA DE PEDIDOS");
            System.out.println("2. LISTA DE PEDIDOS POR TOTAL");
            System.out.println("3. NUEVO PEDIDO");
            System.out.println("9. SALIR");
            opcion = sc.nextInt();
            switch (opcion) {
                case 1: {
                    listarPedidos();
                    break;
                }
                case 2: {
                    listarPedidosPorTotal();
                    break;
                }
                case 3: {
                    nuevoPedido();
                    break;
                }
            }
        } while (opcion != 9);
    }
    
    private void menuArticulos() {
        Scanner sc= new Scanner(System.in);
        int opcion=0;
        do {
            System.out.println("1. NUEVO ARTÍCULO");
            System.out.println("2. ELIMINAR ARTÍCULO");
            System.out.println("3. LISTA DE ARTÍCULOS");
            System.out.println("4. ARTÍCULOS MÁS VENDIDOS");
            System.out.println("5. MOSTRAR ARTÍCULOS POR SECCIONES");
            System.out.println("6. COPIA DE SEGURIDAD POR SECCIONES");
            System.out.println("9. SALIR");
            opcion = sc.nextInt();
            switch (opcion) {
                case 1: {
                    nuevoArticulo();
                    break;
                }
                case 2: {
                    eliminarArticulo();
                    break;
                }
                case 3: {
                    listarArticulos();
                    break;
                }
                case 4: {
                    ordenarArticulosPorDemanda();
                    break;
                }
                case 5: {
                    leerArchivosSeccion();
                    break;
                }
                case 6: {
                    backupPorSeccion();
                    break;
                }
            }
        } while (opcion != 9);
    }
    
    private void menuClientes() {
        Scanner sc= new Scanner(System.in);
        int opcion=0;
        do {
            System.out.println("1. NUEVO CLIENTE");
            System.out.println("2. ELIMINAR CLIENTE");
            System.out.println("3. CLIENTES A TEXTO");
            System.out.println("4. LEER TXT CLIENTES");
            System.out.println("9. SALIR");
            opcion = sc.nextInt();
            switch (opcion) {
                case 1: {
                    nuevoCliente();
                    break;
                }
                case 2: {
                    eliminarCliente();
                    break;
                }
                case 3: {
                    clientesTxtBackup();
                    break;
                }
                case 4: {
                    clientesTxtLeer();
                    break;
                }
            }
        } while (opcion != 9);
    }
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="GESTIÓN DE CLIENTES">
    private void nuevoCliente() {
         String dni, nombre, telefono, email;

    do {
        System.out.print("Deme su DNI: ");
        dni = sc.nextLine().toUpperCase();
        if (!MetodosAux.validarDNI(dni)) {
            System.out.println("DNI no válido. Inténtelo de nuevo.");
        } else if (clientes.containsKey(dni)) {
            System.out.println("Ese cliente ya existe. Intente con otro DNI.");
            return;
        }
    } while (!MetodosAux.validarDNI(dni));

    System.out.print("Deme su NOMBRE: ");
    nombre = sc.nextLine();
    
    do {
        System.out.print("Deme su TELÉFONO: ");
        telefono = sc.nextLine();
        if (!telefono.matches("\\d{9}")) {
            System.out.println("Número de teléfono no válido. Debe tener 9 dígitos.");
        }
    } while (!telefono.matches("\\d{9}"));
    
    do {
        System.out.print("Deme su EMAIL: ");
        email = sc.nextLine();
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            System.out.println("Correo electrónico no válido. Inténtelo de nuevo.");
        }
    } while (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$"));

    Cliente c = new Cliente(dni, nombre, telefono, email);
    clientes.put(dni, c);
    System.out.println("Cliente añadido correctamente.");
    }
     
     private void eliminarCliente() {
         System.out.println("_");
    }
    
    public void clientesTxtBackup() {
        try(BufferedWriter bfwClientes=new BufferedWriter(new FileWriter("clientes.csv"))){
            for (Cliente c : clientes.values()) {
                bfwClientes.write(c.getDni() + "," + c.getNombre() + "," + c.getTelefono() + "," + c.getEmail() + "\n");
            }
        }catch (FileNotFoundException e) {
                 System.out.println(e.toString());   
        }catch(IOException e){
            System.out.println(e.toString());
        }
    }  
    
    public void clientesTxtLeer() {
        // LEEMOS LOS CLIENTES DESDE EL ARCHIVO .csv A UNA COLECCION HASHMAP AUXILIAR Y LA IMPRIMIMOS
        HashMap <String,Cliente> clientesAux = new HashMap();
        try(Scanner scClientes=new Scanner(new File("clientes.csv"))){
            while (scClientes.hasNextLine()){
                String [] atributos = scClientes.nextLine().split("[,]");                                                              
                Cliente c=new Cliente(atributos[0],atributos[1],atributos[2],atributos[3]); 
                clientesAux.put(atributos[0], c);
            }
        }catch(IOException e){
            System.out.println(e.toString());
        }
        clientesAux.values().forEach(System.out::println);
    }  
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="GESTIÓN DE ARTÍCULOS">
    private void nuevoArticulo() {
        String id, descripcion;
        int existencias;
        double precio;

        do {
            System.out.print("Ingrese el ID del artículo: ");
            id = sc.nextLine();
            if (articulos.containsKey(id)) {
                System.out.println("El ID ya existe. Intente con otro.");
                return;
            }
        } while (id.isEmpty());

        System.out.print("Ingrese la descripción del artículo: ");
        descripcion = sc.nextLine();

        do {
            System.out.print("Ingrese la cantidad en stock: ");
            while (!sc.hasNextInt()) {
                System.out.println("Por favor, introduzca un número válido.");
                sc.next();
            }
            existencias = sc.nextInt();
        } while (existencias < 0);

        do {
            System.out.print("Ingrese el precio: ");
            while (!sc.hasNextDouble()) {
                System.out.println("Por favor, introduzca un precio válido.");
                sc.next();
            }
            precio = sc.nextDouble();
        } while (precio <= 0);

        Articulo a = new Articulo(id, descripcion, existencias, precio);
        articulos.put(id, a);
        System.out.println("Artículo añadido correctamente.");
    }

    private void eliminarArticulo() {
         System.out.println("HOLA");
    }

    
    public void listarArticulos() {
        ArrayList<Articulo> articulosAux = new ArrayList(articulos.values());
        Collections.sort(articulosAux);
        for (Articulo a : articulosAux) {
            System.out.println(a);
        }
        
        System.out.println("");
        System.out.println("AL REVÉS");
        System.out.println("");
        
        Collections.reverse(articulosAux);
        for (Articulo a : articulosAux) {
            System.out.println(a);
        }
        
        System.out.println("");
        System.out.println("ORDENADO POR PRECIO");
        System.out.println("");
        
        Collections.sort(articulosAux, new ComparaArticulosPorPrecio());
        for (Articulo a : articulosAux) {
            System.out.println(a);
        }
        
        System.out.println("");
        System.out.println("ORDENADO POR EXISTENCIAS");
        System.out.println("");
        
        Collections.sort(articulosAux, new ComparaArticulosPorExistencias());
        for (Articulo a : articulosAux) {
            System.out.println(a);
        }
        
        System.out.println("");
        
        articulos.values().stream().sorted().forEach(System.out::println);
        System.out.println("");
        articulos.values().stream().sorted(new ComparaArticulosPorExistencias()).forEach(System.out::println);
        System.out.println("");
        articulos.values().stream().sorted(new ComparaArticulosPorPrecio()).forEach(System.out::println);
        
        System.out.println("");
        
        
    }
    
    public int cantidadTotalVendida(String idArticulo) {
        return pedidos.stream().flatMap(p -> p.getCestaCompra().stream()) .filter(lp -> lp.getIdArticulo().equals(idArticulo)).mapToInt(LineaPedido::getUnidades).sum();
    }
    
    
    public void ordenarArticulosPorDemanda() {
        articulos.values().stream()
                .sorted(Comparator.comparing(a -> cantidadTotalVendida(a.getIdArticulo()), Comparator.reverseOrder()))
                .forEach(a-> System.out.println(a + " \t - el número de artículos vendidos es: " + cantidadTotalVendida(a.getIdArticulo())));
    }
    
    public void leerArchivosSeccion(){
      System.out.println("Teclea la sección de los artículos que quieres recuperar");
      System.out.println("1 - PERIFERICOS");
      System.out.println("2 - ALMACENAMIENTO");
      System.out.println("3 - IMPRESORAS");
      System.out.println("4 - MONITORES");
      System.out.println("5 - TODOS");
      String id=sc.next();
      ArrayList<Articulo> articulosAux=new ArrayList();
      Articulo a;
      
      try (ObjectInputStream oisArticulos = new ObjectInputStream(new FileInputStream("articulos.dat"))){
            while ( (a=(Articulo)oisArticulos.readObject()) != null){
                if (id.equals("5")) {
                    articulosAux.add(a);
                }else if (a.getIdArticulo().startsWith(id)) {
                    articulosAux.add(a);
                }
            }
	} catch (FileNotFoundException e) {
                 System.out.println(e.toString());
                 
        } catch (EOFException e){
            
        } catch (ClassNotFoundException | IOException e) {
                System.out.println(e.toString()); 
        } 
        articulosAux.forEach(System.out::println);
   }
   
   public void backupPorSeccion(){
      System.out.println("Teclea la sección de la que quiere hacer una copia de seguridad =)");
      System.out.println("1 - PERIFERICOS");
      System.out.println("2 - ALMACENAMIENTO");
      System.out.println("3 - IMPRESORAS");
      System.out.println("4 - MONITORES");
      System.out.println("5 - TODOS");
      String id=sc.next();
      
      
      try (ObjectOutputStream oosPerifericos=new ObjectOutputStream(new FileOutputStream("perifericos.dat"));
            ObjectOutputStream oosAlmacenamiento=new ObjectOutputStream(new FileOutputStream("almacenamiento.dat"));
            ObjectOutputStream oosImpresoras=new ObjectOutputStream(new FileOutputStream("impresoras.dat"));
            ObjectOutputStream oosMonitores=new ObjectOutputStream(new FileOutputStream("monitores.dat"))){
            
            for (Articulo a : articulos.values()) {
                char seccion=a.getIdArticulo().charAt(0);
                switch (seccion) {
                    case '1':
                        oosPerifericos.writeObject(a);
                        break;
                    case '2':
                        oosAlmacenamiento.writeObject(a);
                        break;
                    case '3':
                        oosImpresoras.writeObject(a);
                        break;
                    case '4':
                        oosMonitores.writeObject(a);
                        break;   
                }
            }
            
            System.out.println("Copia de seguridad realizada con éxito =)");
        }
        
        catch (FileNotFoundException e) {
            System.out.println(e.toString());
        } catch (IOException e) {
            System.out.println(e.toString());
        }
      
   }
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="GESTIÓN DE PEDIDOS">
    
    public void listarPedidos() {
        Collections.sort(pedidos);
        for (Pedido p : pedidos) {
            System.out.println(p);
        }
        
        System.out.println("");
        System.out.println("AL REVÉS");
        System.out.println("");
        
        Collections.reverse(pedidos);
        for (Pedido p : pedidos) {
            System.out.println(p);
        }
        
        pedidos.stream().sorted().forEach(System.out::println);
        //podría ser también: pedidos.stream().forEach(p-> System.out.println(p));
    }
    
    public void listarPedidosPorTotal() {
        Scanner sc=new Scanner(System.in);
        System.out.println(pedidos.get(0));
        
        pedidos.stream().sorted(Comparator.comparing(p -> totalPedido(p)))
                .forEach(p -> System.out.println(p + "\t - IMPORTE TOTAL:" + totalPedido(p)));
        
        System.out.println("n");
        System.out.println("¿De qué cliente quieres ver el pedido? Introduce el nombre");
        String nombre=sc.nextLine().toUpperCase();
        
         pedidos.stream().filter(p -> p.getClientePedido().getNombre().equals(nombre))
                .filter(p -> totalPedido(p)>500)
                .sorted(Comparator.comparing(p -> totalPedido((Pedido) p)).reversed())
                .forEach(p -> System.out.println(p + "\t - IMPORTE TOTAL:" + totalPedido(p)));
        
	System.out.println("\n");

        //ARTICULOS DE UNA SECCIÓN EN CONCRETO (POR TECLADO) ORDENADOS DE MENOR A MAYOR PVP
        System.out.println("Introduce una SECCION:");
        char s=sc.next().charAt(0);
        articulos.values().stream().filter(a->a.getIdArticulo().charAt(0)==s)
                .sorted(new ComparaArticulosPorPrecio().reversed()).forEach(System.out::println);
        }
    
    public double totalPedido(Pedido p) {
        double total=0;
        for (LineaPedido l:p.getCestaCompra())
        {
            total+=(articulos.get(l.getIdArticulo()).getPvp())
                    *l.getUnidades();
        }
        return total;
    }
    
    
    public void stock(String id, int unidadesPed) throws StockAgotado, StockInsuficiente {
        int n=articulos.get(id).getExistencias();
        if (n==0){
            throw new StockAgotado ("Stock AGOTADO para el articulo: "+ articulos.get(id).getDescripcion());
        }else if (n < unidadesPed){
            throw new StockInsuficiente ("No hay Stock suficiente. Me pide  " + unidadesPed + " de "
                                        + articulos.get(id).getDescripcion()
                                        + " y sólo se dispone de: "+ n);
        }
    } 
    
    public String generaIdPedido(String idCliente){ 
        int contador = 0;     
        String nuevoId;
        for (Pedido p: pedidos){
            if (p.getClientePedido().getDni().equalsIgnoreCase(idCliente)){
                contador++;
            }
        }
        contador++;
        nuevoId= idCliente + "-" + String.format("%03d", contador) + "/" + LocalDate.now().getYear();
        return nuevoId;
    }
    
     public void nuevoPedido() {
        //ARRAYLIST AUXILIAR PARA CREAR EL PEDIDO
        ArrayList<LineaPedido> CestaCompraAux = new ArrayList();
        String dniT, idT, opc, pedidasS;
        int pedidas=0;
        sc.nextLine();
        do{
            System.out.println("CLIENTE PEDIDO (DNI):");
            dniT=sc.nextLine().toUpperCase();
            //EN CUALQUIER MOMENTO PODEMOS SALIR DEL BUCLE TECLEANDO RETORNO
            if (dniT.isBlank()) break;
            if (!MetodosAux.validarDNI(dniT)|| !clientes.containsKey(dniT)) System.out.println("El DNI no es válido O NO ES CLIENTE DE LA TIENDA");;
        }while (!clientes.containsKey(dniT));
        
        if (!dniT.isBlank()){
            System.out.println("\t\tCOMENZAMOS CON EL PEDIDO");
            System.out.println("INTRODUCE CODIGO ARTICULO (RETURN PARA TERMINAR): ");
            idT=sc.nextLine();
                 
            while (!idT.isEmpty()) {
                if (!articulos.containsKey(idT)){
                    System.out.println("El ID articulo tecleado no existe");
                }else{
                    System.out.print("(" + articulos.get(idT).getDescripcion()+ ") - UNIDADES? ");
                    do {
                        pedidasS=sc.nextLine();
                    }while(!MetodosAux.esInt(pedidasS)); 

                    pedidas=Integer.parseInt(pedidasS);

                    try{
                        stock(idT,pedidas); // LLAMO AL METODO STOCK, PUEDEN SALTAR 2 EXCEPCIONES
                        CestaCompraAux.add(new LineaPedido(idT,pedidas));
                        articulos.get(idT).setExistencias(articulos.get(idT).getExistencias()-pedidas);
                    }catch (StockAgotado e){
                        System.out.println(e.getMessage());
                    }catch (StockInsuficiente e){
                        System.out.println(e.getMessage());
                        int disponibles=articulos.get(idT).getExistencias();
                        System.out.print("QUIERES LAS " + disponibles + " UNIDADES DISPONIBLES? (S/N) ");
                        opc=sc.next();
                        if (opc.equalsIgnoreCase("S")){
                            CestaCompraAux.add(new LineaPedido(idT,disponibles));
                            articulos.get(idT).setExistencias(articulos.get(idT).getExistencias()-disponibles);
                        }
                    }
                }
                System.out.println("INTRODUCE CODIGO ARTICULO (RETURN PARA TERMINAR): ");
                idT=sc.nextLine();
            }
         
            //IMPRIMO EL PEDIDO Y SOLICITO ACEPTACION DEFINITIVA DEL MISMO 
            for (LineaPedido l:CestaCompraAux)
            {
                System.out.println(articulos.get(l.getIdArticulo()).getDescripcion() + " - ("+ l.getUnidades() + ")");
            }
            System.out.println("ESTE ES TU PEDIDO. PROCEDEMOS? (S/N)   ");
            opc=sc.next();
            if (opc.equalsIgnoreCase("S")){
            // ESCRIBO EL PEDIDO DEFINITIVAMENTE Y DESCUENTO LAS EXISTENCIAS PEDIDAS DE CADA ARTICULO
                LocalDate hoy=LocalDate.now();
                pedidos.add(new Pedido(generaIdPedido(dniT),clientes.get(dniT),hoy,CestaCompraAux));
            }
            else{    
                for (LineaPedido l:CestaCompraAux)
                {
                    articulos.get(l.getIdArticulo()).setExistencias(articulos.get(l.getIdArticulo()).getExistencias()+l.getUnidades());
                } 
            }
        }
    }
//</editor-fold>
     
    //<editor-fold defaultstate="collapsed" desc="PERSISTENCIA">
    public void backup() {
        try (ObjectOutputStream oosArticulos=new ObjectOutputStream(new FileOutputStream("articulos.dat"));
            ObjectOutputStream oosClientes=new ObjectOutputStream(new FileOutputStream("clientes.dat"));
            ObjectOutputStream oosPedidos=new ObjectOutputStream(new FileOutputStream("pedidos.dat"))) {
            
            for (Articulo a: articulos.values()) {
                oosArticulos.writeObject(a);
            }
            
            for (Cliente c: clientes.values()) {
                oosClientes.writeObject(c);
            }
            
            for (Pedido p: pedidos) {
                oosPedidos.writeObject(p);
            }
            
            System.out.println("Copia de seguridad realizada con éxito =)");
        }
        
        catch (FileNotFoundException e) {
            System.out.println(e.toString());
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }
    
   public void leerArchivos() {
        try (ObjectInputStream oisArticulos = new ObjectInputStream(new FileInputStream("articulos.dat"))){
            Articulo a;
            while ( (a=(Articulo)oisArticulos.readObject()) != null){
                 articulos.put(a.getIdArticulo(), a);
            } 
	} catch (FileNotFoundException e) {
                 System.out.println(e.toString());
                 
        } catch (EOFException e){
            
        } catch (ClassNotFoundException | IOException e) {
                System.out.println(e.toString()); 
        } 
        
        try (ObjectInputStream oisClientes = new ObjectInputStream(new FileInputStream("clientes.dat"))){
            Cliente c;
            while ( (c=(Cliente)oisClientes.readObject()) != null){
                 clientes.put(c.getDni(), c);
            } 
	} catch (FileNotFoundException e) {
                 System.out.println(e.toString());    
                 
        } catch (EOFException e){
            
        } catch (ClassNotFoundException | IOException e) {
                System.out.println(e.toString()); 
        }
        
        
        try (ObjectInputStream oisPedidos = new ObjectInputStream(new FileInputStream("pedidos.dat"))){
            Pedido p;
            while ( (p=(Pedido)oisPedidos.readObject()) != null){
                 pedidos.add(p);
            } 
	} catch (FileNotFoundException e) {
                 System.out.println(e.toString());    
                 
        } catch (EOFException e){
            
        } catch (ClassNotFoundException | IOException e) {
                System.out.println(e.toString()); 
        }

    }
   
     
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="CARGA DATOS">
    
    public void cargaDatos(){
        
       clientes.put("80580845T",new Cliente("80580845T","ANA ","658111111","ana@gmail.com"));
       clientes.put("36347775R",new Cliente("36347775R","LOLA","649222222","lola@gmail.com"));
       clientes.put("63921307Y",new Cliente("63921307Y","JUAN","652333333","juan@gmail.com"));
       clientes.put("02337565Y",new Cliente("02337565Y","EDU","634567890","edu@gmail.com"));
              
       articulos.put("1-11",new Articulo("1-11","RATON LOGITECH ST ",14,15));
       articulos.put("1-22",new Articulo("1-22","TECLADO STANDARD  ",9,18));
       articulos.put("2-11",new Articulo("2-11","HDD SEAGATE 1 TB  ",16,80));
       articulos.put("2-22",new Articulo("2-22","SSD KINGSTOM 256GB",9,70));
       articulos.put("2-33",new Articulo("2-33","SSD KINGSTOM 512GB",0,200));
       articulos.put("3-22",new Articulo("3-22","EPSON PRINT XP300 ",5,80));
       articulos.put("4-11",new Articulo("4-11","ASUS  MONITOR  22 ",5,100));
       articulos.put("4-22",new Articulo("4-22","HP MONITOR LED 28 ",5,180));
       articulos.put("4-33",new Articulo("4-33","SAMSUNG ODISSEY G5",12,580));
       
       LocalDate hoy = LocalDate.now();
       pedidos.add(new Pedido("80580845T-001/2024",clientes.get("80580845T"),hoy.minusDays(1), new ArrayList<>
        (List.of(new LineaPedido("1-11",3),new LineaPedido("4-22",3)))));                                                                                                                                                               
       pedidos.add(new Pedido("80580845T-002/2024",clientes.get("80580845T"),hoy.minusDays(2), new ArrayList<>
        (List.of(new LineaPedido("4-11",3),new LineaPedido("4-22",2),new LineaPedido("4-33",4)))));
       pedidos.add(new Pedido("36347775R-001/2024",clientes.get("36347775R"),hoy.minusDays(3), new ArrayList<>
        (List.of(new LineaPedido("4-22",1),new LineaPedido("2-22",3)))));
       pedidos.add(new Pedido("36347775R-002/2024",clientes.get("36347775R"),hoy.minusDays(5), new ArrayList<>
        (List.of(new LineaPedido("4-33",3),new LineaPedido("2-11",3)))));
       pedidos.add(new Pedido("63921307Y-001/2024",clientes.get("63921307Y"),hoy.minusDays(4), new ArrayList<>
        (List.of(new LineaPedido("2-11",5),new LineaPedido("2-33",3),new LineaPedido("4-33",2)))));
    }
    
//</editor-fold>

}
