package ingdelsw.ExecutablePrototype;

public enum MassIcon{
    GALILEO("galileo.png"),
    NEWTON("newton.png"),
    LEIBNITZ("leibnitz.png"),
	BERNOULLI("bernoulli.png");

    private String filePath;

    MassIcon(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
}
