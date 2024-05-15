package Services.ServiceProduit;

import DataBaseSource.DataSource;
import Entity.UserAdmin.Membre;
import Entity.entitiesProduits.Command;
import Entity.entitiesProduits.Commandes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceCommand {
    Statement ste;
    Connection conn = DataSource.getInstance().getCnx();





    public List<Command> afficherCommands(int userId) {
        List<Command> commandsList = new ArrayList<>();

        try {
            String query = "SELECT * FROM commands WHERE id_client = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Command command = new Command(
                        rs.getInt("id"),
                        rs.getInt("id_client"),
                        rs.getString("date_commande"),
                        rs.getFloat("cout_totale"),
                        rs.getString("mode_paiement"),
                        rs.getString("mode_livraison"),
                        rs.getString("etat_commande"),
                        rs.getString("adresse")
                );
                commandsList.add(command);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return commandsList;
    }




}
