package no.hvl.dat107;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class VitnemalDAO {

    private EntityManagerFactory emf;

    public VitnemalDAO() {
        emf = Persistence.createEntityManagerFactory("vitnemalPU",
				Map.of("jakarta.persistence.jdbc.password", Passwords.AZURE_PASSWORD));
    }
    
    /* --------------------------------------------------------------------- */

    public Vitnemal hentVitnemalForStudent(int studnr) {
        
        EntityManager em = emf.createEntityManager();
        try {
        	
        	return em.find(Vitnemal.class, studnr);
        	
        } finally {
            em.close();
        }
    }

    /* --------------------------------------------------------------------- */

//    public Karakter hentKarakterForStudentIEmne2(int studnr, String emnekode) {
//    	
//    	Vitnemal v = hentVitnemalForStudent(studnr);
//    	for (Karakter k : v.getKarakterer()) {
//    		if osv ... Dere kan gjøre resten selv!
//    	}
//    }
    
    public Karakter hentKarakterForStudentIEmne(int studnr, String emnekode) {
        
        EntityManager em = emf.createEntityManager();
        
        try {
        	String q = """
        			select k from Karakter as k
        			where k.vitnemal.studnr = :studnr
        			  and k.emnekode = :emnekode """;
        	
        	TypedQuery<Karakter> query = em.createQuery(q, Karakter.class);
        	query.setParameter("studnr", studnr);
        	query.setParameter("emnekode", emnekode);
        	
        	return query.getSingleResult();
        	
        } catch (NoResultException e) {
        	return null;
        	
        } finally {
            em.close();
        }
    }
    
    /* --------------------------------------------------------------------- */

    public void registrerKarakterForStudent(
    		int studNr, String emnekode, 
    		LocalDate eksDato, String bokstav) {
        
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        try {
        	tx.begin();
        	
        	// Vi begynner med å hente evt. gammel karakter i aktuelt emne. 
        	// -> Detatched
        	Karakter gmlK = hentKarakterForStudentIEmne(studNr, emnekode); 
        	
        	// Vi henter også vitnemålet for aktuell student.
        	// -> Managed
        	Vitnemal v = em.find(Vitnemal.class, studNr);
        	
        	// Hvis gammel karakter finnes, så fjerner vi den før vi gjør noe mer.
        	if (gmlK != null) {
        		v.fjernKarakter(gmlK); // Fjerner den fra listen i vitnemål-objektet
        		
        		gmlK = em.merge(gmlK); // Merge -> Managed, for å kunne slette
        		em.remove(gmlK);	   // Sletter -> Removed 
        		
        		em.flush(); // Vi forteller em at sletting skal synces i databasen
        					// før vi setter inn ny rad (pga. unique-constraint).
        	}
        	
        	// Da gjenstår å sette inn den nye karakteren
        	// -> New
        	Karakter nyK = new Karakter(emnekode, eksDato, bokstav, v);
        	
        	// Lagring -> Managed
        	em.persist(nyK);
        	
        	// Legge den til i listen i vitnemål-objektet
        	v.leggTilKarakter(nyK);
        	
        	tx.commit();
        	
        } finally {
            em.close();
        }
    }
    
    /* --------------------------------------------------------------------- */

    public List<Karakter> hentKarakterlisteForFerdige(String emnekode) {
        
        EntityManager em = emf.createEntityManager();
        
        try {
        	
        	/* 
        	   	Finne liste av DAT107-karakterer for studenter som er
				ferdig (har sluttdato). Forventer kun denne:
				(1, DAT107, '2022-05-30', 'A', 123456)
				
        	  I SQL kan det se slik ut:
        	  		SELECT k.* 
					FROM karakter AS k 
					NATURAL JOIN vitnemal AS v
					WHERE v.studieslutt IS NOT NULL
					AND k.emnekode LIKE 'DAT107';
        	 */
        	
        	String jpqlQuery = """
        			select k 
        			from Karakter as k, 
        			k.vitnemal as v 
        			where v.studieslutt is not null
        			and k.emnekode like :emnekode""";
        	
			TypedQuery<Karakter> query = em.createQuery(jpqlQuery, Karakter.class);
			query.setParameter("emnekode", emnekode);

			return query.getResultList();
        	
        } finally {
            em.close();
        }
    }
    

}

