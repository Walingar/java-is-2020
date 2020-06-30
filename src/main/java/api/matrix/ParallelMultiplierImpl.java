package api.matrix;

public class ParallelMultiplierImpl implements ParallelMultiplier {

  private final int maxThreadsCount;

  public ParallelMultiplierImpl(int maxThreadsCount) {
    this.maxThreadsCount = maxThreadsCount;
  }

  @Override
  public double[][] mul(double[][] firstMatrix, double[][] secondMatrix) {
    if (isValidOperation(firstMatrix, secondMatrix)) {
      return new double[0][0];
    }

    return new ParallelMultiplierExecutor(
        calculateNumberOfThreads(maxThreadsCount, firstMatrix.length * secondMatrix[0].length),
        firstMatrix,
        secondMatrix
    ).calculateMatrix();
  }

  private boolean isValidOperation(double[][] firstMatrix, double[][] secondMatrix) {
    return firstMatrix[0].length != secondMatrix.length;
  }

  private int calculateNumberOfThreads(int maxThreadsCount, int neededThreadCount) {
    return Math.min(maxThreadsCount, neededThreadCount);
  }
}


