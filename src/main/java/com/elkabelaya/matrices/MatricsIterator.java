/*
 * Iterates in matrix cells
 */
package com.elkabelaya.matrices;
import java.util.concurrent.locks.ReentrantLock;
/**
 *
 * @author elkabelaya
 */
public class MatricsIterator {
    private int currentI;
    private int currentJ;
    private int size;
    private ReentrantLock mutex = new ReentrantLock();

    MatricsIterator(int size){
        this.size = size;
    }
    //starts iteration from C00
    void start(){
       currentI = 0;
       currentJ = -1;
    }

    //moves to next element: next column in row, or first column of next row
    //synchronized
    CellPosition next (String threadName) {

        try {
            mutex.lock();
            int lastIndex = size-1;
            if (currentJ < lastIndex){
                currentJ++;
            } else {
                currentJ=0;
                currentI++;
            }
            
            if (currentI > lastIndex){
                return null;
            } else {
                
                Logger.getInstance().debug ( threadName + " counts Element i: " + currentI + ", j: " + currentJ );
                return new CellPosition (currentI,currentJ);
            }

        } catch (Exception ex) {
                ex.printStackTrace();
        } finally {
            mutex.unlock();
        }
        return null;

    }

    //indicates if it is possible to move next
    boolean hasNext(){
        return currentI < size;
    }
}
