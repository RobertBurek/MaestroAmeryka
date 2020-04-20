package pl.info.mojeakcje.maestroameryka.model.modeleStrategii;

public class Szukana {

    private String rodzajSzukanej;
    private String szukanaWartosc;

    public Szukana() {
    }

    public Szukana(String rodzajSzukanej, String szukanaWartosc) {
        this.rodzajSzukanej = rodzajSzukanej;
        this.szukanaWartosc = szukanaWartosc;
    }

    public String getRodzajSzukanej() {
        return rodzajSzukanej;
    }

    public void setRodzajSzukanej(String rodzajSzukanej) {
        this.rodzajSzukanej = rodzajSzukanej;
    }

    public String getSzukanaWartosc() {
        return szukanaWartosc;
    }

    public void setSzukanaWartosc(String szukanaWartosc) {
        this.szukanaWartosc = szukanaWartosc;
    }

    @Override
    public String toString() {
        return "Szukana{" +
                "rodzajSzukanej='" + rodzajSzukanej + '\'' +
                ", szukanaWartosc='" + szukanaWartosc + '\'' +
                '}';
    }
}
