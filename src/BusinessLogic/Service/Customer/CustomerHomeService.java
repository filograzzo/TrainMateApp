package BusinessLogic.Service.Customer;

public class CustomerHomeService {
    private void showMenu(){
        System.out.println("\n");
        System.out.println("Cosa vuoi fare?");
        System.out.println("1) Visualizza allenamenti");//databse:vedere e poter aggiungere allenamenti,orario
        System.out.println("2) Prenota un corso");//database:mostra tutti,quando scegli vede se è stato raggiunto il max
        System.out.println("3) Visualizza progressi");//
        System.out.println("4) Prenota un incontro con un personal trainer");
        System.out.println("5) Visualizza il tuo profilo");//database:visualizza i dati del cliente,li può modificare
        System.out.println("6) Logout");
    }
}
