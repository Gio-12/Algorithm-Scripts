package AlgoritmiDicembre;
import java.util.NoSuchElementException;

public class MinHeap {
    private int capacity;
    private int size;
    private Tripla[] array;

    public MinHeap(int capacity){
        this.capacity = capacity;
        this.size = 0;
        array = new Tripla[capacity];
    }

    public Tripla pop(){
        Tripla value = array[0];
        array[0] = array[--size];
        restoreTopToBottom();
        return value;
    }
    public Tripla peek(){
        if(!isEmpty()){
            return array[0];
        }else{
            throw new NoSuchElementException();
        }
    }
    public void insert(Tripla element){
        if(size < capacity){
            array[size++] = element;
            restoreBottomUp();
        }
    }
    private void restoreTopToBottom() {
            int index = 0;
            while(hasLeftChild(index)) {
                int child = getIndexLeftChild(index);
                if (hasRightChild(index) && getRightChild(index).compareTo(array[child]) < 0) {
                    child = getIndexRightChild(index);
                }
                if (array[index].compareTo(array[child]) > 0) {
                    swap(index, child);
                }else{
                    break;
                }
                index = child;
            }
    }
    private void restoreBottomUp() {
        int index = size-1;
        while(hasParent(index) && getParent(index).compareTo(array[index]) > 0){
            swap(index,getIndexParent(index));
            index = getIndexParent(index);
        }
    }
    private void swap(int index, int indexParent) {
        Tripla tmp = array[index];
        array[index] = array[indexParent];
        array[indexParent] = tmp;
    }
    public boolean isEmpty(){
        return size == 0;
    }

    public boolean isFull(){
        return size == capacity;
    }
    private int getIndexLeftChild(int pos){
        return pos * 2 + 1;
    }
    private int getIndexRightChild(int pos){
        return pos * 2 + 2;
    }
    private int getIndexParent(int pos){
        return (pos - 1) / 2;
    }
    private boolean isLeaf(int pos){
        if(getIndexLeftChild(pos) >= size || getIndexRightChild(pos) >= size){
            return true;
        }
        return false;
    }

    private boolean hasRightChild(int pos){
        return getIndexRightChild(pos) < size;
    }

    private boolean hasLeftChild(int pos){
        return getIndexLeftChild(pos) < size;
    }

    private boolean hasParent(int pos){
        return getIndexParent(pos) >= 0;
    }

    private Tripla getLeftChild(int pos){
        return array[getIndexLeftChild(pos)];
    }

    private Tripla getRightChild(int pos){
        return array[getIndexRightChild(pos)];
    }
    private Tripla getParent(int pos){
        return array[getIndexParent(pos)];
    }

    public String toString(){
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < array.length/2; i++){
             if(hasLeftChild(i)){
                 str.append("PARENT: "+array[i].getPriority()+"("+array[i].getI()+")");
                 str.append("  LEFT: "+getLeftChild(i).getPriority()+"("+getLeftChild(i).getI()+")");
             }
             if(hasRightChild(i)){
                 str.append("  RIGHT: "+getRightChild(i).getPriority()+"("+getRightChild(i).getI()+")");
             }
             str.append("\n");
        }
        return str.toString();
    }
    public void printArray(){
        for(int i = 0; i < size; i++){
            System.out.print(array[i].getPriority()+"("+array[i].getI()+"), ");
        }
    }
    public void printTriple(){
        for(int i = 0; i < size; i++){
            System.out.println(array[i]);
        }
    }
}
