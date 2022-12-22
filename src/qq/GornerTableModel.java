package qq;
import javax.swing.table.AbstractTableModel;
public class GornerTableModel extends AbstractTableModel{
    private Double[] coefficients;
    private Double from;
    private Double to;
    private Double step;


    public Double getTo() {
        return to;
    }

    public Double getFrom() {
        return from;
    }

    public Double getStep() {
        return step;
    }

    public GornerTableModel(Double from, Double to, Double step,
                            Double[] coefficients) {
        this.from = from;
        this.to = to;
        this.step = step;
        this.coefficients = coefficients;
    }

    @Override
    public int getRowCount() {
        return  new Double(Math.ceil((to-from)/step)).intValue()+1;
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int row, int col) {
        double x = from + step * row;
        if (col == 0) {
// Если запрашивается значение 1-го столбца, то это X
            return x;
        } else if (col == 1) {

            Double result = 0.0;
            int i = 0;
            while (i < coefficients.length) {
                result = result + x * coefficients[i];
                i++;
            }
            return result;
        } else if (col == 2) {
            Double result = coefficients[coefficients.length - 1];
            int i = coefficients.length - 1;
            while (i >= 0) {
                result = result + x * coefficients[i];
                i--;
            }
            return result;
        } else  {
            Double result2 = 0.0, result = 0.0, result1 = 0.0;
            int i = 0;
            while (i < coefficients.length) {
                result1 = result1 + x * coefficients[i];
                i++;
            }
            result2 = coefficients[coefficients.length - 1];
            i = coefficients.length - 1;
            while (i >= 0) {
                result2 = result2 + x * coefficients[i];
                i--;
            }
            result = result2 - result1;
            return result;
        }

    }
    public String getColumnName(int col) {
        switch (col) {
            case 0:
// Название 1-го столбца
                return "Значение X";
            case 2:
                return "значение многочлена в точке";
            case 3:
                return "разность";
// Название 2-го столбца
            default:
                return "Значение многочлена";
        }

    }
    public Class<?> getColumnClass(int col) {
// И в 1-ом и во 2-ом столбце находятся значения типа Double
        return Double.class;
    }


}
