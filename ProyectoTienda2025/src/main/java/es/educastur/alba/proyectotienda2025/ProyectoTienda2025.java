/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package es.educastur.alba.proyectotienda2025;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
        t.cargaDatos();
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
            System.out.println("4. HACER COPIA DE SEGURIDAD");
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
            System.out.println("1. LISTA DE ARTÍCULOS");
            System.out.println("2. ARTÍCULOS MÁS VENDIDOS");
            System.out.println("3. ");
            System.out.println("9. SALIR");
            opcion = sc.nextInt();
            switch (opcion) {
                case 1: {
                    listarArticulos();
                    break;
                }
                case 2: {
                    ordenarArticulosPorDemanda();
                    break;
                }
            }
        } while (opcion != 9);
    }
    
    private void menuClientes() {
        
    }
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="GESTIÓN DE ARTÍCULOS">
    public void nuevoArticulo() {
        
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
            
            //COLECCIONES COMPLETAS        
            oosArticulos.writeObject(articulos);
            oosClientes.writeObject(clientes);
            
            //LOS PEDIDOS SE GUARDAN OBJETO A OBJETO     
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
         try (ObjectInputStream oisArticulos=new ObjectInputStream(new FileInputStream("articulos.dat"));
            ObjectInputStream oisClientes=new ObjectInputStream(new FileInputStream("clientes.dat"));
            ObjectInputStream oisPedidos=new ObjectInputStream(new FileInputStream("pedidos.dat"))) {
            
            //COLECCIONES COMPLETAS        
            articulos = (HashMap<String, Articulo>) oisArticulos.readObject();
            clientes = (HashMap<String, Cliente>) oisClientes.readObject();
            
            //LOS PEDIDOS SE GUARDAN OBJETO A OBJETO     
            Pedido p=null;
            while ( (p=(Pedido) oisPedidos.readObject()) != null) {
                pedidos.add(p);
            }
            
            System.out.println("Colecciones importadas con éxito =)");
        }
         
        catch (FileNotFoundException e) {
            System.out.println(e.toString());
        } catch (EOFException e) {
        } catch (ClassNotFoundException | IOException e) {
            System.out.println(e.toString());
        }
    }
     
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="OTROS MÉTODOS">
    
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
