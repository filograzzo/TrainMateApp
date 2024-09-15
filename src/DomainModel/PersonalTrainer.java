package DomainModel;

public class PersonalTrainer extends BaseUser {

    //Questa classe è una classe di oggetti temporanei: i dati restano sempre salvati nel database e solo al momento del login
    //voglio compiere un'azione allora viene creato un oggetto Personal trainer che, essendo il costruttore package private,
    //può essere chiamato solo all'interno dello stesso package da altri metodi (nel nostro caso solo dal metodo per il login).

    //fixme: trovare un metodo per rendere questo costruttore non pubblico ma allo stesso tempo accedibile solo da service e dao
    public PersonalTrainer(int id, String username, String password, String email) {
        super(id, username, password, email);
    }



}