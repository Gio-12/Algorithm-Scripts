package AlgoritmiDicembre;
public class Tripla implements Comparable<Tripla>{
    private int priority;
    private int TSI;
    private int TSE;
    private int i;

    public Tripla(int TSI, int TSE, int i) {
        this.TSI = TSI;
        this.TSE = TSE;
        this.i = i;
        this.priority = TSE - TSI;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getTSI() {
        return TSI;
    }

    public void setTSI(int TSI) {
        this.TSI = TSI;
    }

    public int getTSE() {
        return TSE;
    }

    public void setTSE(int TSE) {
        this.TSE = TSE;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    @Override
    public int compareTo(Tripla o) {
        Tripla b = null;
        if(o instanceof Tripla){
            b = (Tripla) o;
        }
        if (this.priority == b.priority){
            return this.i > b.i ? 1 : -1;
        }else if(this.priority < b.priority){
            return -1;
        }else{
            return 1;
        }
    }
    public String toString(){
        return "{P: "+priority+", i: "+i+", TSI: "+TSI+", TSE: "+TSE+"}";
    }
}
