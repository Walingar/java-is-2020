package api.matrix;

import java.util.ArrayList;
import java.util.List;

public class Executor {

  private List<Worker> workers;
  private double[][] result;
  private int[] tasks;

  public Executor(int numberOfThreads, double[][] firstMatrix, double[][] secondMatrix) {
    this.workers = new ArrayList<>(numberOfThreads);
    int resultHeight = firstMatrix.length;
    int resultWidth = secondMatrix[0].length;
    this.result = new double[resultHeight][resultWidth];
    calculateTasks(resultHeight, resultWidth, numberOfThreads);

    for (int i = 0; i < numberOfThreads; i++) {
      workers.add(new Worker(firstMatrix, secondMatrix, result));
    }
  }

  public double[][] calculateMatrix() {
    try {
      int taskNumber = 0;
      for (Worker worker : workers) {
        worker.setTask(tasks[taskNumber], tasks[taskNumber + 1] - 1);
        worker.start();
        taskNumber++;
      }

      for (Worker worker : workers) {
        worker.join();
      }
    } catch (InterruptedException ex) {
      System.out.println(ex.getMessage());
    }

    return result;
  }

  private void calculateTasks(int resultHeight, int resultWidth, int numberOfThreads) {
    tasks = new int[numberOfThreads + 1];
    tasks[0] = 0;
    int[] taskPerThread = calculateTaskPerThread(resultHeight, resultWidth, numberOfThreads);
    for (var i = 1; i < numberOfThreads; i++) {
      tasks[i] = tasks[i - 1] + taskPerThread[i];
    }
    tasks[numberOfThreads] = resultHeight * resultWidth;
  }

  private int[] calculateTaskPerThread(int resultHeight, int resultWidth, int numberOfThreads) {
    int[] simpleTask = new int[numberOfThreads];
    for (var i = 0; i < numberOfThreads; i++) {
      simpleTask[i] = resultHeight * resultWidth / numberOfThreads;
    }

    int additionalTasks = resultHeight * resultWidth - resultHeight * resultWidth / numberOfThreads * numberOfThreads;
    for (int i = 0; i <= additionalTasks; i++) {
      simpleTask[i]++;
    }
    return simpleTask;
  }

  private static class Worker extends Thread {

    private double[][] firstMatrix;
    private double[][] secondMatrix;
    private double[][] result;
    private int taskBegin;
    private int taskEnd;

    Worker(double[][] firstMatrix, double[][] secondMatrix, double[][] result) {
      this.firstMatrix = firstMatrix;
      this.secondMatrix = secondMatrix;
      this.result = result;
    }

    public void setTask(int begin, int end) {
      this.taskBegin = begin;
      this.taskEnd = end;
    }

    @Override
    public void run() {
      int resultHeight = firstMatrix.length;
      int resultWidth = firstMatrix[0].length;

      for (int i = taskBegin; i <= taskEnd; i++) {
        int column = i % resultHeight;
        int row = i / resultHeight;
        result[row][column] = 0;
        for (int r = 0; r < resultWidth; r++) {
          result[row][column] += firstMatrix[row][r] * secondMatrix[r][column];
        }
      }
    }
  }
}