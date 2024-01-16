package main;

import BD.Requetes;
import Model.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import static Model.Entite.getId;

public class Main {
    public static final String NOMDB = "DB-SAE";

    public static void main(String[] args) throws SQLException {
        Scanner scannerStr=new Scanner(System.in);
        Scanner scannerStrInt=new Scanner(System.in);
        while (true){
            String nom,mdp,prenom;
            System.out.println("-----------\nVoulez vous :\n1 Vous connecter ?\n2 Vous enregistrer ?\n3 Rester visiteur ?\n4 Quitter :\n-----------");
            int choix= scannerStrInt.nextInt();
            switch (choix){
                case 1:
                    System.out.println("Nom :");
                    nom=scannerStr.next();
                    System.out.println("Mdp :");
                    mdp=scannerStr.next();
                    int id_auth=Requetes.getIdMembreConnecte(NOMDB,nom,mdp);
                    System.out.println(id_auth);
                    if (id_auth >0){


                        boolean retour=false;
                        while (!retour) {
                            System.out.println("-----------\nVoulez vous :\n1 Lister les projets que vous avez fais ?\n2 Lister les projets existant ?\n3 Creer un projet ?\n4 Choisir projet ?\n5 Retour \n6 Quitter \n-----------");
                            choix = scannerStrInt.nextInt();
                            switch (choix) {
                                case 1:
                                    System.out.println("lister projets auth");
                                    List<String> tabName=Requetes.allNameProjectMembre(id_auth,NOMDB);
                                    if(tabName.size()==0){
                                        System.out.println("Vous n'avez pas de projet");
                                        break;
                                    }
                                    for(int i=0;i<tabName.size();i++){
                                        System.out.println(i+1+tabName.get(i));
                                    }
                                    break;


                                case 2:
                                    ///TODO
                                    System.out.println("lister projet public");
                                    break;

                                    ///FAIT
                                case 3:
                                    System.out.println("Qu'elle est le nom de votre projet :");
                                    String nom_projet=scannerStr.next();
                                    System.out.println("Qu'elle est le sujet du projet :");
                                    String sujet=scannerStr.next();
                                    System.out.println("Qu'elles sont les technologies utilise :");
                                    String techno=scannerStr.next();
                                    Projet newProjet=new Projet(1, nom_projet,sujet,techno, new Date(System.currentTimeMillis()), new Date(2024, 12, 28));
                                    newProjet.ajoutBD(NOMDB);
                                    new ProjetMembre(getId(NOMDB,"Projet"),id_auth,false).ajoutBD(NOMDB);


                                    System.out.println("projet creer");
                                    break;

                                    //FAIT
                                case 4:
                                    System.out.println("choisir projets");
                                    System.out.println("lister projets auth");
                                    tabName=Requetes.allNameProjectMembre(id_auth,NOMDB);
                                    if(tabName.size()==0){
                                        System.out.println("Vous n'avez pas de projet");
                                        break;
                                    }
                                    System.out.println("Qu'elle projet voulez-vous choisir :");
                                    for(int i=0;i<tabName.size();i++){
                                        System.out.println(i+1+tabName.get(i));
                                    }
                                    choix = scannerStrInt.nextInt();
                                    Projet projet=Requetes.allProjectId(choix,NOMDB);//PAS au bonne endroit car tab.get(i) renvois le nom normalement peut etre [0]de celle la
                                    retour=false;
                                    while (!retour){
                                        System.out.println("-----------\nVoulez vous :\n1 Lister les membres ?\n2 Lister les objectifs ?\n3 lister les ressources logicielles ?\n4 lister les ressources materielles \n5 lister les messages lier au forum "+projet.getNom_projet()+"\n6 Retour \n7 Quitter \n-----------");
                                        choix = scannerStrInt.nextInt();
                                        switch (choix){
                                            ///FAIT
                                            case 1:
                                                System.out.println("lister des membres");
                                                Requetes.afficherMembresDuProjet(NOMDB,projet.getId());
                                                break;


                                                ///FAIT
                                            case 2:
                                                System.out.println("lister des objectifs realise");
                                                List<Objectif> tabObjectif=Requetes.getObjectifsRealisesParProjet(NOMDB,projet.getId());
                                                for (int i=0;i<tabObjectif.size();i++){
                                                    tabObjectif.get(i).toString();
                                                }
                                                System.out.println("lister des objectifs non realise");
                                                tabObjectif=Requetes.getObjectifsNonRealisesParProjet(NOMDB,projet.getId());
                                                for (int i=0;i<tabObjectif.size();i++){
                                                    tabObjectif.get(i).toString();
                                                }
                                                break;

                                                ///FAIT
                                            case 3:
                                                System.out.println("lister les ressources logicielles");
                                                List<RessourcesLogicielles> tabLog=Requetes.allResourceLogicielProjet(projet.getId(),NOMDB);
                                                for (int i=0;i<tabLog.size();i++){
                                                    tabLog.get(i).toString();
                                                }
                                                break;

                                                ///FAIT
                                            case 4:
                                                System.out.println("lister les ressources materielles");
                                                List<RessourcesMaterielles> tabMat=Requetes.allResourceMaterielleProjet(projet.getId(),NOMDB);
                                                for (int i=0;i<tabMat.size();i++){
                                                    tabMat.get(i).toString();
                                                }
                                                break;


                                            case 5:
//                                                System.out.println("lister les messages lier au forum "+tab.get(i));
                                                break;

                                                ///FAIT
                                            case 6:
                                                retour = true;
                                                break;
                                                ///FAIT
                                            case 7:
                                                return;
                                        }
                                    }
                                    break;
                                    ///FAIT
                                case 5:
                                    retour = true;
                                    break;
                                    //FAIT
                                case 6:
                                    return;
                            }
                        }
                    }
                    break;

                ///FAIT
                case 2:
                    System.out.println("Nom :");
                    nom=scannerStr.next();
                    System.out.println("Prenom :");
                    prenom=scannerStr.next();
                    System.out.println("Mdp :");
                    mdp=scannerStr.next();
                    //TODO
                    //creer auth
                    new Membre(1,nom,prenom);
                    System.out.println("Creer auth");
                    System.out.println("Utilisateur crée vous pouvez desormais vous connecter");
                    break;


                case 3:
                    ///TODO
                    System.out.println("lister projets public");
                    break;


                case 4:
                    return;
            };
        }
    }
}
