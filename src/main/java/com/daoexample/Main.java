package com.daoexample;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.daoexample.factory.DaoFactory;
import com.daoexample.iface.IDao;
import com.daoexample.models.Player;

public class Main {
    public static void main(String[] args) {
        // Objeto de prueba para persistir
        Player player = new Player();
        IDao<Player, Integer> dao = DaoFactory.getDao(DaoFactory.DaoType.PLAYER);
        
        // Lista de players para almacenar registros
        List<Player> players = new ArrayList<>();
        try (Scanner sc = new Scanner(System.in)) {
            // Variable para elegir una opcion
            int option;
            int idPlayer;
            boolean runapp = true;

            do { 
                System.out.println("*# GESTIÓN DE JUGADORES SELECCIÓN #*");
                System.out.println("¿Qué acción quieres realizar?");
                System.out.println("""
                                   [1] Crear registro
                                   [2] Actualizar registro
                                   [3] Eliminar registro
                                   [4] Buscar un registro
                                   [5] Mostrar todos los registros
                                   [0] Salir""");
                option = sc.nextInt();            

                switch (option) {
                    case 0 -> {
                        runapp = false;
                        System.out.println("|CIERRE DE APLICACION|");
                    }
                    case 1 -> {
                        System.out.println("|REGISTRAR JUGADOR|");
                        System.out.println("Inserta nombre: ");
                        player.setName(sc.next());
                        System.out.println("Inserta posicion: ");
                        player.setPosition(sc.next());
                        System.out.println("Inserta edad: ");
                        player.setAge(sc.nextInt());
                        dao.createRecord(player);
                    }
                    case 2 -> {
                        System.out.println("|ACTUALIZAR JUGADOR|");
                        System.out.println("Id: ");
                        idPlayer = sc.nextInt();
                        System.out.println("Inserta nombre: ");
                        player.setName(sc.next());
                        System.out.println("Posición del jugador: ");
                        player.setPosition(sc.next());
                        System.out.println("Edad del jugador: ");
                        player.setAge(sc.nextInt());
                        dao.updateRecord(player,idPlayer);
                    }
                    case 3 -> {
                        System.out.println("|ELIMINAR JUGADOR|");
                        System.out.println("Id del jugador: ");
                        idPlayer = sc.nextInt();
                        System.out.println(dao.deleteRecord(idPlayer));
                    }
                    case 4 -> {
                        System.out.println("|BUSCAR JUGADOR|");
                        System.out.println("Id del jugador: ");
                        idPlayer = sc.nextInt();
                        Player p = dao.readRecord(idPlayer);
                        System.out.println(p != null ? p.toString() : "Jugador no encontrado");
                    }
                    case 5 -> {
                        System.out.println("|LISTA DE REGISTROS|");
                        players.clear();
                        players = dao.readRecords();
                        for (Player rec : players) {
                            System.out.println(rec.toString());
                        }
                    }
                    default -> System.out.println("Opción no válida");
                }
                
            } while (runapp);
        }
    }
}