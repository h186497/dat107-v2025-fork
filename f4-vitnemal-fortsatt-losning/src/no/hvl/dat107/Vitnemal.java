package no.hvl.dat107;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(schema = "forelesning3_one2many")
public class Vitnemal {
	
	@Id private int studnr;
	private LocalDate studiestart;
	private LocalDate studieslutt;
	
	@OneToMany(mappedBy = "vitnemal", fetch = FetchType.EAGER)
	private List<Karakter> karakterer;

	@Override
	public String toString() {
		return "Vitnemal [studnr=" + studnr + ", studiestart=" 
				+ studiestart + ", studieslutt=" + studieslutt + "]" 
				+ karaktererString();
	}

	private String karaktererString() {
		return karakterer.stream().map(String::valueOf)
				.reduce("", (a, b) -> a + "\n\t" + b);
	}

	public List<Karakter> getKarakterer() {
		return karakterer;
	}

	public void fjernKarakter(Karakter k) {
		karakterer.remove(k);
	}

	public void leggTilKarakter(Karakter nyK) {
		karakterer.add(nyK);
	}
	
	
	
	
	
}
