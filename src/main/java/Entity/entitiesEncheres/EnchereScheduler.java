package Entity.entitiesEncheres;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EnchereScheduler {
    private ScheduledExecutorService scheduler;

    public EnchereScheduler() {
        scheduler = Executors.newScheduledThreadPool(1);
    }

    public void planifierEvenement(Enchere enchere) {
        LocalDate dateDebut = enchere.getDateDebut();
        LocalDateTime debutDuJour = dateDebut.atStartOfDay(); // Début de la journée correspondante
        ZonedDateTime debutDuJourZoned = debutDuJour.atZone(ZoneId.systemDefault()); // Convertir en ZonedDateTime

        long delay = calculerDelai(debutDuJourZoned);

        if (delay >= 0) {
            scheduler.schedule(() -> declencherEvenement(enchere), delay, TimeUnit.MILLISECONDS);
        }
    }

    private long calculerDelai(ZonedDateTime debutDuJour) {
        ZonedDateTime maintenant = ZonedDateTime.now();
        long delay = maintenant.until(debutDuJour, ChronoUnit.MILLIS);
        return delay;
    }

    private void declencherEvenement(Enchere enchere) {
        // Implémentez ici le code à exécuter lorsque la date de début de l'enchère est atteinte
        System.out.println("L'enchère " + enchere.getIdEnchere() + " a démarré !");
    }
}
