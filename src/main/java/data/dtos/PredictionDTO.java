package data.dtos;

public class PredictionDTO {
    private String codeDTO;
    private String nameDTO;
    private int nStudentDTO;
    private double errorDTO;

    public PredictionDTO(String codeDTO, String nameDTO, int nStudentDTO, double errorDTO) {
        this.codeDTO = codeDTO;
        this.nameDTO = nameDTO;
        this.nStudentDTO = nStudentDTO;
        this.errorDTO = errorDTO;
    }

    public String getCodeDTO() {
        return codeDTO;
    }

    public void setCodeDTO(String codeDTO) {
        this.codeDTO = codeDTO;
    }

    public String getNameDTO() {
        return nameDTO;
    }

    public void setNameDTO(String nameDTO) {
        this.nameDTO = nameDTO;
    }

    public int getnStudentDTO() {
        return nStudentDTO;
    }

    public void setnStudentDTO(int nStudentDTO) {
        this.nStudentDTO = nStudentDTO;
    }

    public double getErrorDTO() {
        return errorDTO;
    }

    public void setErrorDTO(double errorDTO) {
        this.errorDTO = errorDTO;
    }
}
